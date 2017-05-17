package com.sharingif.cube.communication.transport;

import com.sharingif.cube.core.request.RequestInfo;

/**
 * 通讯Transport
 * 2017年5月17日 下午3:36:50
 * @author Joly
 * @version v1.0
 * @since v1.0
 */
public interface CommunicationTransport<MI,MO,CO,UI,UO> extends Transport<MI,MO,UI,UO> {

	void setConnection(Connection<RequestInfo<MO>,CO> connection);

}
