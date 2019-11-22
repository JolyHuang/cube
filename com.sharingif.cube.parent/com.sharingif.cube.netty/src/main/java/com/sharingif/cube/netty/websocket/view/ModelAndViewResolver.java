package com.sharingif.cube.netty.websocket.view;

import com.sharingif.cube.core.exception.handler.ExceptionContent;
import com.sharingif.cube.core.request.RequestContext;
import com.sharingif.cube.core.view.ModelAndView;
import com.sharingif.cube.core.view.View;
import com.sharingif.cube.core.view.ViewResolver;

public class ModelAndViewResolver implements ViewResolver {

    private ChannelView channelView;
    private ChannelAllView channelAllView;

    public void setChannelView(ChannelView channelView) {
        this.channelView = channelView;
    }

    public void setChannelAllView(ChannelAllView channelAllView) {
        this.channelAllView = channelAllView;
    }

    @Override
    public View resolveView(RequestContext<?> requestContext, Object returnValue, ExceptionContent exceptionContent) {
        if(returnValue instanceof ModelAndView) {
            return channelAllView;
        }
        return channelView;
    }

}
