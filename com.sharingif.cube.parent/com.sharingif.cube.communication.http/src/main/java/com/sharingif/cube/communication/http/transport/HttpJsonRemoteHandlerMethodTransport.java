package com.sharingif.cube.communication.http.transport;

import com.sharingif.cube.communication.JsonModel;
import com.sharingif.cube.communication.exception.BusinessCommunicationException;
import com.sharingif.cube.communication.exception.CommunicationException;
import com.sharingif.cube.communication.transport.AbstractRemoteHandlerMethodTransport;
import com.sharingif.cube.communication.transport.Connection;
import com.sharingif.cube.communication.transport.transform.MethodParameterArgument;
import com.sharingif.cube.communication.transport.transform.Transform;
import com.sharingif.cube.communication.transport.transform.exception.MarshallerException;
import com.sharingif.cube.core.exception.CubeException;
import com.sharingif.cube.core.handler.HandlerMethod;
import com.sharingif.cube.core.handler.HandlerMethodContent;
import com.sharingif.cube.core.request.RequestInfo;
import com.sharingif.cube.core.util.CubeExceptionUtil;
import com.sharingif.cube.core.util.StringUtils;

/**
 * 提供远程方法访问
 * 2017年1月9日 下午4:09:16
 * @author Joly
 * @version v1.0
 * @since v1.0
 */
public class HttpJsonRemoteHandlerMethodTransport extends AbstractRemoteHandlerMethodTransport<RequestInfo<Object[]>,Object[],String,MethodParameterArgument<Object[],String>,JsonModel<Object>,RequestInfo<String>,String> {
	
	private Connection<RequestInfo<String>,String> connection;
	private Transform<Object[],String, MethodParameterArgument<Object[],String>, JsonModel<Object>> transform;

	public HttpJsonRemoteHandlerMethodTransport(HandlerMethod handlerMethod) {
		super(handlerMethod);
	}

	public void setConnection(Connection<RequestInfo<String>,String> connection) {
		this.connection = connection;
	}
	
	public void setTransform(Transform<Object[], String, MethodParameterArgument<Object[],String>, JsonModel<Object>> transform) {
		this.transform = transform;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Object doTransport(RequestInfo<Object[]> requestInfo) throws CubeException {
		
		boolean handlerMethodChainIsNotEmpty = (null != getHandlerMethodChain());
		HandlerMethodContent handlerMethodContent = null;
		
		if(handlerMethodChainIsNotEmpty) {
			handlerMethodContent = new HandlerMethodContent(getBean(), getMethod(), requestInfo.getRequest(), null, getMethodParameters(), requestInfo.getLocale(), requestInfo);
			getHandlerMethodChain().before(handlerMethodContent);
		}
		
		String jsonData = marshaller((RequestInfo<Object[]>) handlerMethodContent.getRequestInfo());
		
		Object returnValue = null;
		try {
			
			String connectReturnValue = connect(requestInfo, jsonData);
			
			JsonModel<Object> jsonModel = unmarshaller(connectReturnValue, requestInfo);
			
			handleException(jsonModel);
			
			returnValue = jsonModel.get_data();
			
		} catch (Exception exception) {
			if(handlerMethodChainIsNotEmpty) {
				try {
					getHandlerMethodChain().exception(handlerMethodContent, exception);
				} catch (Exception handlerMethodChainException) {
					this.logger.error("doTransport error", exception);
					CubeExceptionUtil.throwCubeRuntimeException(handlerMethodChainException);
				}
				
			}
			
			CubeExceptionUtil.throwCubeRuntimeException(exception);
		}
			
		if(handlerMethodChainIsNotEmpty) {
			handlerMethodContent.setReturnValue(returnValue);
			getHandlerMethodChain().after(handlerMethodContent);
		}
		
		if(handlerMethodChainIsNotEmpty) {
			return handlerMethodContent.getReturnValue();
		} else {
			return returnValue;
		}
	}
	
	protected JsonModel<Object> unmarshaller(String connectReturnValue, RequestInfo<Object[]> requestInfo) throws MarshallerException {
		MethodParameterArgument<Object[], String> methodParameterArgument = new MethodParameterArgument<Object[], String>(getReturnType(), requestInfo, getDataBinderFactory(), connectReturnValue);
		
		return transform.unmarshaller(methodParameterArgument);
	}
	
	protected String marshaller(RequestInfo<Object[]> requestInfo) throws MarshallerException {
		return transform.marshaller(requestInfo.getRequest());
	}
	
	protected String connect(RequestInfo<Object[]> requestInfo, String jsonData) {
		RequestInfo<String> httpJsonContext = new RequestInfo<String>(requestInfo.getMediaType(), requestInfo.getLookupPath(), requestInfo.getLocale(), requestInfo.getMethod(), jsonData);
		
		String connectReturnValue = connection.connect(httpJsonContext);
		
		if(StringUtils.isEmpty(connectReturnValue) || "".equals(connectReturnValue.trim())) {
			throw new CommunicationException("The reponse message is empty");
		}
		
		return connectReturnValue;
	}
	
	protected void handleException(JsonModel<Object> jsonModel) throws BusinessCommunicationException {
		if(!jsonModel.get_tranStatus()) {
			BusinessCommunicationException businessCommunicationException = new BusinessCommunicationException(jsonModel.get_exceptionMessage());
			businessCommunicationException.setLocalizedMessage(jsonModel.get_exceptionLocalizedMessage());
			
			throw businessCommunicationException;
		}
	}
	
}
