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
import com.sharingif.cube.core.request.RequestInfo;

/**
 * Abstract base class for {@link HandlerMapping} implementations that define a
 * mapping between a request and a {@link HandlerMethod}.
 *
 * <p>For each registered handler method, a unique mapping is maintained with
 * subclasses defining the details of the mapping type {@code <T>}.
 *
 * @param <T> The mapping for a {@link HandlerMethod} containing the conditions
 * needed to match the handler method to incoming request.
 * 
 * 2015年6月21日 下午10:59:11
 * @author Joly
 * @version v1.0
 * @since v1.0
 */
public abstract class AbstractHandlerMethodMapping<T> extends AbstractHandlerMapping<HandlerMethod> implements InitializingBean {
	
	protected final Logger logger = LoggerFactory.getLogger(getClass());
	
	/**
	 * Bean name prefix for target beans behind scoped proxies. Used to exclude those
	 * targets from handler method detection, in favor of the corresponding proxies.
	 * <p>We're not checking the autowire-candidate status here, which is how the
	 * proxy target filtering problem is being handled at the autowiring level,
	 * since autowire-candidate may have been turned to {@code false} for other
	 * reasons, while still expecting the bean to be eligible for handler methods.
	 * <p>Originally defined in {@link org.springframework.aop.scope.ScopedProxyUtils}
	 * but duplicated here to avoid a hard dependency on the spring-aop module.
	 */
	private static final String SCOPED_TARGET_NAME_PREFIX = "scopedTarget.";
	
	private boolean detectHandlerMethodsInAncestorContexts = false;
	
	private HandlerMethodMappingNamingStrategy<T> namingStrategy;
	
	private final Map<T, HandlerMethod> handlerMethods = new LinkedHashMap<T, HandlerMethod>();
	
	private final MultiValueMap<String, T> urlMap = new LinkedMultiValueMap<String, T>();
	
	private final MultiValueMap<String, HandlerMethod> nameMap = new LinkedMultiValueMap<String, HandlerMethod>();
	
	/**
	 * Whether to detect handler methods in beans in ancestor ApplicationContexts.
	 * <p>Default is "false": Only beans in the current ApplicationContext are
	 * considered, i.e. only in the context that this HandlerMapping itself
	 * is defined in (typically the current DispatcherServlet's context).
	 * <p>Switch this flag on to detect handler beans in ancestor contexts
	 * (typically the Spring root WebApplicationContext) as well.
	 */
	public void setDetectHandlerMethodsInAncestorContexts(boolean detectHandlerMethodsInAncestorContexts) {
		this.detectHandlerMethodsInAncestorContexts = detectHandlerMethodsInAncestorContexts;
	}
	
	/**
	 * Configure the naming strategy to use for assigning a default name to every
	 * mapped handler method.
	 */
	public void setHandlerMethodMappingNamingStrategy(HandlerMethodMappingNamingStrategy<T> namingStrategy) {
		this.namingStrategy = namingStrategy;
	}
	
	/**
	 * Return a map with all handler methods and their mappings.
	 */
	public Map<T, HandlerMethod> getHandlerMethods() {
		return Collections.unmodifiableMap(this.handlerMethods);
	}
	
	/**
	 * Return the handler methods mapped to the mapping with the given name.
	 * @param mappingName the mapping name
	 */
	public List<HandlerMethod> getHandlerMethodsForMappingName(String mappingName) {
		return this.nameMap.get(mappingName);
	}
	
	/**
	 * Invoked after all handler methods have been detected.
	 * @param handlerMethods a read-only map with handler methods and mappings.
	 */
	protected void handlerMethodsInitialized(Map<T, HandlerMethod> handlerMethods) {
	}
	
	/**
	 * Scan beans in the ApplicationContext, detect and register handler methods.
	 * @see #isHandler(Class)
	 * @see #getMappingForMethod(Method, Class)
	 * @see #handlerMethodsInitialized(Map)
	 */
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
	
	/**
	 * Look for handler methods in a handler.
	 * @param handler the bean name of a handler or a handler instance
	 */
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
	
	/**
	 * Create the HandlerMethod instance.
	 * @param handler either a bean name or an actual handler instance
	 * @param method the target method
	 * @return the created HandlerMethod
	 */
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
	
	/**
	 * Register a handler method and its unique mapping.
	 * @param handler the bean name of the handler or the handler instance
	 * @param method the method to register
	 * @param mapping the mapping conditions associated with the handler method
	 * @throws IllegalStateException if another method was already registered
	 * under the same mapping
	 */
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
	
