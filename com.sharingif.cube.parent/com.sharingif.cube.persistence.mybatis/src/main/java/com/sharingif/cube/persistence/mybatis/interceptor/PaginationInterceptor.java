package com.sharingif.cube.persistence.mybatis.interceptor;

import java.util.Properties;

import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;

import com.sharingif.cube.persistence.database.pagination.PaginationCondition;

/**
 * MyBatis 分页拦截器 2017年5月8日 上午10:41:42
 * 
 * @author Joly
 * @version v1.0
 * @since v1.0
 */
@Intercepts({ @Signature(type = Executor.class, method = "query", args = { PaginationCondition.class }) })
public class PaginationInterceptor implements Interceptor {

	@Override
	public Object intercept(Invocation invocation) throws Throwable {
//		Object[] args = invocation.getArgs();
		
		return null;
	}

	@Override
	public Object plugin(Object target) {
		if (target instanceof PaginationCondition) {
			return Plugin.wrap(target, this);
		}

		return target;
	}

	@Override
	public void setProperties(Properties properties) {

	}

}
