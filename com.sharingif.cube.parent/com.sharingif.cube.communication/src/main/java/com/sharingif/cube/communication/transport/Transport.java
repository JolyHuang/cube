package com.sharingif.cube.communication.transport;

import com.sharingif.cube.communication.transport.transform.Transform;
import com.sharingif.cube.core.exception.CubeException;
import com.sharingif.cube.core.request.RequestInfo;

/**
 * Transport
 * 2016年4月12日 下午8:11:53
 * @author Joly
 * @version v1.0
 * @since v1.0
 */
public interface Transport<MI,MO,UI,UO> {
	
	void setTransform(Transform<MI,MO,UI,UO> transform);
	
	Object doTransport(RequestInfo<MI> obj) throws CubeException ;
	
}
