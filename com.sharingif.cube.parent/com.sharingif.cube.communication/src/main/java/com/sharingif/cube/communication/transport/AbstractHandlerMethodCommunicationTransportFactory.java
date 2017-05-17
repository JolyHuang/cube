package com.sharingif.cube.communication.transport;

import com.sharingif.cube.core.request.RequestInfo;

/**
 * AbstractHandlerMethodCommunicationTransportFactory
 * 2017年1月9日 下午8:56:38
 * @author Joly
 * @version v1.0
 * @since v1.0
 */
public abstract class AbstractHandlerMethodCommunicationTransportFactory<MI,MO,CO,UI,UO> extends AbstractHandlerMethodTransportFactory<MI,MO,CO,UI,UO> implements HandlerMethodCommunicationTransportFactory<MI,MO,CO,UI,UO>  {
	
	private Connection<RequestInfo<MO>,CO> connection;
	
	public Connection<RequestInfo<MO>, CO> getConnection() {
		return connection;
	}
	public void setConnection(Connection<RequestInfo<MO>, CO> connection) {
		this.connection = connection;
	}
	
}
