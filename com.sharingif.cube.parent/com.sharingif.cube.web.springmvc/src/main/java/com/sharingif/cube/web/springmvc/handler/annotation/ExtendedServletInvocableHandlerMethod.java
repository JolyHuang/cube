package com.sharingif.cube.web.springmvc.handler.annotation;

import java.lang.reflect.Method;
import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;

import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.core.MethodParameter;
import org.springframework.core.ParameterNameDiscoverer;
import org.springframework.util.ClassUtils;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.method.support.HandlerMethodArgumentResolverComposite;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.servlet.mvc.method.annotation.ServletInvocableHandlerMethod;
import org.springframework.web.servlet.support.RequestContextUtils;

import com.sharingif.cube.core.exception.CubeException;
import com.sharingif.cube.core.handler.chain.HandlerMethodChain;
import com.sharingif.cube.core.util.CubeExceptionUtil;
import com.sharingif.cube.web.springmvc.handler.SpringMVCHandlerMethodContent;

/**
 * TODO
 * 2017年4月30日 下午3:39:04
 * @author Joly
 * @version v1.0
 * @since v1.0
 */
public class ExtendedServletInvocableHandlerMethod extends ServletInvocableHandlerMethod {

	public ExtendedServletInvocableHandlerMethod(HandlerMethod handlerMethod) {
		super(handlerMethod);
	}
	
	public ExtendedServletInvocableHandlerMethod(Object handler, Method method) {
		super(handler, method);
	}
	
	private WebDataBinderFactory dataBinderFactory;

	private HandlerMethodArgumentResolverComposite argumentResolvers = new HandlerMethodArgumentResolverComposite();

	private ParameterNameDiscoverer parameterNameDiscoverer = new DefaultParameterNameDiscoverer();
	
	private HandlerMethodChain<SpringMVCHandlerMethodContent> handlerMethodChain;

	public WebDataBinderFactory getDataBinderFactory() {
		return dataBinderFactory;
	}
	@Override
	public void setDataBinderFactory(WebDataBinderFactory dataBinderFactory) {
		this.dataBinderFactory = dataBinderFactory;
	}
	public HandlerMethodArgumentResolverComposite getHandlerMethodArgumentResolvers() {
		return argumentResolvers;
	}
	@Override
	public void setHandlerMethodArgumentResolvers(HandlerMethodArgumentResolverComposite argumentResolvers) {
		this.argumentResolvers = argumentResolvers;
	}
	public ParameterNameDiscoverer getParameterNameDiscoverer() {
		return parameterNameDiscoverer;
	}
	@Override
	public void setParameterNameDiscoverer(ParameterNameDiscoverer parameterNameDiscoverer) {
		this.parameterNameDiscoverer = parameterNameDiscoverer;
	}
	public HandlerMethodChain<SpringMVCHandlerMethodContent> getHandlerMethodChain() {
		return handlerMethodChain;
	}

	public void setHandlerMethodChain(HandlerMethodChain<SpringMVCHandlerMethodContent> handlerMethodChain) {
		this.handlerMethodChain = handlerMethodChain;
	}

	@Override
	public Object invokeForRequest(NativeWebRequest request, ModelAndViewContainer mavContainer, Object... providedArgs)
			throws Exception {
		
		Object[] args = getMethodArgumentValues(request, mavContainer, providedArgs);
		if (logger.isTraceEnabled()) {
			logger.trace("Invoking '" + ClassUtils.getQualifiedMethodName(getMethod(), getBeanType()) +
					"' with arguments " + Arrays.toString(args));
		}
		
		boolean handlerMethodChainIsNotEmpty = (null != getHandlerMethodChain());
		SpringMVCHandlerMethodContent handlerMethodContent = null;
		if(handlerMethodChainIsNotEmpty) {
			handlerMethodContent = new SpringMVCHandlerMethodContent(
					getBean()
					,super.getMethod()
					,args
					,null
					,super.getMethodParameters()
					,RequestContextUtils.getLocale(request.getNativeRequest(HttpServletRequest.class))
					,null
					,request
					,mavContainer
					,providedArgs
				);
			getHandlerMethodChain().before(handlerMethodContent);
		}
		
		Object returnValue = null;
		try {
			returnValue = doInvoke(handlerMethodContent.getArgs());
		} catch (Exception exception) {
			if(handlerMethodChainIsNotEmpty) {
				try {
					getHandlerMethodChain().exception(handlerMethodContent, exception);
				} catch (CubeException handlerMethodChainException) {
					this.logger.error("doTransport error", exception);
					CubeExceptionUtil.throwCubeRuntimeException(handlerMethodChainException);
				}
				
			}
			
			throw exception;
		}
		
		if(handlerMethodChainIsNotEmpty) {
			handlerMethodContent.setReturnValue(returnValue);
			getHandlerMethodChain().after(handlerMethodContent);
		}
		
		if (logger.isTraceEnabled()) {
			logger.trace("Method [" + ClassUtils.getQualifiedMethodName(getMethod(), getBeanType()) +
					"] returned [" + returnValue + "]");
		}
		
		if(handlerMethodChainIsNotEmpty) {
			return handlerMethodContent.getReturnValue();
		} else {
			return returnValue;
		}
		
	}
	
	/**
	 * Attempt to resolve a method parameter from the list of provided argument values.
	 */
	private Object resolveProvidedArgument(MethodParameter parameter, Object... providedArgs) {
		if (providedArgs == null) {
			return null;
		}
		for (Object providedArg : providedArgs) {
			if (parameter.getParameterType().isInstance(providedArg)) {
				return providedArg;
			}
		}
		return null;
	}
	
	private String getArgumentResolutionErrorMessage(String text, int index) {
		Class<?> paramType = getMethodParameters()[index].getParameterType();
		return text + " argument " + index + " of type '" + paramType.getName() + "'";
	}
	
	/**
	 * Get the method argument values for the current request.
	 */
	protected Object[] getMethodArgumentValues(NativeWebRequest request, ModelAndViewContainer mavContainer,
			Object... providedArgs) throws Exception {

		MethodParameter[] parameters = getMethodParameters();
		Object[] args = new Object[parameters.length];
		for (int i = 0; i < parameters.length; i++) {
			MethodParameter parameter = parameters[i];
			parameter.initParameterNameDiscovery(this.parameterNameDiscoverer);
			args[i] = resolveProvidedArgument(parameter, providedArgs);
			if (args[i] != null) {
				continue;
			}
			if (this.argumentResolvers.supportsParameter(parameter)) {
				try {
					args[i] = this.argumentResolvers.resolveArgument(
							parameter, mavContainer, request, this.dataBinderFactory);
					continue;
				}
				catch (Exception ex) {
					if (logger.isDebugEnabled()) {
						logger.debug(getArgumentResolutionErrorMessage("Failed to resolve", i), ex);
					}
					throw ex;
				}
			}
			if (args[i] == null) {
				throw new IllegalStateException("Could not resolve method parameter at index " +
						parameter.getParameterIndex() + " in " + parameter.getMethod().toGenericString() +
						": " + getArgumentResolutionErrorMessage("No suitable resolver for", i));
			}
		}
		return args;
	}
	
}
