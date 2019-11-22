package com.sharingif.cube.netty.websocket.view;

import com.sharingif.cube.core.exception.handler.ExceptionContent;
import com.sharingif.cube.core.request.RequestContext;
import com.sharingif.cube.core.view.AbstractJsonView;
import com.sharingif.cube.core.view.JsonModel;
import com.sharingif.cube.netty.websocket.request.WebSocketRequestContext;
import com.sharingif.cube.netty.websocket.response.JsonResponse;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

public class ChannelView extends AbstractJsonView {

    @Override
    public void view(RequestContext<?> requestContext, Object returnValue, ExceptionContent exceptionContent) {

        JsonModel jsonModel = getJsonModel(returnValue, exceptionContent == null ? null: exceptionContent.getCubeException());

        JsonResponse jsonResponse = new JsonResponse();
        jsonResponse.setLookupPath(requestContext.getLookupPath());
        jsonResponse.set_data(jsonModel.get_data());
        jsonResponse.set_tranStatus(jsonModel.get_tranStatus());
        jsonResponse.set_fieldErrors(jsonModel.get_fieldErrors());
        jsonResponse.set_exceptionMessage(jsonModel.get_exceptionMessage());
        jsonResponse.set_exceptionLocalizedMessage(jsonModel.get_exceptionLocalizedMessage());

        String jsonData =  objectoJson(jsonResponse);

        TextWebSocketFrame textWebSocketFrame = new TextWebSocketFrame(jsonData);

        WebSocketRequestContext webSocketRequestContext = (WebSocketRequestContext)requestContext;
        ChannelHandlerContext channelHandlerContext = webSocketRequestContext.getChannelHandlerContext();
        channelHandlerContext.writeAndFlush(textWebSocketFrame);
    }

}
