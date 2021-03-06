package com.sharingif.cube.communication.remote;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ApplicationObjectSupport;

import com.sharingif.cube.core.exception.CubeRuntimeException;
import com.sharingif.cube.core.exception.validation.ValidationCubeException;
import com.sharingif.cube.core.handler.bind.support.BindingInitializer;
import com.sharingif.cube.core.handler.bind.support.DefaultDataBinderFactory;

/**
 * RemoteServicesApplicationContext
 * 2017年1月9日 下午8:29:34
 * @author Joly
 * @version v1.0
 * @since v1.0
 */
public class RemoteServicesApplicationContext extends ApplicationObjectSupport implements BeanPostProcessor, InitializingBean {
	
	protected final Logger logger = LoggerFactory.getLogger(getClass());
	
	private BindingInitializer bindingInitializer;
	private List<RemoteServices> remoteServices;
	private Map<String, Object> remoteServiceMap = new HashMap<String,Object>(128);

	
	public BindingInitializer getBindingInitializer() {
		return bindingInitializer;
	}
	public void setBindingInitializer(BindingInitializer bindingInitializer) {
		this.bindingInitializer = bindingInitializer;
	}
	public List<RemoteServices> getRemoteServices() {
		return remoteServices;
	}
	public void setRemoteServices(List<RemoteServices> remoteServices) {
		this.remoteServices = remoteServices;
	}
	
	public void init() throws ClassNotFoundException {
		DefaultDataBinderFactory defaultDataBinderFactory = new DefaultDataBinderFactory(getBindingInitializer());
		for(RemoteServices remoteService : remoteServices) {
			Map<String,Object> initRemoteService = remoteService.initServices(defaultDataBinderFactory);
			for (Map.Entry<String, Object> entry : initRemoteService.entrySet()) {
				String key = entry.getKey();
				Object value = entry.getValue();
				
				Object oldproxyClass = remoteServiceMap.put(key, value);
				if(null != oldproxyClass) {
					this.logger.error("roxy class name repeat,name:{}",key);
					throw new ValidationCubeException("proxy class name repeat");
				}
			}
			
		}
	}
	
	public Object getRemoteService(String key){
		return remoteServiceMap.get(key);
	}

	@Override
	public void afterPropertiesSet() throws Exception {

		BeanDefinitionRegistry registry = (BeanDefinitionRegistry) ((ConfigurableApplicationContext) getApplicationContext()).getBeanFactory();

		try {
			init();
		} catch (ClassNotFoundException e) {
			throw new CubeRuntimeException(e);
		}

		for(String beanName : remoteServiceMap.keySet()) {
			Object proxyObject = remoteServiceMap.get(beanName);

			GenericBeanDefinition genericBeanDefinition = new GenericBeanDefinition();
			genericBeanDefinition.setBeanClass(RemoteServiceFactoryBean.class);
			genericBeanDefinition.getConstructorArgumentValues().addGenericArgumentValue(proxyObject);
			genericBeanDefinition.setLazyInit(true);
			registry.registerBeanDefinition(beanName, genericBeanDefinition);
		}
	}

	@Override
	public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
		return bean;
	}

	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
		return bean;
	}

}
