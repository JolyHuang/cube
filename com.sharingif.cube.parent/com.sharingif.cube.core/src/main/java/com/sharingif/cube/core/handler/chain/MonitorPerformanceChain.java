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
	
	private static final ThreadLocal<String> CONTEXT_HOLDER = new ThreadLocal<String>();
	
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
		CONTEXT_HOLDER.set(String.valueOf(beginCurrentTime));
		
		this.logger.info("{} begin===> ThdId:{}, TrsId:{}.{}"
				,name
				,Thread.currentThread().getId()
				,content.getObj().getClass().getName()
				,content.getMethod().getName()
				);
		
	}

	@Override
	public void after(HandlerMethodContent content) throws CubeException {
		Long beginCurrentTime = Long.valueOf(CONTEXT_HOLDER.get());
		
		Long endCurrentTime = System.currentTimeMillis();
		
		this.logger.info("{} end===> ThdId:{}, TrsId:{}.{}, ExTime:{}"
				,name
				,Thread.currentThread().getId()
				,content.getObj().getClass().getName()
				,content.getMethod().getName()
				,(endCurrentTime-beginCurrentTime)
				);
		
	}

	@Override
	public void exception(HandlerMethodContent content, Exception exception) throws CubeException {
		
		Long beginCurrentTime = Long.valueOf(CONTEXT_HOLDER.get());
		
		Long endCurrentTime = System.currentTimeMillis();
		
		String loggerMessage = "{} error===> ThdId:{}, TrsId:{}.{}, ExTime:{} \nmessage:{} \nlocalizedMessage:{}";
			
		this.logger.error(loggerMessage
				,name
				,Thread.currentThread().getId()
				,content.getObj().getClass().getName()
				,content.getMethod().getName()
				,(endCurrentTime-beginCurrentTime)
				,exception.getMessage()
				,exception.getLocalizedMessage()
				);
		
	}
	
}
