package com.sharingif.cube.core.handler.chain;

import com.sharingif.cube.core.exception.CubeException;
import com.sharingif.cube.core.exception.ICubeException;

/**
 * 日志监控
 * 2015年8月1日 下午4:25:07
 * @author Joly
 * @version v1.0
 * @since v1.0
 */
public class MonitorPerformanceChain extends AbstractHandlerMethodChain {
	
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

		this.logger.info("{} begin===> thdId:{}, method:{}, trsId:{}"
				,name
				,Thread.currentThread().getId()
				,content.getRequestContext().getMethod()
				,content.getRequestContext().getLookupPath()
				);
		
	}

	@Override
	public void after(HandlerMethodContent content) throws CubeException {
		Long beginCurrentTime = content.getCacheData(BEGIN_CURRENT_TIME);
		
		Long endCurrentTime = System.currentTimeMillis();
		
		this.logger.info("{} end===> thdId:{}, method:{}, trsId:{}, exTime:{}"
				,name
				,Thread.currentThread().getId()
				,content.getRequestContext().getMethod()
				,content.getRequestContext().getLookupPath()
				,(endCurrentTime-beginCurrentTime)
				);
		
	}

	@Override
	public void exception(HandlerMethodContent content, Exception exception) throws CubeException {
		
		Long beginCurrentTime = content.getCacheData(BEGIN_CURRENT_TIME);
		
		Long endCurrentTime = System.currentTimeMillis();

		String loggerMessage = "{} error===> thdId:{}, method:{}, trsId:{}, exTime:{}, message:{}, localizedMessage:{}";

		String message = null;
		String localizedMessage = null;
		if(exception instanceof ICubeException) {
			ICubeException cubeException = ((ICubeException)exception);
			message = cubeException.getMessage();
			localizedMessage = cubeException.getLocalizedMessage();
		}

		if(null == exception.getCause()) {
			this.logger.error(loggerMessage
					,name
					,Thread.currentThread().getId()
					,content.getRequestContext().getMethod()
					,content.getRequestContext().getLookupPath()
					,(endCurrentTime-beginCurrentTime)
					,message
					,localizedMessage
			);

			return;
		}

		this.logger.error(loggerMessage
				,name
				,Thread.currentThread().getId()
				,content.getRequestContext().getMethod()
				,content.getRequestContext().getLookupPath()
				,(endCurrentTime-beginCurrentTime)
				,message
				,localizedMessage
				,exception
		);

	}
	
}
