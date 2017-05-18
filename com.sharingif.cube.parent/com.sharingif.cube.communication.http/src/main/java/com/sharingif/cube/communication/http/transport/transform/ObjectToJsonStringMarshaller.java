package com.sharingif.cube.communication.http.transport.transform;

import java.util.TimeZone;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sharingif.cube.core.config.CubeConfigure;
import com.sharingif.cube.core.transport.exception.MarshallerException;
import com.sharingif.cube.core.transport.transform.Marshaller;

/**
 * 对象数组转json字符串
 * 2017年1月7日 下午8:32:16
 * @author Joly
 * @version v1.0
 * @since v1.0
 */
public class ObjectToJsonStringMarshaller implements Marshaller<Object[], String> {
	
	private ObjectMapper objectMapper;
	
	public ObjectToJsonStringMarshaller() {
		objectMapper = new ObjectMapper();
		objectMapper.setTimeZone(TimeZone.getTimeZone(CubeConfigure.DEFAULT_TIME_ZONE));
	}
	
	public ObjectToJsonStringMarshaller(ObjectMapper objectMapper) {
		this.objectMapper = objectMapper;
	}

	@Override
	public String marshaller(Object[] data) throws MarshallerException {
		try {
			if(data == null || data.length == 0) {
				return null;
			}
			
			return objectMapper.writeValueAsString((data.length > 1) ? data : data[0]);
		} catch (Exception e) {
			throw new MarshallerException("marshaller object to json error", e);
		}
	}

}
