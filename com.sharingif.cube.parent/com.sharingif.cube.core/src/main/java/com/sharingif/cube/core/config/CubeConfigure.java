package com.sharingif.cube.core.config;

import com.sharingif.cube.core.exception.CubeRuntimeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * 加载cube系统文件
 * 2017年5月6日 下午10:48:33
 * @author Joly
 * @version v1.0
 * @since v1.0
 */
public final class CubeConfigure {
	
	private static final Logger logger = LoggerFactory.getLogger(CubeConfigure.class);

	public static final String DEFAULT_ENCODING;
	public static final String EXTERNAL_CONFIGURE;
	public static final String DEFAULT_TIME_ZONE;

	private static final String DEFAULT_ENCODING_KEY = "cube.default.encoding";
	private static final String EXTERNAL_CONFIGURE_KEY = "cube.external.configure";
	private static final String DEFAULT_TIME_ZONE_KEY = "cube.default.time.zone";

	static{
		Properties properties = null;
		InputStream in = null;
		try {
			in = CubeConfigure.class.getClassLoader().getResourceAsStream("config/app/CubeConfigure.properties");
			properties = new Properties();
			properties.load(in);
		} catch (Exception e) {
			logger.error("config.app.CubeConfigure file not found");
			throw new CubeRuntimeException(e);
		} finally {
			if(in != null) {
				try {
					in.close();
				} catch (IOException e) {
					logger.error("close InputStream error");
				}
			}
		}
		
		String defaultEncoding = null;
		try {
			defaultEncoding = properties.getProperty(DEFAULT_ENCODING_KEY).trim();
		} catch (Exception e) {
			notFindLogger(DEFAULT_ENCODING_KEY);
			defaultEncoding = "UTF-8";
		}
		DEFAULT_ENCODING = defaultEncoding;
		initParameterLogger(DEFAULT_ENCODING_KEY, DEFAULT_ENCODING);

		try {
			EXTERNAL_CONFIGURE = properties.getProperty(EXTERNAL_CONFIGURE_KEY).trim();
		} catch (Exception e) {
			logger.error("Could not find key '{}' in CubeConfigure.properties", EXTERNAL_CONFIGURE_KEY);
			throw e;
		}
		initParameterLogger(EXTERNAL_CONFIGURE_KEY, EXTERNAL_CONFIGURE);

		String defaultTimeZone = null;
		try {
			defaultTimeZone = properties.getProperty(DEFAULT_TIME_ZONE_KEY).trim();
		} catch (Exception e) {
			notFindLogger(DEFAULT_TIME_ZONE_KEY);
			defaultTimeZone = "GMT+0800";// 中国上海
		}
		DEFAULT_TIME_ZONE = defaultTimeZone;
		initParameterLogger(DEFAULT_TIME_ZONE_KEY, DEFAULT_TIME_ZONE);

	}

	private static void notFindLogger(String key) {
		logger.warn("Could not find key '{}' in CubeConfigure.properties", key);
	}

	private static void initParameterLogger(String key, String value) {
		logger.info("system initialization parameter {}=[{}]", key, value);
	}
	
}
