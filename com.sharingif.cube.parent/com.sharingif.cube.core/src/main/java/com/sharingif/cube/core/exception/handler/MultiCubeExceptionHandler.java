package com.sharingif.cube.core.exception.handler;

import java.util.List;

import com.sharingif.cube.core.exception.ICubeException;
import com.sharingif.cube.core.request.RequestInfo;

/**
 * 组合多种异常处理器当有异常需要处理时按照顺序选择合适的处理器处理,
 * 如果在处理异常过程中产生了新的异常将选择最后一个异常处理器对异常进行处理
 * 2015年8月7日 上午12:34:00
 * @author Joly
 * @version v1.0
 * @since v1.0
 */
public class MultiCubeExceptionHandler<RI, O extends ExceptionContent, H extends Object> extends AbstractCubeExceptionHandler<RI,O,H> {
	
	private List<AbstractCubeExceptionHandler<RI,O,H>> cubeExceptionHandlers;
	
	public List<AbstractCubeExceptionHandler<RI,O,H>> getCubeExceptionHandlers() {
		return cubeExceptionHandlers;
	}

	public void setCubeExceptionHandlers(List<AbstractCubeExceptionHandler<RI,O,H>> cubeExceptionHandlers) {
		this.cubeExceptionHandlers = cubeExceptionHandlers;
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
	public O handlerException(RequestInfo<RI> requestInfo, H handler, ICubeException cubeException) {
		return null;
	}
	
	@Override
	public O handler(RequestInfo<RI> requestInfo, H handler, Exception exception) {
		try {
			return handlerInternal(requestInfo, handler, exception);
		} catch (Exception e) {
			this.logger.error("execute cubeExceptionHandler error", e);
			return cubeExceptionHandlers.get(cubeExceptionHandlers.size()-1).handler(requestInfo, handler, e);
		}
	}
	
	protected O handlerInternal(RequestInfo<RI> requestInfo, H handler, Exception exception) {
		
		for(AbstractCubeExceptionHandler<RI,O,H> cubeExceptionHandler : cubeExceptionHandlers){
			if(cubeExceptionHandler.supports(exception)) {
				O result = cubeExceptionHandler.handler(requestInfo, handler, exception);
				return result;
			}
			
		}
		
		return null;
	}


}
