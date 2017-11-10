package com.sharingif.cube.communication.transport;

import com.sharingif.cube.communication.JsonModel;
import com.sharingif.cube.communication.exception.BusinessCommunicationException;
import com.sharingif.cube.core.exception.CubeException;
import com.sharingif.cube.core.handler.HandlerMethod;
import com.sharingif.cube.core.handler.chain.HandlerMethodContent;
import com.sharingif.cube.core.request.RequestInfo;
import com.sharingif.cube.core.transport.exception.MarshallerException;
import com.sharingif.cube.core.transport.transform.MethodParameterArgument;
import com.sharingif.cube.core.util.CubeExceptionUtil;

/**
 * 远程接口代理HandlerMethodTransport
 * 2017年5月17日 下午2:19:24
 * @author Joly
 * @version v1.0
 * @since v1.0
 */
public class ProxyInterfaceHandlerMethodCommunicationTransport<MO,CO,UO> extends AbstractHandlerMethodCommunicationTransport<RequestInfo<Object[]>,MO,RequestInfo<MO>,CO,MethodParameterArgument<Object[],CO>,UO> {

	public ProxyInterfaceHandlerMethodCommunicationTransport(HandlerMethod handlerMethod) {
		super(handlerMethod);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Object doTransport(RequestInfo<Object[]> requestInfo) throws CubeException {
		
		boolean handlerMethodChainIsNotEmpty = (null != getHandlerMethodChain());
		HandlerMethodContent handlerMethodContent = new HandlerMethodContent(this, requestInfo.getRequest(), null, requestInfo.getLocale(), requestInfo);
		
		if(handlerMethodChainIsNotEmpty) {
			getHandlerMethodChain().before(handlerMethodContent);
		}
		
		Object returnValue = null;
		try {
			returnValue = doTransportInternal(handlerMethodContent.getRequestInfo());
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
	
	public Object doTransportInternal(RequestInfo<Object[]> requestInfo) throws CubeException {
		MO marshallerData = marshaller(requestInfo);
		
		CO connectReceiveMessage = connect(requestInfo, marshallerData);
		
		UO unmarshallerData = unmarshaller(connectReceiveMessage, requestInfo);
		
		handleException(unmarshallerData);

		if(JsonModel.class.isInstance(unmarshallerData)) {
			JsonModel jsonModel = (JsonModel)unmarshallerData;
			return jsonModel.get_data();
		}
		
		return unmarshallerData;
	}
	
	protected MO marshaller(RequestInfo<Object[]> requestInfo) throws MarshallerException {
		return getTransform().marshaller(requestInfo);
	}
	
	protected CO connect(RequestInfo<Object[]> requestInfo, MO marshallerData) {
		RequestInfo<MO> httpJsonContext = new RequestInfo<MO>(requestInfo.getMediaType(), requestInfo.getLookupPath(), requestInfo.getLocale(), requestInfo.getMethod(), marshallerData);
		
		CO connectReturnValue = getConnection().connect(httpJsonContext);
		
		return connectReturnValue;
	}
	
	protected UO unmarshaller(CO connectReceiveMessage, RequestInfo<Object[]> requestInfo) throws MarshallerException {
		MethodParameterArgument<Object[],CO> methodParameterArgument = new MethodParameterArgument<Object[],CO>(getReturnType(), requestInfo, getDataBinderFactory(), connectReceiveMessage);
		
		return getTransform().unmarshaller(methodParameterArgument);
	}
	
	protected void handleException(UO unmarshallerData) throws BusinessCommunicationException {
		getBusinessCommunicationExceptionHandler().handleCommunicationException(unmarshallerData);
	}



}
