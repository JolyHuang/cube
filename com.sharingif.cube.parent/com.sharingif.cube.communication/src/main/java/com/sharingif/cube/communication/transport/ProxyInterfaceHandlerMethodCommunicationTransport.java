package com.sharingif.cube.communication.transport;

import com.sharingif.cube.communication.exception.BusinessCommunicationException;
import com.sharingif.cube.communication.transport.transform.MethodParameterArgument;
import com.sharingif.cube.communication.transport.transform.exception.MarshallerException;
import com.sharingif.cube.core.exception.CubeException;
import com.sharingif.cube.core.handler.HandlerMethod;
import com.sharingif.cube.core.handler.HandlerMethodContent;
import com.sharingif.cube.core.request.RequestInfo;
import com.sharingif.cube.core.util.CubeExceptionUtil;

/**
 * 远程接口代理HandlerMethodTransport
 * 2017年5月17日 下午2:19:24
 * @author Joly
 * @version v1.0
 * @since v1.0
 */
public class ProxyInterfaceHandlerMethodCommunicationTransport<MO,CO,UO> extends AbstractHandlerMethodCommunicationTransport<Object[],MO,CO,MethodParameterArgument<Object[],CO>,UO> {

	public ProxyInterfaceHandlerMethodCommunicationTransport(HandlerMethod handlerMethod) {
		super(handlerMethod);
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
		
		MO marshallerData = marshaller((RequestInfo<Object[]>)handlerMethodContent.getRequestInfo());
		
		Object returnValue = null;
		try {
			
			CO connectReceiveMessage = connect(requestInfo, marshallerData);
			
			UO unmarshallerData = unmarshaller(connectReceiveMessage, requestInfo);
			
			handleException(unmarshallerData);
			
			returnValue = unmarshallerData;
			
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
	
	protected UO unmarshaller(CO connectReceiveMessage, RequestInfo<Object[]> requestInfo) throws MarshallerException {
		MethodParameterArgument<Object[],CO> methodParameterArgument = new MethodParameterArgument<Object[],CO>(getReturnType(), requestInfo, getDataBinderFactory(), connectReceiveMessage);
		
		return getTransform().unmarshaller(methodParameterArgument);
	}
	
	protected MO marshaller(RequestInfo<Object[]> requestInfo) throws MarshallerException {
		return getTransform().marshaller(requestInfo.getRequest());
	}
	
	protected CO connect(RequestInfo<Object[]> requestInfo, MO marshallerData) {
		RequestInfo<MO> httpJsonContext = new RequestInfo<MO>(requestInfo.getMediaType(), requestInfo.getLookupPath(), requestInfo.getLocale(), requestInfo.getMethod(), marshallerData);
		
		CO connectReturnValue = getConnection().connect(httpJsonContext);
		
		return connectReturnValue;
	}
	
	protected void handleException(UO unmarshallerData) throws BusinessCommunicationException {
		
	}



}
