package com.sharingif.cube.communication.http.transport.transform;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sharingif.cube.communication.exception.CommunicationException;
import com.sharingif.cube.communication.transport.Connection;
import com.sharingif.cube.core.config.CubeConfigure;
import com.sharingif.cube.core.exception.CubeRuntimeException;
import com.sharingif.cube.core.request.RequestInfo;

/**
 * 模拟http json通讯
 * 2017年5月16日 下午2:26:31
 * @author Joly
 * @version v1.0
 * @since v1.0
 */
public class MockHttpJsonConnection implements Connection<RequestInfo<String>, String> {
	
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	private Properties properties;
	
	public MockHttpJsonConnection(String filePath) {
		try {
			InputStream in = CubeConfigure.class.getClassLoader().getResourceAsStream(filePath);
			properties = new Properties();
			properties.load(new BufferedReader(new InputStreamReader(in,CubeConfigure.DEFAULT_ENCODING)));
		} catch (Exception e) {
			logger.error("file path error, path:{}", filePath);
			throw new CubeRuntimeException(e);
		}
	}
	
	@Override
	public String connect(RequestInfo<String> obj) throws CommunicationException {
		this.logger.info("send message:{}", obj.getRequest());
		String receiveMessage = properties.getProperty(obj.getLookupPath());
		this.logger.info("receive message:{}", receiveMessage);
		return receiveMessage;
	}
	
}
