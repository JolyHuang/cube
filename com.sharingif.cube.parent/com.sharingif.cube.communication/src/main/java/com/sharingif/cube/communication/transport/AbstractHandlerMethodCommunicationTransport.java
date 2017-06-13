package com.sharingif.cube.communication.transport;

import com.sharingif.cube.communication.exception.IBusinessCommunicationExceptionHandler;
import com.sharingif.cube.core.handler.HandlerMethod;

/**
 * AbstractHandlerMethodCommunicationTransport
 * 2017年1月16日 下午4:30:32
 * @author Joly
 * @version v1.0
 * @since v1.0
 */
public abstract class AbstractHandlerMethodCommunicationTransport<MI,MO,CI,CO,UI,UO> extends AbstractHandlerMethodTransport<MI,MO,UI,UO> implements CommunicationTransport<MI,MO,CI,CO,UI,UO> {

	protected AbstractHandlerMethodCommunicationTransport(HandlerMethod handlerMethod) {
		super(handlerMethod);
	}

	private Connection<CI,CO> connection;
	private IBusinessCommunicationExceptionHandler<UO> businessCommunicationExceptionHandler;

	public void setConnection(Connection<CI, CO> connection) {
		this.connection = connection;
	}
	public Connection<CI, CO> getConnection() {
		return connection;
	}
	public IBusinessCommunicationExceptionHandler<UO> getBusinessCommunicationExceptionHandler() {
		return businessCommunicationExceptionHandler;
	}
	public void setBusinessCommunicationExceptionHandler(
			IBusinessCommunicationExceptionHandler<UO> businessCommunicationExceptionHandler) {
		this.businessCommunicationExceptionHandler = businessCommunicationExceptionHandler;
	}
	
}
