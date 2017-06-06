package com.sharingif.cube.core.exception.handler;

import com.sharingif.cube.core.config.CubeConfigure;
import com.sharingif.cube.core.exception.CubeRuntimeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * 错误消息码转换
 * 2017/6/6 下午3:01
 * @author Joly
 * @version v1.0
 * @since v1.0
 */
public class ExceptionMessageConversion {

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    private Properties properties = null;

    public ExceptionMessageConversion() {
        this("config/app/ErrorMessageConfigure.properties");
    }

    public ExceptionMessageConversion(String errorMessageConfigurePath) {
        InputStream in = null;
        try {
            in = CubeConfigure.class.getClassLoader().getResourceAsStream(errorMessageConfigurePath);
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
    }

    public String convert(String message){

        try {
            return properties.getProperty(message);
        } catch (Exception e) {}

        return message;
    }

}
