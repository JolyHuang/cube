package com.sharingif.cube.core.handler.mapping;

import com.sharingif.cube.core.exception.CubeException;
import com.sharingif.cube.core.handler.exception.NoHandlerMappingFoundException;
import com.sharingif.cube.core.request.RequestInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * HandlerMapping 集合
 * 2017/5/20 下午7:40
 * @author Joly
 * @version v1.0
 * @since v1.0
 */
public class MultiHandlerMapping<T> implements HandlerMapping<T> {

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    private List<HandlerMapping<T>> handlerMappings;

    public List<HandlerMapping<T>> getHandlerMappings() {
        return handlerMappings;
    }

    public void setHandlerMappings(List<HandlerMapping<T>> handlerMappings) {
        this.handlerMappings = handlerMappings;
    }

    @Override
    public T getHandler(RequestInfo<?> requestInfo) throws CubeException {
        for (HandlerMapping<T> hm : getHandlerMappings()) {
            T handler = hm.getHandler(requestInfo);
            if (handler != null) {
                return handler;
            }
        }

        logger.error("No mapping found for request with URI {}", requestInfo.getLookupPath());
        throw new NoHandlerMappingFoundException();
    }

}
