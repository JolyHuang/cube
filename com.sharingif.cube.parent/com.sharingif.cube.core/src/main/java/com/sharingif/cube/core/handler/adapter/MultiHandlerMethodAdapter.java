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
public class MultiHandlerMethodAdapter implements HandlerAdapter<Object,Object> {

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    @SuppressWarnings("rawtypes")
	private List<HandlerAdapter> handlerAdapters;

    @SuppressWarnings("rawtypes")
	public List<HandlerAdapter> getHandlerAdapters() {
        return handlerAdapters;
    }

    @SuppressWarnings("rawtypes")
	public void setHandlerAdapters(List<HandlerAdapter> handlerAdapters) {
        this.handlerAdapters = handlerAdapters;
    }

    @Override
    public boolean supports(Object handler) {
        return true;
    }

    @SuppressWarnings("unchecked")
	@Override
    public Object handle(RequestInfo<Object> request, Object handler) throws CubeException {

        for (HandlerAdapter<Object,Object> ha : getHandlerAdapters()) {
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
