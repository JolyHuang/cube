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
			defaultEncoding = properties.getProperty("cube.default.encoding").trim();
		} catch (Exception e) {
			defaultEncoding = "UTF-8";
		}
		DEFAULT_ENCODING = defaultEncoding;
		logger.info("system initialization parameter DEFAULT_ENCODING=[{}]",DEFAULT_ENCODING);

		EXTERNAL_CONFIGURE = properties.getProperty("cube.external.configure").trim();
		logger.info("system initialization parameter EXTERNAL_CONFIGURE=[{}]",EXTERNAL_CONFIGURE);
		
		String defaultTimeZone = null;
		try {
			defaultTimeZone = properties.getProperty("cube.default.time.zone").trim();
		} catch (Exception e) {
			defaultTimeZone = "GMT+0800";// 中国上海
		}
		DEFAULT_TIME_ZONE = defaultTimeZone;
		logger.info("system initialization parameter DEFAULT_TIME_ZONE=[{}]",DEFAULT_TIME_ZONE);
		
	}
	
}
