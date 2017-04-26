package com.sharingif.cube.communication.remote;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.beans.factory.support.RootBeanDefinition;

import com.sharingif.cube.core.exception.CubeRuntimeException;
import com.sharingif.cube.core.method.bind.support.BindingInitializer;
import com.sharingif.cube.core.method.bind.support.DefaultDataBinderFactory;

/**
 * RemoteServicesApplicationContext
 * 2017年1月9日 下午8:29:34
 * @author Joly
 * @version v1.0
 * @since v1.0
 */
public class RemoteServicesApplicationContext implements BeanDefinitionRegistryPostProcessor {
	
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
			remoteServiceMap.putAll(remoteService.initServices(defaultDataBinderFactory));
		}
	}
	
	public Object getRemoteService(String key){
		return remoteServiceMap.get(key);
	}
	
	@Override
	public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
		
	}
	@Override
	public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
		try {
			init();
		} catch (ClassNotFoundException e) {
			throw new CubeRuntimeException(e);
		}
		
		for(String beanName : remoteServiceMap.keySet()) {
			Object proxyObject = remoteServiceMap.get(beanName);
			
			RootBeanDefinition rootBeanDefinition = new RootBeanDefinition();
			rootBeanDefinition.setBeanClass(RemoteServiceFactoryBean.class);
			rootBeanDefinition.setLazyInit(true);
			rootBeanDefinition.getPropertyValues().addPropertyValue(RemoteServiceFactoryBean.PROXY_OBJECT, proxyObject);
			registry.registerBeanDefinition(beanName, rootBeanDefinition);
		}
	}

}
