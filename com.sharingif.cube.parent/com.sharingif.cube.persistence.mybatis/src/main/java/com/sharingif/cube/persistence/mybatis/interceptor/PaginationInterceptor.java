package com.sharingif.cube.persistence.mybatis.interceptor;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.executor.CachingExecutor;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ResultMap;
import org.apache.ibatis.mapping.ResultMapping;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;

import com.sharingif.cube.persistence.database.pagination.IPaginationHandler;
import com.sharingif.cube.persistence.database.pagination.PaginationCondition;

/**
 * MyBatis 分页拦截器 2017年5月8日 上午10:41:42
 * 
 * @author Joly
 * @version v1.0
 * @since v1.0
 */
@Intercepts(
	    {
	        @Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class}),
	        @Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class, CacheKey.class, BoundSql.class}),
	    }
	)
public class PaginationInterceptor implements Interceptor {
	
	private IPaginationHandler paginationHandler;
	
	public IPaginationHandler getPaginationHandler() {
		return paginationHandler;
	}
	public void setPaginationHandler(IPaginationHandler paginationHandler) {
		this.paginationHandler = paginationHandler;
	}

	private static final String COUNT = "Count";
	private static final Map<CacheKey, MappedStatement> CACHE_MAPPED_STATEMENT = new HashMap<CacheKey, MappedStatement>(100);

	@SuppressWarnings({"rawtypes"})
	@Override
	public Object intercept(Invocation invocation) throws Throwable {
		Object[] args = invocation.getArgs();
		Object obj = args[1];
		if(!(obj instanceof PaginationCondition)) {
			return invocation.proceed();
		}
		
		
		MappedStatement mappedStatement = (MappedStatement) args[0];
		PaginationCondition paginationCondition = (PaginationCondition) args[1];
		RowBounds rowBounds = (RowBounds) args[2];
		ResultHandler resultHandler = (ResultHandler) args[3];
		
		Executor executor = (Executor) invocation.getTarget();
		
		CacheKey cacheKey = null;
        BoundSql boundSql = null;
		if(args.length == 4){
            boundSql = mappedStatement.getBoundSql(paginationCondition.getCondition());
            cacheKey = executor.createCacheKey(mappedStatement, paginationCondition.getCondition(), rowBounds, boundSql);
        } else {
            cacheKey = (CacheKey) args[4];
            boundSql = (BoundSql) args[5];
        }
		
		
		paginationCondition.setRowBounds(paginationHandler.handleRowBounds(paginationCondition.getCurrentPage(), paginationCondition.getPageSize()));
		List<Object> list = queryPagination(mappedStatement, paginationCondition, resultHandler, cacheKey, boundSql, executor);
		
		return list;
	}

	@Override
	public Object plugin(Object target) {
		if(target instanceof CachingExecutor) {
			return Plugin.wrap(target, this);
		}
		return target;
	}

	@Override
	public void setProperties(Properties properties) {

	}
	
	protected MappedStatement createCountMappedStatement(MappedStatement mappedStatement) {
		MappedStatement.Builder builder = new MappedStatement.Builder(mappedStatement.getConfiguration(), mappedStatement.getId() + COUNT, mappedStatement.getSqlSource(), mappedStatement.getSqlCommandType());
        builder.resource(mappedStatement.getResource());
        builder.fetchSize(mappedStatement.getFetchSize());
        builder.statementType(mappedStatement.getStatementType());
        builder.keyGenerator(mappedStatement.getKeyGenerator());
        if (mappedStatement.getKeyProperties() != null && mappedStatement.getKeyProperties().length != 0) {
            StringBuilder keyProperties = new StringBuilder();
            for (String keyProperty : mappedStatement.getKeyProperties()) {
                keyProperties.append(keyProperty).append(",");
            }
            keyProperties.delete(keyProperties.length() - 1, keyProperties.length());
            builder.keyProperty(keyProperties.toString());
        }
        builder.timeout(mappedStatement.getTimeout());
        builder.parameterMap(mappedStatement.getParameterMap());
        List<ResultMap> resultMaps = new ArrayList<ResultMap>();
        ResultMap resultMap = new ResultMap.Builder(mappedStatement.getConfiguration(), mappedStatement.getId(), Long.class, new ArrayList<ResultMapping>(0)).build();
        resultMaps.add(resultMap);
        builder.resultMaps(resultMaps);
        builder.resultSetType(mappedStatement.getResultSetType());
        builder.cache(mappedStatement.getCache());
        builder.flushCacheRequired(mappedStatement.isFlushCacheRequired());
        builder.useCache(mappedStatement.isUseCache());
        
        return builder.build();
	}
	
	@SuppressWarnings("rawtypes")
	protected int queryPaginationCount(MappedStatement mappedStatement, PaginationCondition paginationCondition, ResultHandler resultHandler, BoundSql boundSql, Executor executor) throws SQLException {
       CacheKey countKey = executor.createCacheKey(mappedStatement, paginationCondition.getCondition(), RowBounds.DEFAULT, boundSql);
       countKey.update(COUNT);

       MappedStatement countMappedStatement = CACHE_MAPPED_STATEMENT.get(countKey);
       
       if(countMappedStatement == null) {
    	   countMappedStatement = createCountMappedStatement(mappedStatement);
    	   CACHE_MAPPED_STATEMENT.put(countKey, countMappedStatement);
       }
       
       String countSql = paginationHandler.createCountSql(boundSql.getSql());
       countKey.update(countSql);
       BoundSql countBoundSql = new BoundSql(mappedStatement.getConfiguration(), countSql, boundSql.getParameterMappings(), paginationCondition.getCondition());
       Object countResultList = executor.query(countMappedStatement, paginationCondition.getCondition(), RowBounds.DEFAULT, resultHandler, countKey, countBoundSql);
       Long count = (Long) ((List) countResultList).get(0);
       if(count == null){
    	   return 0;
       } else {
    	   return count.intValue();
       }
	}

	@SuppressWarnings("rawtypes")
	protected List<Object> queryPagination(MappedStatement mappedStatement, PaginationCondition paginationCondition, ResultHandler resultHandler, CacheKey cacheKey, BoundSql boundSql, Executor executor) throws SQLException {
		String paginationSql = paginationHandler.createPaginationSql(boundSql.getSql(), paginationCondition.getRowBounds());
		
		BoundSql pageBoundSql = new BoundSql(mappedStatement.getConfiguration(), paginationSql, boundSql.getParameterMappings(), paginationCondition.getCondition());
		
		return executor.query(mappedStatement, paginationCondition.getCondition(), RowBounds.DEFAULT, resultHandler, cacheKey, pageBoundSql);
	}
	
	
}
