package com.sharingif.cube.core.handler.exception.handler;

import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sharingif.cube.core.exception.ICubeException;
import com.sharingif.cube.core.exception.handler.AbstractCubeExceptionHandler;
import com.sharingif.cube.core.exception.handler.ExceptionContent;
import com.sharingif.cube.core.handler.HandlerMethod;
import com.sharingif.cube.core.handler.request.RequestLocalContextHolder;
import com.sharingif.cube.core.request.RequestContext;
import com.sharingif.cube.core.util.StringUtils;

/**
 * AbstractCubeExceptionHandler
 * 2015年8月2日 下午11:22:46
 * @author Joly
 * @version v1.0
 * @since v1.0
 */
public abstract class AbstractCubeHandlerMethodExceptionHandler<RI extends RequestContext<?>> extends AbstractCubeExceptionHandler<RI,HandlerMethod> {
	
	protected final Logger logger = LoggerFactory.getLogger(getClass());
	
	@Override
	public void resolverMessages(ICubeException cubeException, Locale locale) {
		
		if(!StringUtils.isEmpty(cubeException.getLocalizedMessage())&&!(cubeException.getMessage().equals(cubeException.getLocalizedMessage()))){
			return;
		}

		try {
			Object[] args = cubeException.getArgs();
			if(null != args){
				for(int i=0; i<args.length; i++){
					try {
						args[i] = super.getApplicationContext().getMessage((String)args[i], null, locale);
					} catch (Exception e) {
						this.logger.error("ICubeExceptionResolver error,message {} is not find in the locale source file", cubeException.getMessage());
					}
				}
			}
			String localizedMessage = super.getApplicationContext().getMessage(cubeException.getMessage(), args, locale);
			cubeException.setLocalizedMessage(localizedMessage);
		} catch (Exception e) {
			this.logger.error("ICubeExceptionResolver error,message {} is not find in the locale source file", cubeException.getMessage());
		}
	}
	
	public void wirteLogInternal(RI requestContext, HandlerMethod handlerMethod, ICubeException cubeException, Locale locale, Long exTime) {
		String loggerMessage = "transaction error===> thdId:{}, method:{}, trsId:{}, exTime:{}, message:{}, localizedMessage:{}";
		
		
		if(null == ((Exception)cubeException).getCause()){
			
			this.logger.error(loggerMessage
					,Thread.currentThread().getId()
					,requestContext.getMethod()
					,requestContext.getLookupPath()
					,exTime
					,cubeException.getMessage()
					,cubeException.getLocalizedMessage()
					);
			return ;
		}
		
		this.logger.error(loggerMessage
				,Thread.currentThread().getId()
				,requestContext.getMethod()
				,requestContext.getLookupPath()
				,exTime
				,cubeException.getMessage()
				,cubeException.getLocalizedMessage()
				,cubeException
				);
	}
	
	@Override
	public void wirteLog(RI requestContext, HandlerMethod handlerMethod, ICubeException cubeException) {
		Long beginCurrentTime = RequestLocalContextHolder.getRequestDateTime();
		if(beginCurrentTime == null) {
			beginCurrentTime = Long.valueOf(0);
		}
		Long endCurrentTime = System.currentTimeMillis();
		wirteLogInternal(requestContext, handlerMethod, cubeException, requestContext.getLocale(), (endCurrentTime-beginCurrentTime));
		
	}
	
	/**
	 * @param requestContext : 错误输入信息
	 * @param handlerMethod : 请求处理器
	 * @param exception : 异常
	 * @return O : 异常处理结果
	 */
	public ExceptionContent handler(RI requestContext, HandlerMethod handlerMethod, Exception exception) {
		
		if(supports(exception)){
			ICubeException cubeException = convertException(exception);
			
			resolverMessages(cubeException, requestContext.getLocale());
			
			wirteLog(requestContext, handlerMethod, cubeException);

			ExceptionContent result = handlerException(requestContext, handlerMethod, cubeException);
			result.setCubeException(cubeException);
			
			return result;
		}
		
		return null;
	}
	
	

}
