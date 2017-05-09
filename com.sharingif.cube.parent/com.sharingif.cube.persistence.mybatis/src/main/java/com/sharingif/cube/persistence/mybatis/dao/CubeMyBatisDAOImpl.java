package com.sharingif.cube.persistence.mybatis.dao;

import java.beans.Introspector;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sharingif.cube.components.monitor.IObjectOperationHistory;
import com.sharingif.cube.components.sequence.ISequenceHandler;
import com.sharingif.cube.core.user.CoreUserContextHolder;
import com.sharingif.cube.core.user.ICoreUser;
import com.sharingif.cube.core.util.ClassUtils;
import com.sharingif.cube.persistence.database.dao.AbstractBaseDAO;
import com.sharingif.cube.persistence.database.pagination.IPaginationHandler;
import com.sharingif.cube.persistence.database.pagination.PaginationCondition;
import com.sharingif.cube.persistence.database.pagination.PaginationRepertory;

/**
 * 
 * @Description: [MyBatis Batis DAO]
 * @Author: [Joly]
 * @CreateDate: [2014年1月15日 下午3:14:26]
 * @UpdateUser: [Joly]
 * @UpdateDate: [2014年1月15日 下午3:14:26]
 * @UpdateRemark: [说明本次修改内容]
 * @Version: [v1.0]
 * 
 */
public abstract class CubeMyBatisDAOImpl<T, ID extends Serializable> extends AbstractBaseDAO<T, ID> implements ICubeMyBatisDAO<T, ID> {
	
	private static final String UPDATE_CONDITION_KEY = "condition";
	
	protected final Logger logger = LoggerFactory.getLogger(getClass());
	
	@SuppressWarnings("unchecked")
	private String namespace = namespace = Introspector.decapitalize(((Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0]).getSimpleName());
	
	public CubeMyBatisDAOImpl(){
		
	}

	private SqlSession sqlSession;
	private IPaginationHandler paginationHandler;
	private ISequenceHandler sequenceHandler;

