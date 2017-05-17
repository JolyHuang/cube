package com.sharingif.cube.web.springmvc.http.converter.json;

import java.util.TimeZone;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sharingif.cube.core.config.CubeConfigure;

/**
 * [设置默认时区位为“中国上海”] 
 * [2015年4月4日 下午3:05:00] 
 * [@author Joly] 
 * [@version v1.0] 
 * [@since v1.0]
 */
public class ExtendedMappingJackson2HttpMessageConverter extends MappingJackson2HttpMessageConverter {
	
	public ExtendedMappingJackson2HttpMessageConverter() {
		super();
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.setTimeZone(TimeZone.getTimeZone(CubeConfigure.DEFAULT_TIME_ZONE));
		
		super.setObjectMapper(objectMapper);
	}
	
	public ExtendedMappingJackson2HttpMessageConverter(String timeZone) {
		super();
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.setTimeZone(TimeZone.getTimeZone(timeZone));
		
		super.setObjectMapper(objectMapper);
	}

}