	/**
	 * Whether the given type is a handler with handler methods.
	 * @param beanType the type of the bean being checked
	 * @return "true" if this a handler type, "false" otherwise.
	 */
	protected abstract boolean isHandler(Class<?> beanType);
	
	/**
	 * Provide the mapping for a handler method. A method for which no
	 * mapping can be provided is not a handler method.
	 * @param method the method to provide a mapping for
	 * @param handlerType the handler type, possibly a sub-type of the method's
	 * declaring class
	 * @return the mapping, or {@code null} if the method is not mapped
	 */
	protected abstract T getMappingForMethod(Method method, Class<?> handlerType);
	
	/**
	 * Check if a mapping matches the current request and return a (potentially
	 * new) mapping with conditions relevant to the current request.
	 * @param mapping the mapping to get a match for
	 * @param request the current HTTP servlet request
	 * @return the match, or {@code null} if the mapping doesn't match
	 */
	protected abstract T getMatchingMapping(T mapping, RequestInfo<?> requestInfo);
	
	/**
	 * Return a comparator for sorting matching mappings.
	 * The returned comparator should sort 'better' matches higher.
	 * @param request the current request
	 * @return the comparator, never {@code null}
	 */
	protected abstract Comparator<T> getMappingComparator(RequestInfo<?> requestInfo);
	
	private void addMatchingMappings(Collection<T> mappings, List<Match> matches, RequestInfo<?> requestInfo) {
		for (T mapping : mappings) {
			T match = getMatchingMapping(mapping, requestInfo);
			if (match != null) {
				matches.add(new Match(match, handlerMethods.get(mapping)));
			}
		}
	}
	
	/**
	 * Invoked when no matching mapping is not found.
	 * @param mappings all registered mappings
	 * @param lookupPath mapping lookup path within the current servlet mapping
	 * @param request the current request
	 * @throws ServletException in case of errors
	 */
	protected HandlerMethod handleNoMatch(Set<T> mappings, String lookupPath, RequestInfo<?> requestInfo) throws CubeException {

		return null;
	}
	
	/**
	 * Look up the best-matching handler method for the current request.
	 * If multiple matches are found, the best match is selected.
	 * @param lookupPath mapping lookup path within the current servlet mapping
	 * @param request the current request
	 * @return the best-matching handler method, or {@code null} if no match
	 * @see #handleMatch(Object, String, HttpServletRequest)
	 * @see #handleNoMatch(Set, String, HttpServletRequest)
	 */
	protected HandlerMethod lookupHandlerMethod(String lookupPath, RequestInfo<?> requestInfo) throws CubeException {
		List<Match> matches = new ArrayList<Match>();

		List<T> directPathMatches = this.urlMap.get(lookupPath);
		if (directPathMatches != null) {
			addMatchingMappings(directPathMatches, matches, requestInfo);
		}

		if (matches.isEmpty()) {
			// No choice but to go through all mappings
			addMatchingMappings(this.handlerMethods.keySet(), matches, requestInfo);
		}
		
		if (!matches.isEmpty()) {
			Comparator<Match> comparator = new MatchComparator(getMappingComparator(requestInfo));
			Collections.sort(matches, comparator);
			if (logger.isTraceEnabled()) {
				logger.trace("Found " + matches.size() + " matching mapping(s) for [" + requestInfo + "] : " + matches);
			}
			Match bestMatch = matches.get(0);
			if (matches.size() > 1) {
				Match secondBestMatch = matches.get(1);
				if (comparator.compare(bestMatch, secondBestMatch) == 0) {
					Method m1 = bestMatch.handlerMethod.getMethod();
					Method m2 = secondBestMatch.handlerMethod.getMethod();
					throw new IllegalStateException(
							"Ambiguous handler methods mapped for request info '" + requestInfo + "': {" +
							m1 + ", " + m2 + "}");
				}
			}
			return bestMatch.handlerMethod;
		}

		return handleNoMatch(handlerMethods.keySet(), lookupPath, requestInfo);
	}
	
	/**
	 * Look up a handler method for the given request.
	 */
	@Override
	protected HandlerMethod getHandlerInternal(RequestInfo<?> request) throws CubeException {
		
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
	
	/**
	 * Extract and return the URL paths contained in a mapping.
	 */
	protected abstract Set<String> getMappingPathPatterns(T mapping);
	
	/**
	 * A thin wrapper around a matched HandlerMethod and its mapping, for the purpose of
	 * comparing the best match with a comparator in the context of the current request.
	 */
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
	
	/**
	 * Detects handler methods at initialization.
	 */
	@Override
	public void afterPropertiesSet() {
		initHandlerMethods();
	}
	
}
