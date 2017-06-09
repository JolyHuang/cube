package com.sharingif.cube.components.json;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sharingif.cube.core.config.CubeConfigure;
import com.sharingif.cube.core.exception.CubeRuntimeException;

import java.util.TimeZone;

/**
 *
 * @Description:  [用一句话描述该文件做什么]
 * @Author:       [Joly]
 * @CreateDate:   [2014年8月12日 上午10:47:18]
 * @UpdateUser:   [Joly]
 * @UpdateDate:   [2014年8月12日 上午10:47:18]
 * @UpdateRemark: [说明本次修改内容]
 * @Version:      [v1.0]
 *
 */
public class JacksonJsonService implements IJsonService {
	
	private ObjectMapper objectMapper;

	public JacksonJsonService() {
		objectMapper = new ObjectMapper();
		objectMapper.setTimeZone(TimeZone.getTimeZone(CubeConfigure.DEFAULT_TIME_ZONE));
	}
	
	public ObjectMapper getObjectMapper() {
		return objectMapper;
	}
	public void setObjectMapper(ObjectMapper objectMapper) {
		this.objectMapper = objectMapper;
	}


	@Override
	public String objectoJson(Object obj) {
		try {
			return objectMapper.writeValueAsString(obj);
		} catch (Exception e) {
			throw new CubeRuntimeException("convert object to json error", e);
		}
	}
	
}
