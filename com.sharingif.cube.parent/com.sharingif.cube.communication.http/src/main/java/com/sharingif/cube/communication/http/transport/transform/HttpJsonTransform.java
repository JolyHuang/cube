package com.sharingif.cube.communication.http.transport.transform;

import com.sharingif.cube.communication.JsonModel;
import com.sharingif.cube.core.transport.transform.MethodParameterArgument;
import com.sharingif.cube.core.transport.exception.MarshallerException;
import com.sharingif.cube.core.transport.transform.Marshaller;
import com.sharingif.cube.core.transport.transform.Transform;

/**
 * http json数据解析器
 * 2017年2月25日 下午10:31:19
 * @author Joly
 * @version v1.0
 * @since v1.0
 */
public class HttpJsonTransform implements Transform<Object[],String,MethodParameterArgument<Object[], String>,JsonModel<Object>> {

	private Marshaller<Object[], String> marshaller;
	private Marshaller<MethodParameterArgument<Object[], String>, JsonModel<Object>> unmarshaller;
	
	@Override
	public void setMarshaller(Marshaller<Object[], String> marshaller) {
		this.marshaller = marshaller;
	}

	@Override
	public void setUnmarshaller(Marshaller<MethodParameterArgument<Object[], String>, JsonModel<Object>> unmarshaller) {
		this.unmarshaller = unmarshaller;
	}
	
	@Override
	public String marshaller(Object[] data) throws MarshallerException {
		return marshaller.marshaller(data);
	}

	@Override
	public JsonModel<Object> unmarshaller(MethodParameterArgument<Object[], String> methodParameterArgument) throws MarshallerException {
		return unmarshaller.marshaller(methodParameterArgument);
	}

}
