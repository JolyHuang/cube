package com.sharingif.cube.core.handler.chain;

import com.sharingif.cube.core.exception.CubeException;
import com.sharingif.cube.core.handler.HandlerMethodContent;

/**
 * 日志监控
 * 2015年8月1日 下午4:25:07
 * @author Joly
 * @version v1.0
 * @since v1.0
 */
public class MonitorPerformanceChain extends AbstractHandlerMethodChain<HandlerMethodContent> {
	
	private static final String BEGIN_CURRENT_TIME = "_beginCurrentTime";

	private String name = "controller";
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public void before(HandlerMethodContent content) throws CubeException {
		Long beginCurrentTime = System.currentTimeMillis();
		content.addacheData(BEGIN_CURRENT_TIME, beginCurrentTime);

		this.logger.info("{} begin===> ThdId:{}, method:{}, TrsId:{}"
				,name
				,Thread.currentThread().getId()
				,content.getRequestInfo().getMethod()
				,content.getRequestInfo().getLookupPath()
				);
		
	}

	@Override
	public void after(HandlerMethodContent content) throws CubeException {
		Long beginCurrentTime = content.getCacheData(BEGIN_CURRENT_TIME);
		
		Long endCurrentTime = System.currentTimeMillis();
		
		this.logger.info("{} end===> ThdId:{}, method:{}, TrsId:{}, ExTime:{}"
				,name
				,Thread.currentThread().getId()
				,content.getRequestInfo().getMethod()
				,content.getRequestInfo().getLookupPath()
				,(endCurrentTime-beginCurrentTime)
				);
		
	}

	@Override
	public void exception(HandlerMethodContent content, Exception exception) throws CubeException {
		
		Long beginCurrentTime = content.getCacheData(BEGIN_CURRENT_TIME);
		
		Long endCurrentTime = System.currentTimeMillis();
		
		String loggerMessage = "{} error===> ThdId:{}, method:{}, TrsId:{}, ExTime:{} message:{}";
			
		this.logger.error(loggerMessage
				,name
				,Thread.currentThread().getId()
				,content.getRequestInfo().getMethod()
				,content.getRequestInfo().getLookupPath()
				,(endCurrentTime-beginCurrentTime)
				,exception.getMessage()
				);
		
	}
	
}
