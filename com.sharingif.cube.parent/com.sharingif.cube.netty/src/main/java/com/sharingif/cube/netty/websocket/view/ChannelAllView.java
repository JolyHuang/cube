package com.sharingif.cube.netty.websocket.view;

import com.sharingif.cube.core.exception.handler.ExceptionContent;
import com.sharingif.cube.core.request.RequestContext;
import com.sharingif.cube.core.view.AbstractJsonView;
import com.sharingif.cube.core.view.ModelAndView;
import com.sharingif.cube.netty.websocket.WebSocketChannelGroup;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

public class ChannelAllView extends AbstractJsonView {

    public static final String NAME = "channelAllView";

    @Override
    public void view(RequestContext<?> requestContext, Object returnValue, ExceptionContent exceptionContent) {

        ModelAndView modelAndView = (ModelAndView)returnValue;

        String jsonData =  getResponseData(modelAndView.getModel(), exceptionContent == null ? null: exceptionContent.getCubeException());

        TextWebSocketFrame textWebSocketFrame = new TextWebSocketFrame(jsonData);

        WebSocketChannelGroup.sendMessage(textWebSocketFrame);
    }

}
