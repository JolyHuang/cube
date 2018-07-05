package com.sharingif.cube.core.handler.mapping;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.IdentityHashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.ClassUtils;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.ReflectionUtils.MethodFilter;

import com.sharingif.cube.core.exception.CubeException;
import com.sharingif.cube.core.handler.HandlerMethod;
import com.sharingif.cube.core.handler.HandlerMethodMappingNamingStrategy;
import com.sharingif.cube.core.handler.HandlerMethodSelector;
import com.sharingif.cube.core.request.RequestContext;

/**
 *
 * AbstractHandlerMethodMapping
 * 2015年6月21日 下午10:59:11
 * @author Joly
 * @version v1.0
 * @since v1.0
 */
public abstract class AbstractHandlerMethodMapping<T> extends AbstractHandlerMapping<Object,HandlerMethod> implements InitializingBean {
	
	protected final Logger logger = LoggerFactory.getLogger(getClass());
	
	private static final String SCOPED_TARGET_NAME_PREFIX = "scopedTarget.";
	
	private boolean detectHandlerMethodsInAncestorContexts = false;
	
	private HandlerMethodMappingNamingStrategy<T> namingStrategy;
	
	private final Map<T, HandlerMethod> handlerMethods = new LinkedHashMap<T, HandlerMethod>();
	
	private final MultiValueMap<String, T> urlMap = new LinkedMultiValueMap<String, T>();
	
	private final MultiValueMap<String, HandlerMethod> nameMap = new LinkedMultiValueMap<String, HandlerMethod>();
	
	public void setDetectHandlerMethodsInAncestorContexts(boolean detectHandlerMethodsInAncestorContexts) {
		this.detectHandlerMethodsInAncestorContexts = detectHandlerMethodsInAncestorContexts;
	}
	
	public void setHandlerMethodMappingNamingStrategy(HandlerMethodMappingNamingStrategy<T> namingStrategy) {
		this.namingStrategy = namingStrategy;
	}
	
	public Map<T, HandlerMethod> getHandlerMethods() {
		return Collections.unmodifiableMap(this.handlerMethods);
	}
	
	public List<HandlerMethod> getHandlerMethodsForMappingName(String mappingName) {
		return this.nameMap.get(mappingName);
	}
	
	protected void handlerMethodsInitialized(Map<T, HandlerMethod> handlerMethods) {
	}
	
	private void initHandlerMethods() {
		if (logger.isDebugEnabled()) {
			logger.debug("Looking for request mappings in application context: " + getApplicationContext());
		}

		String[] beanNames = (this.detectHandlerMethodsInAncestorContexts ?
				BeanFactoryUtils.beanNamesForTypeIncludingAncestors(getApplicationContext(), Object.class) :
				getApplicationContext().getBeanNamesForType(Object.class));

		for (String beanName : beanNames) {
			if (!beanName.startsWith(SCOPED_TARGET_NAME_PREFIX) &&
					isHandler(getApplicationContext().getType(beanName))){
				detectHandlerMethods(beanName);
			}
		}
		handlerMethodsInitialized(getHandlerMethods());
	}
	
	protected void detectHandlerMethods(final Object handler) {
		Class<?> handlerType =
				(handler instanceof String ? getApplicationContext().getType((String) handler) : handler.getClass());

		// Avoid repeated calls to getMappingForMethod which would rebuild RequestMappingInfo instances
		final Map<Method, T> mappings = new IdentityHashMap<Method, T>();
		final Class<?> userType = ClassUtils.getUserClass(handlerType);

		Set<Method> methods = HandlerMethodSelector.selectMethods(userType, new MethodFilter() {
			@Override
			public boolean matches(Method method) {
				T mapping = getMappingForMethod(method, userType);
				if (mapping != null) {
					mappings.put(method, mapping);
					return true;
				}
				else {
					return false;
				}
			}
		});

		for (Method method : methods) {
			registerHandlerMethod(handler, method, mappings.get(method));
		}
	}
	
	protected HandlerMethod createHandlerMethod(Object handler, Method method) {
		HandlerMethod handlerMethod;
		if (handler instanceof String) {
			String beanName = (String) handler;
			handlerMethod = new HandlerMethod(beanName,
					getApplicationContext().getAutowireCapableBeanFactory(), method);
		}
		else {
			handlerMethod = new HandlerMethod(handler, method);
		}
		return handlerMethod;
	}
	
	protected void registerHandlerMethod(Object handler, Method method, T mapping) {
		HandlerMethod newHandlerMethod = createHandlerMethod(handler, method);
		HandlerMethod oldHandlerMethod = this.handlerMethods.get(mapping);
		if (oldHandlerMethod != null && !oldHandlerMethod.equals(newHandlerMethod)) {
			throw new IllegalStateException("Ambiguous mapping found. Cannot map '" + newHandlerMethod.getBean() +
					"' bean method \n" + newHandlerMethod + "\nto " + mapping + ": There is already '" +
					oldHandlerMethod.getBean() + "' bean method\n" + oldHandlerMethod + " mapped.");
		}

		this.handlerMethods.put(mapping, newHandlerMethod);
		if (logger.isInfoEnabled()) {
			logger.info("Mapped \"" + mapping + "\" onto " + newHandlerMethod);
		}

		Set<String> patterns = getMappingPathPatterns(mapping);
		for (String pattern : patterns) {
			if (!getPathMatcher().isPattern(pattern)) {
				this.urlMap.add(pattern, mapping);
			}
		}

		if (this.namingStrategy != null) {
			String name = this.namingStrategy.getName(newHandlerMethod, mapping);
			updateNameMap(name, newHandlerMethod);
		}
	}

