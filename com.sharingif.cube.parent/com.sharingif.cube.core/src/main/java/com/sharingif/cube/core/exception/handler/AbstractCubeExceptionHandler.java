package com.sharingif.cube.core.exception.handler;

import java.lang.reflect.Field;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ApplicationObjectSupport;

import com.sharingif.cube.core.exception.ICubeException;
import com.sharingif.cube.core.exception.UnknownCubeException;
import com.sharingif.cube.core.request.RequestContext;
import com.sharingif.cube.core.util.StringUtils;

/**
 * AbstractCubeExceptionHandler
 * 2015年8月2日 下午11:22:46
 * @author Joly
 * @version v1.0
 * @since v1.0
 */
public abstract class AbstractCubeExceptionHandler<RI extends RequestContext<?>, H extends Object> extends ApplicationObjectSupport implements IExceptionHandler<RI,H> {


	private static final Field DETAIL_MESSAGE_FIELD;

	static {
		try {
			DETAIL_MESSAGE_FIELD = Throwable.class.getDeclaredField("detailMessage");
		} catch (NoSuchFieldException e) {
			throw new RuntimeException(e);
		}
	}

	protected final Logger logger = LoggerFactory.getLogger(getClass());

	@Override
	public ICubeException convertException(Exception exception) {
		ICubeException cubeException = convertExceptionInternal(exception);
		convertExceptionMessage(cubeException);
		return cubeException;
	}

	protected ICubeException convertExceptionInternal(Exception exception) {
		return (ICubeException)exception;
	}

	protected void convertExceptionMessage(ICubeException exception) {

		String message = ExceptionMessageConversion.convert(exception.getMessage());
		if(StringUtils.isTrimEmpty(message)) {
			return;
		}

		try {

			DETAIL_MESSAGE_FIELD.setAccessible(true);

			DETAIL_MESSAGE_FIELD.set(exception, message);

		} catch (IllegalAccessException e) {
			logger.error("illegal access exception", e);
			throw new UnknownCubeException();
		}

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
						args[i] = this.getApplicationContext().getMessage((String)messageKey, null, locale);
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
	 * @param requestContext : 错误输入信息
	 * @param handler : 请求处理器
	 * @param exception : 异常
	 * @return O : 异常处理结果
	 */
	public ExceptionContent handler(RI requestContext, H handler, Exception exception) {

		ICubeException cubeException = convertException(exception);

		resolverMessages(cubeException, requestContext.getLocale());

		wirteLog(requestContext, handler, cubeException);

		ExceptionContent result = handlerException(requestContext, handler, cubeException);
		result.setCubeException(cubeException);

		return result;

	}



}
