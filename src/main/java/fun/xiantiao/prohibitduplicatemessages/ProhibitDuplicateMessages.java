package fun.xiantiao.prohibitduplicatemessages;

import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;

/**
 * @author xiantiao
 */
public final class ProhibitDuplicateMessages extends JavaPlugin implements Listener {
    private static ProhibitDuplicateMessages instance;
    public static ProhibitDuplicateMessages getInstance() {
        return instance;
    }
    @Override
    public void onEnable() {
        instance = this;

        saveDefaultConfig();reloadConfig();

        ChatInfo.chatInfo = new HashMap<>();

        getServer().getPluginManager().registerEvents(new ChatInfo(),this);
    }
}