	private void updateNameMap(String name, HandlerMethod newHandlerMethod) {
		List<HandlerMethod> handlerMethods = this.nameMap.get(name);
		if (handlerMethods != null) {
			for (HandlerMethod handlerMethod : handlerMethods) {
				if (handlerMethod.getMethod().equals(newHandlerMethod.getMethod())) {
					logger.trace("Mapping name already registered. Multiple controller instances perhaps?");
					return;
				}
			}
		}

		logger.trace("Mapping name=" + name);
		this.nameMap.add(name, newHandlerMethod);

		if (this.nameMap.get(name).size() > 1) {
			if (logger.isDebugEnabled()) {
				logger.debug("Mapping name clash for handlerMethods=" + this.nameMap.get(name) +
						". Consider assigning explicit names.");
			}
		}
	}
	
	protected abstract boolean isHandler(Class<?> beanType);
	
	protected abstract T getMappingForMethod(Method method, Class<?> handlerType);
	
	protected abstract T getMatchingMapping(T mapping, RequestContext<Object> requestContext);
	
	protected abstract Comparator<T> getMappingComparator(RequestContext<Object> requestContext);
	
	private void addMatchingMappings(Collection<T> mappings, List<Match> matches, RequestContext<Object> requestContext) {
		for (T mapping : mappings) {
			T match = getMatchingMapping(mapping, requestContext);
			if (match != null) {
				matches.add(new Match(match, handlerMethods.get(mapping)));
			}
		}
	}
	
	protected HandlerMethod handleNoMatch(Set<T> mappings, String lookupPath, RequestContext<?> requestContext) throws CubeException {

		return null;
	}
	
	protected HandlerMethod lookupHandlerMethod(String lookupPath, RequestContext<Object> requestContext) throws CubeException {
		List<Match> matches = new ArrayList<Match>();

		List<T> directPathMatches = this.urlMap.get(lookupPath);
		if (directPathMatches != null) {
			addMatchingMappings(directPathMatches, matches, requestContext);
		}

		if (matches.isEmpty()) {
			// No choice but to go through all mappings
			addMatchingMappings(this.handlerMethods.keySet(), matches, requestContext);
		}
		
		if (!matches.isEmpty()) {
			Comparator<Match> comparator = new MatchComparator(getMappingComparator(requestContext));
			Collections.sort(matches, comparator);
			if (logger.isTraceEnabled()) {
				logger.trace("Found " + matches.size() + " matching mapping(s) for [" + requestContext + "] : " + matches);
			}
			Match bestMatch = matches.get(0);
			if (matches.size() > 1) {
				Match secondBestMatch = matches.get(1);
				if (comparator.compare(bestMatch, secondBestMatch) == 0) {
					Method m1 = bestMatch.handlerMethod.getMethod();
					Method m2 = secondBestMatch.handlerMethod.getMethod();
					throw new IllegalStateException(
							"Ambiguous handler methods mapped for request info '" + requestContext + "': {" +
							m1 + ", " + m2 + "}");
				}
			}
			return bestMatch.handlerMethod;
		}

		return handleNoMatch(handlerMethods.keySet(), lookupPath, requestContext);
	}
	
	/**
	 * Look up a handler method for the given request.
	 */
	@Override
	protected HandlerMethod getHandlerInternal(RequestContext<Object> request) throws CubeException {
		
		String lookupPath = request.getLookupPath();
		if (logger.isDebugEnabled()) {
			logger.debug("Looking up handler method for path " + lookupPath);
		}

		HandlerMethod handlerMethod = lookupHandlerMethod(lookupPath, request);

		if (logger.isDebugEnabled()) {
			if (handlerMethod != null) {
				logger.debug("Returning handler method [" + handlerMethod + "]");
			}
			else {
				logger.debug("Did not find handler method for [" + lookupPath + "]");
			}
		}

		return (handlerMethod != null) ? handlerMethod.createWithResolvedBean() : null;
	}
	
	protected abstract Set<String> getMappingPathPatterns(T mapping);
	
	private class Match {

		private final T mapping;

		private final HandlerMethod handlerMethod;

		public Match(T mapping, HandlerMethod handlerMethod) {
			this.mapping = mapping;
			this.handlerMethod = handlerMethod;
		}

		@Override
		public String toString() {
			return this.mapping.toString();
		}
	}

	private class MatchComparator implements Comparator<Match> {

		private final Comparator<T> comparator;

		public MatchComparator(Comparator<T> comparator) {
			this.comparator = comparator;
		}

		@Override
		public int compare(Match match1, Match match2) {
			return this.comparator.compare(match1.mapping, match2.mapping);
		}
	}
	
	@Override
	public void afterPropertiesSet() {
		initHandlerMethods();
	}
	
}
