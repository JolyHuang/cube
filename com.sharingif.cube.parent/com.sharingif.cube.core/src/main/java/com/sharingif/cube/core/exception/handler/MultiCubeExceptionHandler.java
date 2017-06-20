package com.sharingif.cube.core.exception.handler;

import com.sharingif.cube.core.exception.ICubeException;
import com.sharingif.cube.core.request.RequestInfo;
import org.springframework.beans.factory.InitializingBean;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 组合多种异常处理器当有异常需要处理时按照顺序选择合适的处理器处理,
 * 如果在处理异常过程中产生了新的异常将选择最后一个异常处理器对异常进行处理
 * 2015年8月7日 上午12:34:00
 * @author Joly
 * @version v1.0
 * @since v1.0
 */
public class MultiCubeExceptionHandler<RI, H extends Object> extends AbstractCubeExceptionHandler<RI,H> implements InitializingBean {
	
	private Map<String,AbstractCubeExceptionHandler<RI,H>> cacheExceptionHandlers = new HashMap<String,AbstractCubeExceptionHandler<RI,H>>(20);
	
	private List<AbstractCubeExceptionHandler<RI,H>> cubeExceptionHandlers;
	private ExceptionMessageConversion exceptionMessageConversion;
	
	public List<AbstractCubeExceptionHandler<RI,H>> getCubeExceptionHandlers() {
		return cubeExceptionHandlers;
	}

	public void setCubeExceptionHandlers(List<AbstractCubeExceptionHandler<RI,H>> cubeExceptionHandlers) {
		this.cubeExceptionHandlers = cubeExceptionHandlers;
	}
	public ExceptionMessageConversion getExceptionMessageConversion() {
		return exceptionMessageConversion;
	}
	public void setExceptionMessageConversion(ExceptionMessageConversion exceptionMessageConversion) {
		this.exceptionMessageConversion = exceptionMessageConversion;
	}

	@Override
	public boolean supports(Exception exception) {
		return true;
	}

	@Override
	public ICubeException convertException(Exception exception) {
		return null;
	}
	
	@Override
	public void wirteLog(RequestInfo<RI> requestInfo, H handler, ICubeException cubeException) {
	}
	
	@Override
	public ExceptionContent handlerException(RequestInfo<RI> requestInfo, H handler, ICubeException cubeException) {
		return null;
	}
	
	@Override
	public ExceptionContent handler(RequestInfo<RI> requestInfo, H handler, Exception exception) {
		try {
			return handlerInternal(requestInfo, handler, exception);
		} catch (Exception e) {
			this.logger.error("execute cubeExceptionHandler error", e);
			return cubeExceptionHandlers.get(cubeExceptionHandlers.size()-1).handler(requestInfo, handler, e);
		}
	}
	
	protected ExceptionContent handlerInternal(RequestInfo<RI> requestInfo, H handler, Exception exception) {
		String cacheKey = exception.getClass().getName();
		AbstractCubeExceptionHandler<RI,H> cacheExceptionHandler = cacheExceptionHandlers.get(cacheKey);
		
		if(cacheExceptionHandler != null){
			return cacheExceptionHandler.handler(requestInfo, handler, exception);
		}
		
		for(AbstractCubeExceptionHandler<RI,H> cubeExceptionHandler : cubeExceptionHandlers){
			if(cubeExceptionHandler.supports(exception)) {
				ExceptionContent result = cubeExceptionHandler.handler(requestInfo, handler, exception);
				cacheExceptionHandlers.put(cacheKey, cubeExceptionHandler);
				return result;
			}
			
		}
		
		return null;
	}


	@Override
	public void afterPropertiesSet() throws Exception {
		if((getCubeExceptionHandlers() != null) && (getExceptionMessageConversion() != null)) {
			for(AbstractCubeExceptionHandler<RI,H> abstractCubeExceptionHandler : getCubeExceptionHandlers()) {
				abstractCubeExceptionHandler.setExceptionMessageConversion(getExceptionMessageConversion());
			}
		}
	}
}
