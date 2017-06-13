package com.sharingif.cube.communication.transport;

import com.sharingif.cube.communication.exception.IBusinessCommunicationExceptionHandler;

/**
 * AbstractHandlerMethodCommunicationTransportFactory
 * 2017年1月9日 下午8:56:38
 * @author Joly
 * @version v1.0
 * @since v1.0
 */
public abstract class AbstractHandlerMethodCommunicationTransportFactory<MI,MO,CI,CO,UI,UO> extends AbstractHandlerMethodTransportFactory<MI,MO,CI,CO,UI,UO> implements HandlerMethodCommunicationTransportFactory<MI,MO,CI,CO,UI,UO>  {
	
	private Connection<CI,CO> connection;
	private IBusinessCommunicationExceptionHandler<UO> businessCommunicationExceptionHandler;
	
	public Connection<CI, CO> getConnection() {
		return connection;
	}
	public void setConnection(Connection<CI, CO> connection) {
		this.connection = connection;
	}
	public IBusinessCommunicationExceptionHandler<UO> getBusinessCommunicationExceptionHandler() {
		return businessCommunicationExceptionHandler;
	}
	public void setBusinessCommunicationExceptionHandler(
			IBusinessCommunicationExceptionHandler<UO> businessCommunicationExceptionHandler) {
		this.businessCommunicationExceptionHandler = businessCommunicationExceptionHandler;
	}
	
}
