package com.redbean0721.essentialsx.WebSocket;

import io.github.cdimascio.dotenv.Dotenv;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Logger;

public class WebSocketClientHandler {
    public static WebSocketClient webSocketClient;
    private int reconnectAttempts = 0;
    private final int maxReconnectAttempts = 10;
    private final long reconnectDelay = 3000;

    Dotenv dotenv = Dotenv.load();

    public void connectWebsocket(Logger logger) {
        URI uri = null;
        try {
            uri = new URI(dotenv.get("WEBSOCKET_URL"));
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
        webSocketClient = new WebSocketClient(uri) {
            @Override
            public void onOpen(ServerHandshake serverHandshake) {
                logger.info("WebSocket connection opened");
            }

            @Override
            public void onMessage(String message) {
//                logger.info("Received message: " + message);
                logger.info(message);
            }

            @Override
            public void onClose(int i, String s, boolean b) {
                logger.info("WebSocket connection closed");

                if (reconnectAttempts < maxReconnectAttempts) {
                    logger.info("Attempting to reconnect in 3 seconds...");
                    scheduleReconnect(logger);
                } else {
                    logger.warning("Reached maximum reconnection attempts. Stopping reconnect attempts.");
                }
            }

            @Override
            public void onError(Exception e) {
                logger.warning("WebSocket error: " + e.getMessage());
            }
        };
//        webSocketClient.connect();
    }

    private void scheduleReconnect(Logger logger) {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                logger.info("Attempting reconnect...");
                webSocketClient.connect();
                reconnectAttempts++;
            }
        }, reconnectDelay);
    }
}
