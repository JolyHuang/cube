package com.sharingif.cube.beans.factory.config;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.core.io.Resource;

/**
 *
 * @Description: [扩展PropertyPlaceholderConfigurer获得属相值]
 * @Author: [Joly]
 * @CreateDate: [2014年7月1日 下午5:08:51]
 * @UpdateUser: [Joly]
 * @UpdateDate: [2014年7月1日 下午5:08:51]
 * @UpdateRemark: [说明本次修改内容]
 * @Version: [v1.0]
 *
 */
public class ExtendedPropertyPlaceholderConfigurer extends
		PropertyPlaceholderConfigurer {

	private static final Map<String, String> PROPERTIES_MAP = new HashMap<String, String>();
	private List<Resource> commonProperties;

	public ExtendedPropertyPlaceholderConfigurer() {
		super();
	}
	
	public ExtendedPropertyPlaceholderConfigurer(List<Resource> commonProperties) {
		super();
		this.commonProperties = commonProperties;
	}
	
	@Override
	protected void processProperties(
			ConfigurableListableBeanFactory beanFactoryToProcess,
			Properties props) throws BeansException {
		super.processProperties(beanFactoryToProcess, props);

		for (Object key : props.keySet()) {
			String keyStr = key.toString();
			String value = props.getProperty(keyStr);
			PROPERTIES_MAP.put(keyStr, value);
		}
	}
	
	public String getContextProperty(String name) {
		return PROPERTIES_MAP.get(name);
	}

	@Override
	public void setLocations(Resource... locations) {
		
		if(commonProperties != null && commonProperties.size() != 0){
			Resource[] mergedlocations = new Resource[locations.length+commonProperties.size()];
			System.arraycopy(locations,0,mergedlocations,0,locations.length);
			System.arraycopy(commonProperties.toArray(), 0, mergedlocations, locations.length, commonProperties.size());
			
			super.setLocations(mergedlocations);
			
			return;
		}
		
		super.setLocations(locations);
	}
	

}
