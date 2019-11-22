import com.sharingif.cube.netty.websocket.WebSocketServerBootstrap;
import com.sharingif.cube.netty.websocket.handler.WebSocketServerChannelInitializer;

public class WebSocketServer {

    public static void main(String[] args) throws Exception {
        WebSocketServerChannelInitializer webSocketChannelInitializer = new WebSocketServerChannelInitializer();
        webSocketChannelInitializer.setPath("/ws");

        WebSocketServerBootstrap webSocketServerBootstrap = new WebSocketServerBootstrap();
        webSocketServerBootstrap.setPort(8888);
        webSocketServerBootstrap.setWebSocketServerChannelInitializer(webSocketChannelInitializer);

        webSocketServerBootstrap.start();
    }

}
