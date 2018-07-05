package com.sharingif.cube.web.vert.x.handler.mapping;

import java.util.LinkedHashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sharingif.cube.core.exception.CubeException;
import com.sharingif.cube.core.handler.mapping.AbstractHandlerMapping;
import com.sharingif.cube.core.request.RequestContext;

import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.StaticHandler;

/**
 * 处理静态页面
 * 2017/5/26 下午5:45
 *
 * @author Joly
 * @version v1.0
 * @since v1.0
 */
public class StaticHandlerMapping extends AbstractHandlerMapping<RoutingContext,StaticHandler> {

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    private final Map<String, StaticHandler> urlMap = new LinkedHashMap<String, StaticHandler>();

    public void setUrlMap(Map<String, StaticHandler> urlMap) {
        this.urlMap.putAll(urlMap);
    }
    public Map<String, StaticHandler> getUrlMap() {
        return urlMap;
    }

    @Override
    protected StaticHandler getHandlerInternal(RequestContext<RoutingContext> request) throws CubeException {
        for(String pattern : urlMap.keySet()) {
            if(getPathMatcher().match(pattern, request.getLookupPath())) {

                StaticHandler staticHandler = urlMap.get(pattern);

                logger.debug("Returning handler method [{}]", staticHandler.getClass().getSimpleName());

                return staticHandler;
            }

        }

        logger.debug("Did not find handler method for [" + request.getLookupPath() + "]");

        return null;
    }

}
