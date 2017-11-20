package com.sharingif.cube.core.handler;

import com.sharingif.cube.core.exception.CubeException;
import com.sharingif.cube.core.handler.adapter.HandlerMethodArgumentResolver;
import com.sharingif.cube.core.handler.adapter.HandlerMethodArgumentResolverComposite;
import com.sharingif.cube.core.handler.bind.support.DataBinderFactory;
import com.sharingif.cube.core.handler.chain.HandlerMethodChain;
import com.sharingif.cube.core.handler.chain.HandlerMethodContent;
import com.sharingif.cube.core.request.RequestContext;
import com.sharingif.cube.core.util.CubeExceptionUtil;
import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.core.GenericTypeResolver;
import org.springframework.core.MethodParameter;
import org.springframework.core.ParameterNameDiscoverer;
import org.springframework.util.ReflectionUtils;
import org.springframework.validation.DataBinder;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;


public class InvocableHandlerMethod extends HandlerMethod {
	
	private DataBinderFactory dataBinderFactory;
	
	private HandlerMethodArgumentResolverComposite argumentResolvers;
	
	private ParameterNameDiscoverer parameterNameDiscoverer = new DefaultParameterNameDiscoverer();
	
	private HandlerMethodChain handlerMethodChain;
	
	protected InvocableHandlerMethod(HandlerMethod handlerMethod) {
		super(handlerMethod);
	}
	
	/**
	 * Set the {@link DataBinderFactory} to be passed to argument resolvers allowing them to create
	 * a {@link DataBinder} for data binding and type conversion purposes.
	 * @param dataBinderFactory the data binder factory.
	 */
	public void setDataBinderFactory(DataBinderFactory dataBinderFactory) {
		this.dataBinderFactory = dataBinderFactory;
	}
	
	
	public DataBinderFactory getDataBinderFactory() {
		return dataBinderFactory;
	}

	/**
	 * Set {@link HandlerMethodArgumentResolver}s to use to use for resolving method argument values.
	 */
	public void setHandlerMethodArgumentResolvers(HandlerMethodArgumentResolverComposite argumentResolvers) {
		this.argumentResolvers = argumentResolvers;
	}
	
	/**
	 * Set the ParameterNameDiscoverer for resolving parameter names when needed
	 * (e.g. default request attribute name).
	 * <p>Default is a {@link org.springframework.core.DefaultParameterNameDiscoverer}.
	 */
	public void setParameterNameDiscoverer(ParameterNameDiscoverer parameterNameDiscoverer) {
		this.parameterNameDiscoverer = parameterNameDiscoverer;
	}
	
	public ParameterNameDiscoverer getParameterNameDiscoverer() {
		return parameterNameDiscoverer;
	}

	public HandlerMethodChain getHandlerMethodChain() {
		return handlerMethodChain;
	}

	public void setHandlerMethodChain(HandlerMethodChain handlerMethodChain) {
		this.handlerMethodChain = handlerMethodChain;
	}

	public Object invokeForRequest(RequestContext<?> requestContext) throws CubeException {

		Object[] args = getMethodArgumentValues(requestContext);
		if (logger.isTraceEnabled()) {
			StringBuilder sb = new StringBuilder("Invoking [");
			sb.append(getBeanType().getSimpleName()).append(".");
			sb.append(getMethod().getName()).append("] method with arguments ");
			sb.append(Arrays.asList(args));
			logger.trace(sb.toString());
		}
		
		boolean handlerMethodChainIsNotEmpty = (null != getHandlerMethodChain());
		HandlerMethodContent handlerMethodContent = null;
		if(handlerMethodChainIsNotEmpty) {
			handlerMethodContent = new HandlerMethodContent(this, args, null, requestContext.getLocale(), requestContext);
			getHandlerMethodChain().before(handlerMethodContent);
		}
		
		Object returnValue = null;;
		try {
			returnValue = doInvoke(args);
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
			handlerMethodChain.after(handlerMethodContent);
		}
		
		if (logger.isTraceEnabled()) {
			logger.trace("Method [" + getMethod().getName() + "] returned [" + returnValue + "]");
		}
		
		if(handlerMethodChainIsNotEmpty) {
			return handlerMethodContent.getReturnValue();
		} else {
			return returnValue;
		}
	}
	
