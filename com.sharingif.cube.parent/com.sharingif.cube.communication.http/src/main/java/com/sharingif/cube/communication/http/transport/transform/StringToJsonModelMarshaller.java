package com.sharingif.cube.communication.http.transport.transform;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.sharingif.cube.communication.JsonModel;
import com.sharingif.cube.core.config.CubeConfigure;
import com.sharingif.cube.core.handler.bind.support.BindingInitializer;
import com.sharingif.cube.core.transport.exception.MarshallerException;
import com.sharingif.cube.core.transport.transform.Marshaller;
import com.sharingif.cube.core.transport.transform.MethodParameterArgument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Type;
import java.util.TimeZone;

/**
 * JsonUnmarshaller
 * 2017年1月7日 下午8:41:59
 * @author Joly
 * @version v1.0
 * @since v1.0
 */
public class StringToJsonModelMarshaller implements Marshaller<MethodParameterArgument<Object[],String>, JsonModel<Object>> {

	protected final Logger logger = LoggerFactory.getLogger(getClass());
	
	private ObjectMapper objectMapper;
	private BindingInitializer bindingInitializer;
	
	public StringToJsonModelMarshaller() {
		objectMapper = new ObjectMapper();
		objectMapper.setTimeZone(TimeZone.getTimeZone(CubeConfigure.DEFAULT_TIME_ZONE));
	}
	
	public StringToJsonModelMarshaller(ObjectMapper objectMapper) {
		this.objectMapper = objectMapper;
	}
	
	public BindingInitializer getBindingInitializer() {
		return bindingInitializer;
	}

	public void setBindingInitializer(BindingInitializer bindingInitializer) {
		this.bindingInitializer = bindingInitializer;
	}


	@Override
	public JsonModel<Object> marshaller(MethodParameterArgument<Object[], String> methodParameterArgument) throws MarshallerException {

		JavaType javaType = getJavaType(methodParameterArgument.getMethodParameter().getNestedGenericParameterType());

		JsonModel<Object> object;
		try {
			object = objectMapper.readValue(methodParameterArgument.getConnectReturnValue(), javaType);
		} catch (Exception e) {
			this.logger.error("marshaller object to json error : {}", e);
			throw new MarshallerException("marshaller json to object error", e);
		}

		return object;
	}

	protected JavaType getJavaType(Type type) {
		TypeFactory typeFactory = this.objectMapper.getTypeFactory();

		if(type.getTypeName().equals(Void.TYPE.getTypeName())) {
			return typeFactory.constructType(JsonModel.class);
		}

		JavaType javaType = typeFactory.constructType(type);
		return typeFactory.constructParametricType(JsonModel.class, javaType);
	}

}
