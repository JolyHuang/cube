package com.sharingif.cube.communication.transport;

import com.sharingif.cube.communication.transport.transform.Transform;
import com.sharingif.cube.core.exception.CubeException;

/**
 * 通讯处理
 * 2016年4月12日 下午8:11:53
 * @author Joly
 * @version v1.0
 * @since v1.0
 */
public interface Transport<I,MI,MO,UI,UO,CI,CO> {
	
	void setTransform(Transform<MI,MO,UI,UO> transform);
	
	void setConnection(Connection<CI,CO> connection);

	Object doTransport(I obj) throws CubeException ;
	
}
