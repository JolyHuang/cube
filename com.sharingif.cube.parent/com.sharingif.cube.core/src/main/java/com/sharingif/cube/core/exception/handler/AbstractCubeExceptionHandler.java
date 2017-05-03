package com.sharingif.cube.core.exception.handler;

import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ApplicationObjectSupport;

import com.sharingif.cube.core.exception.ICubeException;
import com.sharingif.cube.core.request.RequestInfo;
import com.sharingif.cube.core.util.StringUtils;

/**
 * AbstractCubeExceptionHandler
 * 2015年8月2日 下午11:22:46
 * @author Joly
 * @version v1.0
 * @since v1.0
 */
public abstract class AbstractCubeExceptionHandler<RI,O extends ExceptionContent, H extends Object> extends ApplicationObjectSupport implements IExceptionHandler<RI,O,H> {
	
	protected final Logger logger = LoggerFactory.getLogger(getClass());
	
	@Override
	public ICubeException convertException(Exception exception) {
		return (ICubeException)exception;
	}

	@Override
	public void resolverMessages(ICubeException cubeException, Locale locale) {
		
		if(!StringUtils.isEmpty(cubeException.getLocalizedMessage())&&!(cubeException.getMessage().equals(cubeException.getLocalizedMessage()))){
			return;
		}

		try {
			Object[] args = cubeException.getArgs();
			if(null != args){
				for(int i=0; i<args.length; i++){
					Object messageKey = args[i];
					if(!(messageKey instanceof String)) {
						continue;
					}
					try {
						args[i] = super.getApplicationContext().getMessage((String)messageKey, null, locale);
					} catch (Exception e) {
						this.logger.error("ICubeExceptionResolver error,message args key {} is not find in the locale source file", messageKey);
					}
				}
			}
			String localizedMessage = super.getApplicationContext().getMessage(cubeException.getMessage(), args, locale);
			cubeException.setLocalizedMessage(localizedMessage);
		} catch (Exception e) {
			this.logger.error("ICubeExceptionResolver error,message {} is not find in the locale source file", cubeException.getMessage());
		}
	}
	
	/**
	 * @param obj : 错误输入信息
	 * @param H handler : 请求处理器
	 * @param exception : 异常
	 * @return O : 异常处理结果
	 */
	public O handler(RequestInfo<RI> requestInfo, H handler, Exception exception) {
		
		if(supports(exception)){
			ICubeException cubeException = convertException(exception);
			
			resolverMessages(cubeException, requestInfo.getLocale());
			
			wirteLog(requestInfo, handler, cubeException);
			
			O result = handlerException(requestInfo, handler, cubeException);
			result.setCubeException(cubeException);
			
			return result;
		}
		
		return null;
	}
	
	

}
