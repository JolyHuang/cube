package com.sharingif.cube.core.util;

import com.sharingif.cube.core.exception.CubeException;
import com.sharingif.cube.core.exception.CubeRuntimeException;
import com.sharingif.cube.core.exception.ICubeException;
import com.sharingif.cube.core.exception.ICubeRuntimeException;
import com.sharingif.cube.core.exception.UnknownCubeException;

/**
 * CubeException 处理工具
 * 2015年8月8日 下午2:01:08
 * @author Joly
 * @version v1.0
 * @since v1.0
 */
public class CubeExceptionUtil {
	
	
	/**
	 * exception转换为CubeException抛出
	 * @param exception
	 * @throws CubeException
	 */
	public static void throwCubeException(Exception exception) throws CubeException {

		if(exception instanceof ICubeRuntimeException)
			throw (CubeRuntimeException)exception;

		if(exception instanceof ICubeException)
			throw (CubeException)exception;
		
		throw new UnknownCubeException(exception);
	}
	
	/**
	 * exception转换为CubeRuntimeException抛出
	 * @param exception
	 */
	public static void throwCubeRuntimeException(Exception exception) {
		if(exception instanceof ICubeRuntimeException)
			throw (RuntimeException)exception;
		
		if(exception instanceof ICubeException){
			CubeException cubeException = (CubeException)exception;
			CubeRuntimeException cubeRuntimeException = new CubeRuntimeException(cubeException.getMessage(), cubeException.getArgs(), cubeException.getCause());
			cubeRuntimeException.setLocalizedMessage(cubeException.getLocalizedMessage());
			
			throw cubeRuntimeException;
		}
		
		throw new UnknownCubeException(exception);
	}

}
