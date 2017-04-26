package com.sharingif.cube.communication.transport.transform;

import com.sharingif.cube.communication.transport.transform.exception.MarshallerException;

/**
 * 数据格式转换
 * 2016年3月30日 下午7:23:59
 * @author Joly
 * @version v1.0
 * @since v1.0
 */
public interface Marshaller<I,O> {
	
	O marshaller(I data) throws MarshallerException;

}
