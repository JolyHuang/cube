package com.sharingif.cube.communication.transport;

import com.sharingif.cube.core.view.JsonModel;
import com.sharingif.cube.communication.exception.BusinessCommunicationException;
import com.sharingif.cube.core.exception.CubeException;
import com.sharingif.cube.core.handler.HandlerMethod;
import com.sharingif.cube.core.handler.chain.HandlerMethodContent;
import com.sharingif.cube.core.request.RequestContext;
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
public class ProxyInterfaceHandlerMethodCommunicationTransport<MO,CO,UO> extends AbstractHandlerMethodCommunicationTransport<RequestContext<Object[]>,MO,RequestContext<MO>,CO,MethodParameterArgument<Object[],CO>,UO> {

	public ProxyInterfaceHandlerMethodCommunicationTransport(HandlerMethod handlerMethod) {
		super(handlerMethod);
	}
	
	@Override
	public Object doTransport(RequestContext<Object[]> requestContext) throws CubeException {
		
		boolean handlerMethodChainIsNotEmpty = (null != getHandlerMethodChain());
		HandlerMethodContent handlerMethodContent = new HandlerMethodContent(this, requestContext.getRequest(), null, requestContext.getLocale(), requestContext);
		
		if(handlerMethodChainIsNotEmpty) {
			getHandlerMethodChain().before(handlerMethodContent);
		}
		
		Object returnValue = null;
		try {
			returnValue = doTransportInternal(handlerMethodContent.getRequestContext());
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
	
	public Object doTransportInternal(RequestContext<Object[]> requestContext) throws CubeException {
		MO marshallerData = marshaller(requestContext);
		
		CO connectReceiveMessage = connect(requestContext, marshallerData);
		
		UO unmarshallerData = unmarshaller(connectReceiveMessage, requestContext);
		
		handleException(unmarshallerData);

		if(JsonModel.class.isInstance(unmarshallerData)) {
			JsonModel<?> jsonModel = (JsonModel<?>)unmarshallerData;
			return jsonModel.get_data();
		}
		
		return unmarshallerData;
	}
	
	protected MO marshaller(RequestContext<Object[]> requestContext) throws MarshallerException {
		return getTransform().marshaller(requestContext);
	}
	
	protected CO connect(RequestContext<Object[]> requestContext, MO marshallerData) {
		RequestContext<MO> httpJsonContext = new RequestContext<MO>(requestContext.getMediaType(), requestContext.getLookupPath(), requestContext.getLocale(), requestContext.getMethod(), marshallerData);
		
		CO connectReturnValue = getConnection().connect(httpJsonContext);
		
		return connectReturnValue;
	}
	
	protected UO unmarshaller(CO connectReceiveMessage, RequestContext<Object[]> requestContext) throws MarshallerException {
		MethodParameterArgument<Object[],CO> methodParameterArgument = new MethodParameterArgument<Object[],CO>(getReturnType(), requestContext, getDataBinderFactory(), connectReceiveMessage);
		
		return getTransform().unmarshaller(methodParameterArgument);
	}
	
	protected void handleException(UO unmarshallerData) throws BusinessCommunicationException {
		getBusinessCommunicationExceptionHandler().handleCommunicationException(unmarshallerData);
	}



}
