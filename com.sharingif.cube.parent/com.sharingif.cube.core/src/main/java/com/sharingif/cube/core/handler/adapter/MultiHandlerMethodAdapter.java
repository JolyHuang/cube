package com.sharingif.cube.core.handler.adapter;

import com.sharingif.cube.core.exception.CubeException;
import com.sharingif.cube.core.handler.exception.NoHandlerMappingFoundException;
import com.sharingif.cube.core.request.RequestInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * HandlerMethodAdapter 集合
 * 2017/5/20 下午8:03
 * @author Joly
 * @version v1.0
 * @since v1.0
 */
public class MultiHandlerMethodAdapter<T> implements HandlerAdapter<T> {

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    private List<HandlerAdapter<T>> handlerAdapters;

    public List<HandlerAdapter<T>> getHandlerAdapters() {
        return handlerAdapters;
    }

    public void setHandlerAdapters(List<HandlerAdapter<T>> handlerAdapters) {
        this.handlerAdapters = handlerAdapters;
    }

    @Override
    public boolean supports(Object handler) {
        return true;
    }

    @Override
    public Object handle(RequestInfo<?> request, T handler) throws CubeException {

        for (HandlerAdapter<T> ha : getHandlerAdapters()) {
            if (logger.isTraceEnabled()) {
                logger.trace("Testing handler adapter [" + ha + "]");
            }
            if (ha.supports(handler)) {
                return ha.handle(request, handler);
            }
        }

        logger.error("No adapter found for handler");
        throw new NoHandlerMappingFoundException();
    }
}
