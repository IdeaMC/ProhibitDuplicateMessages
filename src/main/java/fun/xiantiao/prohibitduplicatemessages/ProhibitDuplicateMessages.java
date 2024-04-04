package fun.xiantiao.prohibitduplicatemessages;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.plugin.java.JavaPlugin;

public final class ProhibitDuplicateMessages extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        saveDefaultConfig();reloadConfig();
        getServer().getPluginManager().registerEvents(this,this);
    }

    @EventHandler
    public void chat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        String lastMessage = getConfig().getString(player.getName(),"\uD83D\uDE0B");
        if (lastMessage.equals(event.getMessage())) {
            event.setCancelled(true);
            player.sendMessage(getConfig().getString("message"));
        } else {
            getConfig().set(player.getName(),event.getMessage());
        }
    }
}
