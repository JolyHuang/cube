import com.sharingif.cube.netty.websocket.WebSocketClientBootstrap;

public class WebSocketClient {

    public static void main(String[] args) throws Exception {
        new WebSocketClientBootstrap("wss://ws.coincap.io/prices?assets=bitcoin,ethereum").start();
    }
}
