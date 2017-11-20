package com.sharingif.cube.web.vert.x.handler.mapping;

import com.sharingif.cube.communication.http.HttpMethod;
import com.sharingif.cube.core.exception.CubeException;
import com.sharingif.cube.core.handler.mapping.AbstractHandlerMapping;
import com.sharingif.cube.core.request.RequestContext;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.CorsHandler;

/**
 * 处理跨域
 *
 * @author Joly
 * @version v1.0
 * @since v1.0
 * 2017/9/30 上午11:38
 */
public class CORSHandlerMapping extends AbstractHandlerMapping<RoutingContext,CorsHandler> {

    private CorsHandler corsHandler;

    public void setCorsHandler(CorsHandler corsHandler) {
        this.corsHandler = corsHandler;
    }

    @Override
    protected CorsHandler getHandlerInternal(RequestContext<RoutingContext> request) throws CubeException {

        if(request.getMethod().equals(HttpMethod.OPTIONS.toString())) {
            return corsHandler;
        }

        return null;
    }

}
