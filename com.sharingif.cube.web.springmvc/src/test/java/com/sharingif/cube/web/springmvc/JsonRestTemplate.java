package com.sharingif.cube.web.springmvc;

import java.util.Collections;
import java.util.Date;

import org.junit.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;


/**
 * JsonRestTemplate
 * 2016年12月20日 下午1:46:12
 * @author Joly
 * @version v1.0
 * @since v1.0
 */
public class JsonRestTemplate {
	
	private RestTemplate buildRestTemplate() {
		RestTemplate restTemplate = new RestTemplate();
		
		MappingJackson2HttpMessageConverter jsonConverter = new MappingJackson2HttpMessageConverter();
		
		restTemplate.getMessageConverters().add(jsonConverter);
		
		return restTemplate;
	}
	
	@Test
	public void testJson() {
		RestTemplate restTemplate = buildRestTemplate();
		
		HttpHeaders entityHeaders = new HttpHeaders();
		entityHeaders.setContentType(MediaType.APPLICATION_JSON);
		entityHeaders.add("Accept-Language", "zh-cn,zh;q=0.5");
		entityHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		
		User user = new User();
        user.setName("Joly");
        user.setBirthday(new Date());
        user.setAge(18);
		
		HttpEntity<User> requestEntity = new HttpEntity<User>(user, entityHeaders);
		
		restTemplate.postForEntity("http://localhost:8080/wechat-server/user/add", requestEntity, User.class);
		
	}
	
	@Test
	public void testJson2() {
		RestTemplate restTemplate = buildRestTemplate();
		
		HttpHeaders entityHeaders = new HttpHeaders();
		entityHeaders.setContentType(MediaType.APPLICATION_JSON);
		entityHeaders.add("Accept-Language", "zh-cn,zh;q=0.5");
		entityHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		
		User user = new User();
        user.setName("Joly");
        user.setBirthday(new Date());
        user.setAge(18);
		
		HttpEntity<User> requestEntity = new HttpEntity<User>(user, entityHeaders);
		
		restTemplate.postForEntity("http://localhost:8080/wechat-online/entry/add", requestEntity, User.class);
		
	}

}
