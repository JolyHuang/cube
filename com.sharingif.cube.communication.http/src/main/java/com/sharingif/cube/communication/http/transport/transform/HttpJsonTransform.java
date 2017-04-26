package com.sharingif.cube.communication.http.transport.transform;

import java.util.Map;

import com.sharingif.cube.communication.JsonModel;
import com.sharingif.cube.communication.transport.transform.Marshaller;
import com.sharingif.cube.communication.transport.transform.Transform;
import com.sharingif.cube.communication.transport.transform.exception.MarshallerException;

/**
 * http json数据解析器
 * 2017年2月25日 下午10:31:19
 * @author Joly
 * @version v1.0
 * @since v1.0
 */
public class HttpJsonTransform implements Transform<Object[],String,String,JsonModel<Map<String, Object>>> {

	private Marshaller<Object[], String> marshaller;
	private Marshaller<String, JsonModel<Map<String, Object>>> unmarshaller;
	
	@Override
	public void setMarshaller(Marshaller<Object[], String> marshaller) {
		this.marshaller = marshaller;
	}

	@Override
	public void setUnmarshaller(Marshaller<String, JsonModel<Map<String, Object>>> unmarshaller) {
		this.unmarshaller = unmarshaller;
	}
	
	@Override
	public String marshaller(Object[] data) throws MarshallerException {
		return marshaller.marshaller(data);
	}

	@Override
	public JsonModel<Map<String, Object>> unmarshaller(String data) throws MarshallerException {
		return unmarshaller.marshaller(data);
	}

}
