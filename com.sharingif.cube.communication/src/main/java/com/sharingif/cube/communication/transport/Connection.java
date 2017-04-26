package com.sharingif.cube.communication.transport;

import com.sharingif.cube.communication.exception.CommunicationException;

/**
 * Connection
 * 2016年12月27日 下午12:07:25
 * @author Joly
 * @version v1.0
 * @since v1.0
 */
public interface Connection<I,O> {

	O connect(I obj) throws CommunicationException;
	
}
