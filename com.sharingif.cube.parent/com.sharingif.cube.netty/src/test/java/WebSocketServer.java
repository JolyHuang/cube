import com.sharingif.cube.netty.websocket.WebSocketServerBootstrap;
import com.sharingif.cube.netty.websocket.handler.WebSocketChannelInitializer;

public class WebSocketServer {

    public static void main(String[] args) throws Exception {
        WebSocketChannelInitializer webSocketChannelInitializer = new WebSocketChannelInitializer();
        webSocketChannelInitializer.setPath("/ws");

        WebSocketServerBootstrap webSocketServerBootstrap = new WebSocketServerBootstrap();
        webSocketServerBootstrap.setPort(8888);
        webSocketServerBootstrap.setWebSocketChannelInitializer(webSocketChannelInitializer);

        webSocketServerBootstrap.start();
    }

}
