package com.sharingif.cube.web.springmvc.servlet.view.json;

import com.sharingif.cube.core.config.CubeConfigure;
import com.sharingif.cube.core.exception.CubeRuntimeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;
import org.springframework.web.util.UrlPathHelper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.Properties;

/**
 * json 视图挡板
 * 2017/6/1 上午10:54
 * @author Joly
 * @version v1.0
 * @since v1.0
 */
public class MockMappingJackson2JsonView extends MappingJackson2JsonView {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    private Properties properties;
    private UrlPathHelper urlPathHelper = new UrlPathHelper();

    public MockMappingJackson2JsonView(String filePath) {
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
    public void render(Map<String, ?> model, HttpServletRequest request, HttpServletResponse response) throws Exception {
        response.setContentType("application/json");
        String lookupPath = urlPathHelper.getLookupPathForRequest(request);
        String value = (String) properties.get(lookupPath);
        response.getOutputStream().write(value.getBytes(CubeConfigure.DEFAULT_ENCODING));
    }


}