	public void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate) {
		this.sqlSession = sqlSessionTemplate;
	}
	public void setPaginationHandler(IPaginationHandler paginationHandler) {
		this.paginationHandler = paginationHandler;
	}
	public void setSequenceHandler(ISequenceHandler sequenceHandler) {
		this.sequenceHandler = sequenceHandler;
	}
	private String getNamespaceStatement(String statement) {
		return new StringBuilder().append(namespace).append(".").append(statement).toString();
	}
	

	protected T selectOne(String statement) {
		return sqlSession.selectOne(getNamespaceStatement(statement));
	}

	protected T selectOne(String statement, Object parameter) {
		return sqlSession.selectOne(getNamespaceStatement(statement), parameter);
	}

	protected List<T> selectList(String statement) {
		return sqlSession.selectList(getNamespaceStatement(statement));
	}

	protected List<T> selectList(String statement, Object parameter) {
		return sqlSession.selectList(getNamespaceStatement(statement), parameter);
	}
	
	protected List<T> selectList(String statement, Object parameter, RowBounds rowBounds) {
		return sqlSession.selectList(statement, parameter, rowBounds);
	}

	protected int insert(String statement) {
		return sqlSession.insert(getNamespaceStatement(statement));
	}

	protected int insert(String statement, Object parameter) {
		if(null != sequenceHandler)
			parameter=sequenceHandler.handleSequence(parameter);
		
		CubeMyBatisDAOImplAssist.addObjectOperationHistory(parameter);
		
		return sqlSession.insert(getNamespaceStatement(statement), parameter);
	}

	protected int update(String statement) {
		return sqlSession.update(getNamespaceStatement(statement));
	}

	protected int update(String statement, Object parameter) {
		
		CubeMyBatisDAOImplAssist.updateObjectOperationHistory(parameter);
		
		return sqlSession.update(getNamespaceStatement(statement), parameter);
	}

	protected int delete(String statement) {
		return sqlSession.delete(getNamespaceStatement(statement));
	}

	protected int delete(String statement, Object parameter) {
		return sqlSession.delete(getNamespaceStatement(statement), parameter);
	}

	@Override
	public int insert(T obj) {
		return insert("insert",obj);
	}
	
	@Override
	public int deleteById(ID id) {
		return delete("deleteById",id);
	}
	
	@Override
	public int deleteByCondition(T obj) {
		return delete("deleteByCondition", obj);
	}

	@Override
	public int updateById(T obj) {
		return update("updateById", obj);
	}
	
	@Override
	public int updateByCondition(T update, T condition) {
		Map<String, Object> parameter = ClassUtils.getNotEmptyField(update);
		parameter.put(UPDATE_CONDITION_KEY, condition);
		
		return update("updateByCondition", parameter);
	}

	@Override
	public T queryById(ID id) {
		return selectOne("queryById", id);
	}

	@Override
	public T query(T obj) {
		return selectOne("queryList", obj);
	}
	
	@Override
	public List<T> queryList(T obj) {
		return selectList("queryList", obj);
	}
	
	@Override
	public List<T> queryAll() {
		return selectList("queryAll");
	}
	
	@Override
	public int queryPaginationCount(PaginationCondition<? extends Object> paginationCondition) {
		return queryPaginationCount(CubeMyBatisDAOImplAssist.getQueryPaginationCountStatement(CubeMyBatisDAOImplAssist.QUERY_PAGINATION_LIST), paginationCondition);
	}
	
	@Override
	public int queryPaginationCount(String countStatement, PaginationCondition<? extends Object> paginationCondition) {
		return (Integer)selectOne(countStatement, paginationCondition.getCondition());
	}
	
	@Override
	public PaginationRepertory<T> queryPagination(PaginationCondition<? extends Object> paginationCondition) {
		return queryPagination(CubeMyBatisDAOImplAssist.QUERY_PAGINATION_LIST, paginationCondition);
	}
	
	@Override
	public PaginationRepertory<T> queryPagination(String statement, PaginationCondition<? extends Object> paginationCondition) {
		return queryPagination(CubeMyBatisDAOImplAssist.getQueryPaginationCountStatement(statement), statement, paginationCondition);
	}
	
	@Override
	public PaginationRepertory<T> queryPagination(String countStatement, String statement, PaginationCondition<? extends Object> paginationCondition) {
		
		if(null == paginationCondition)
			paginationCondition = new PaginationCondition<Object>();
		
		if(PaginationCondition.DEFAULT_PAGE_SIZE < paginationCondition.getPageSize())
			paginationCondition.setPageSize(PaginationCondition.DEFAULT_PAGE_SIZE);
		
		int count = 0;
		if(paginationCondition.isQueryCount()){
			count = queryPaginationCount(countStatement, paginationCondition);
		}
		
		List<T> list = null;
		if(0 != count || !paginationCondition.isQueryCount()){
			paginationCondition.setRowBounds(paginationHandler.handleRowBounds(paginationCondition.getCurrentPage(), paginationCondition.getPageSize()));
			list = selectList(statement, paginationCondition);
		}
		
		
		return new PaginationRepertory<T>(count, list, paginationCondition);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public <R> R query(String statement, Class<R> obj) {
		if(obj.isAssignableFrom(List.class))
			return (R) selectList(statement);
			
		return (R) selectOne(statement);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public <R> R query(String statement, Object parameter, Class<R> obj) {
		if(obj.isAssignableFrom(List.class))
			return (R) selectList(statement, parameter);
			
		return (R) selectOne(statement, parameter);
	}

	private static final class CubeMyBatisDAOImplAssist{
		
		private static final String QUERY_PAGINATION_LIST = "queryPaginationList";
		
		private static final String getQueryPaginationCountStatement(String statement){
			return new StringBuilder(statement).append("Count").toString();
		}
		
		private static final void addObjectOperationHistory(Object obj){
			
			if(!(obj instanceof IObjectOperationHistory))
				return;
			
			IObjectOperationHistory objectOperation = (IObjectOperationHistory)obj;
			objectOperation.setCreateTime(new Date(System.currentTimeMillis()));
			
			ICoreUser coreUser = CoreUserContextHolder.getContext();
			
			if(null == coreUser)
				return;
			
			objectOperation.setCreateUser(coreUser.getUsername());
				
		}
		
		private static final void updateObjectOperationHistory(Object obj){
			
			if(!(obj instanceof IObjectOperationHistory))
				return;
			
			IObjectOperationHistory objectOperation = (IObjectOperationHistory)obj;
			objectOperation.setModifyTime(new Date(System.currentTimeMillis()));
			
			ICoreUser coreUser = CoreUserContextHolder.getContext();
			
			if(null == coreUser)
				return;
			
			objectOperation.setModifyUser(coreUser.getUsername());
				
		}
		
	}
	
}
