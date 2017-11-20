package com.sharingif.cube.core.handler.mapping;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sharingif.cube.core.exception.CubeException;
import com.sharingif.cube.core.handler.exception.NoHandlerMappingFoundException;
import com.sharingif.cube.core.request.RequestContext;

/**
 * HandlerMapping 集合
 * 2017/5/20 下午7:40
 * @author Joly
 * @version v1.0
 * @since v1.0
 */
public class MultiHandlerMapping implements HandlerMapping<Object,Object> {

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    @SuppressWarnings("rawtypes")
	private List<HandlerMapping> handlerMappings;

    @SuppressWarnings("rawtypes")
    public List<HandlerMapping> getHandlerMappings() {
        return handlerMappings;
    }

    @SuppressWarnings("rawtypes")
    public void setHandlerMappings(List<HandlerMapping> handlerMappings) {
        this.handlerMappings = handlerMappings;
    }

    @SuppressWarnings("unchecked")
	@Override
    public Object getHandler(RequestContext<Object> requestContext) throws CubeException {
        for (HandlerMapping<Object,Object> hm : getHandlerMappings()) {
            Object handler = hm.getHandler(requestContext);
            if (handler != null) {
                return handler;
            }
        }

        logger.error("No mapping found for request with URI {}", requestContext.getLookupPath());
        throw new NoHandlerMappingFoundException();
    }

}
