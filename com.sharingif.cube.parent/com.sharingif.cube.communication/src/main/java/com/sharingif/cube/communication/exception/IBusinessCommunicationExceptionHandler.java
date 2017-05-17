package com.sharingif.cube.communication.exception;

/**
 * 业务通讯异常处理器
 * 2016年4月12日 下午7:58:38
 * @author Joly
 * @version v1.0
 * @since v1.0
 */
public interface IBusinessCommunicationExceptionHandler<T extends Object> {

	void handleCommunicationException(T obj) throws BusinessCommunicationException ;
	
}
