package com.sharingif.cube.core.transport.transform;

import com.sharingif.cube.core.transport.exception.MarshallerException;

/**
 * 数据转换器
 * 2017年2月25日 下午9:48:14
 * @author Joly
 * @version v1.0
 * @since v1.0
 */
public interface Transform<MI,MO,UI,UO>{
	
	void setMarshaller(Marshaller<MI,MO> marshaller);
	
	void setUnmarshaller(Marshaller<UI,UO> unmarshaller);
	
	MO marshaller(MI data) throws MarshallerException;
	
	UO unmarshaller(UI data) throws MarshallerException;
	
}
