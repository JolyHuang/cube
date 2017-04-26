package com.sharingif.cube.persistence.mybatis.dao;

import java.io.Serializable;

import org.mybatis.spring.SqlSessionTemplate;

import com.sharingif.cube.persistence.database.dao.IBaseDAO;
import com.sharingif.cube.persistence.database.pagination.PaginationCondition;
import com.sharingif.cube.persistence.database.pagination.PaginationRepertory;

/**   
 *  
 * @Description:  [MyBatis Batis DAO]   
 * @Author:       [Joly]   
 * @CreateDate:   [2014年1月15日 下午3:17:26]   
 * @UpdateUser:   [Joly]   
 * @UpdateDate:   [2014年1月15日 下午3:17:26]   
 * @UpdateRemark: [说明本次修改内容]  
 * @Version:      [v1.0] 
 *    
 */
public interface ICubeMyBatisDAO<T, ID extends Serializable> extends IBaseDAO<T, ID>  {
	
	void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate);
	

	<R> R query(String statement, Class<R> obj);
	
	<R> R query(String statement, Object parameter, Class<R> obj);
	
	/**
	 * 分页总条数
	 * @param paginationCondition : 查询条件
	 * @return
	 */
	int queryPaginationCount(String countStatement, PaginationCondition<? extends Object> paginationCondition);
	
	/**
	 * 分页查询，默认查询sql id为statement+Count
	 * @param statement : 查询list sql id
	 * @param paginationCondition : 查询条件
	 * @return
	 */
	PaginationRepertory<T> queryPagination(String statement, PaginationCondition<? extends Object> paginationCondition);
	/**
	 * 分页查询，默认查询sql id为statement+Count
	 * @param statement : 查询list sql id
	 * @param paginationCondition : 查询条件
	 * @param isQueryCount : 是否查询总条数
	 * @return
	 */
	PaginationRepertory<T> queryPagination(String statement, PaginationCondition<? extends Object> paginationCondition, boolean isQueryCount);
	
	/**
	 * 分页查询
	 * @param countStatement : 查询总数sql id
	 * @param statement : 查询list sql id
	 * @param paginationCondition : 查询条件
	 * @return
	 */
	PaginationRepertory<T> queryPagination(String countStatement, String statement, PaginationCondition<? extends Object> paginationCondition);
	/**
	 * 分页查询
	 * @param countStatement : 查询总数sql id
	 * @param statement : 查询list sql id
	 * @param paginationCondition : 查询条件
	 * @param isQueryCount : 是否查询总条数
	 * @return
	 */
	PaginationRepertory<T> queryPagination(String countStatement, String statement, PaginationCondition<? extends Object> paginationCondition, boolean isQueryCount);

}
