package com.sharingif.cube.netty.websocket;

import io.netty.channel.Channel;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.ChannelGroupFuture;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

public class WebSocketChannelGroup {

    private static ChannelGroup channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    public static void addChannel(Channel channel){
        channelGroup.add(channel);
    }

    public static void removeChannel(Channel channel) {
        channelGroup.remove(channel);
    }

    public static ChannelGroupFuture sendMessage(Object obj) {

        return channelGroup.writeAndFlush(obj);
    }

}
