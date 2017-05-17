package com.sharingif.cube.communication.transport;

import com.sharingif.cube.core.handler.HandlerMethod;
import com.sharingif.cube.core.request.RequestInfo;

/**
 * AbstractRemoteHandlerMethodTransport
 * 2017年1月16日 下午4:30:32
 * @author Joly
 * @version v1.0
 * @since v1.0
 */
public abstract class AbstractHandlerMethodCommunicationTransport<MI,MO,CO,UI,UO> extends AbstractHandlerMethodTransport<MI, MO, UI, UO> implements CommunicationTransport<MI,MO,CO,UI,UO> {

	protected AbstractHandlerMethodCommunicationTransport(HandlerMethod handlerMethod) {
		super(handlerMethod);
	}

	private Connection<RequestInfo<MO>,CO> connection;

	public void setConnection(Connection<RequestInfo<MO>, CO> connection) {
		this.connection = connection;
	}
	public Connection<RequestInfo<MO>, CO> getConnection() {
		return connection;
	}
	
}