	/**
	 * Get the method argument values for the current request.
	 */
	protected Object[] getMethodArgumentValues(RequestContext<?> requestContext) throws CubeException {

		MethodParameter[] parameters = getMethodParameters();
		Object[] args = new Object[parameters.length];
		for (int i = 0; i < parameters.length; i++) {
			MethodParameter parameter = parameters[i];
			parameter.initParameterNameDiscovery(this.parameterNameDiscoverer);
			GenericTypeResolver.resolveParameterType(parameter, getBean().getClass());
			if (args[i] != null) {
				continue;
			}
			if (this.argumentResolvers.supportsParameter(parameter, requestContext)) {
				try {
					args[i] = this.argumentResolvers.resolveArgument(parameter, requestContext, this.dataBinderFactory);
					continue;
				} catch (CubeException ex) {
					if (logger.isDebugEnabled()) {
						logger.debug(getArgumentResolutionErrorMessage("Error resolving argument", i), ex);
					}
					
					throw ex;
				}
			}
			if (args[i] == null) {
				String msg = getArgumentResolutionErrorMessage("No suitable resolver for argument", i);
				throw new IllegalStateException(msg);
			}
		}
		return args;
	}
	
	/**
	 * Invoke the handler method with the given argument values.
	 */
	protected Object doInvoke(Object... args) throws CubeException {
		ReflectionUtils.makeAccessible(getBridgedMethod());
		try {
			return getBridgedMethod().invoke(getBean(), args);
		}
		catch (IllegalArgumentException ex) {
			assertTargetBean(getBridgedMethod(), getBean(), args);
			throw new IllegalStateException(getInvocationErrorMessage(ex.getMessage(), args), ex);
		}
		catch (InvocationTargetException ex) {
			// Unwrap for HandlerExceptionResolvers ...
			Throwable targetException = ex.getTargetException();
			if (targetException instanceof RuntimeException) {
				throw (RuntimeException) targetException;
			}
			else if (targetException instanceof Error) {
				throw (Error) targetException;
			}
			else if (targetException instanceof Exception) {
				throw new CubeException("do Invoke target exception", targetException);
			}
			else {
				String msg = getInvocationErrorMessage("Failed to invoke controller method", args);
				throw new IllegalStateException(msg, targetException);
			}
		} catch (IllegalAccessException e) {
			throw new IllegalStateException("do Invoke illegal access exception", e);
		}
	}
	
	private String getArgumentResolutionErrorMessage(String message, int index) {
		MethodParameter param = getMethodParameters()[index];
		message += " [" + index + "] [type=" + param.getParameterType().getName() + "]";
		return getDetailedErrorMessage(message);
	}
	
	/**
	 * Adds HandlerMethod details such as the controller type and method signature to the given error message.
	 * @param message error message to append the HandlerMethod details to
	 */
	protected String getDetailedErrorMessage(String message) {
		StringBuilder sb = new StringBuilder(message).append("\n");
		sb.append("HandlerMethod details: \n");
		sb.append("Controller [").append(getBeanType().getName()).append("]\n");
		sb.append("Method [").append(getBridgedMethod().toGenericString()).append("]\n");
		return sb.toString();
	}
	
	/**
	 * Assert that the target bean class is an instance of the class where the given
	 * method is declared. In some cases the actual controller instance at request-
	 * processing time may be a JDK dynamic proxy (lazy initialization, prototype
	 * beans, and others). {@code @Controller}'s that require proxying should prefer
	 * class-based proxy mechanisms.
	 */
	private void assertTargetBean(Method method, Object targetBean, Object[] args) {
		Class<?> methodDeclaringClass = method.getDeclaringClass();
		Class<?> targetBeanClass = targetBean.getClass();
		if (!methodDeclaringClass.isAssignableFrom(targetBeanClass)) {
			String msg = "The mapped controller method class '" + methodDeclaringClass.getName() +
					"' is not an instance of the actual controller bean instance '" +
					targetBeanClass.getName() + "'. If the controller requires proxying " +
					"(e.g. due to @Transactional), please use class-based proxying.";
			throw new IllegalStateException(getInvocationErrorMessage(msg, args));
		}
	}

	private String getInvocationErrorMessage(String message, Object[] resolvedArgs) {
		StringBuilder sb = new StringBuilder(getDetailedErrorMessage(message));
		sb.append("Resolved arguments: \n");
		for (int i=0; i < resolvedArgs.length; i++) {
			sb.append("[").append(i).append("] ");
			if (resolvedArgs[i] == null) {
				sb.append("[null] \n");
			}
			else {
				sb.append("[type=").append(resolvedArgs[i].getClass().getName()).append("] ");
				sb.append("[value=").append(resolvedArgs[i]).append("]\n");
			}
		}
		return sb.toString();
	}
	
	
}
