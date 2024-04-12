package fun.xiantiao.prohibitduplicatemessages;

import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author xiantiao
 */
public final class ProhibitDuplicateMessages extends JavaPlugin implements Listener {
    private static ProhibitDuplicateMessages instance;
    private static final MiniMessage MINI_MESSAGE = MiniMessage.miniMessage();
    public static ProhibitDuplicateMessages getInstance() {
        return instance;
    }
    @Override
    public void onEnable() {
        instance = this;

        saveDefaultConfig();reloadConfig();

        /*
        初始化变量
         */
        ChatInfo.chatInfo = new HashMap<>();
        ChatInfo.limitTime = getConfig().getLong("allowTime");

        List<String> message = getConfig().getStringList("message").stream()
                .map(str -> str.replaceAll("&", "§"))
                .collect(Collectors.toList());
        for (String s : message) {
            ChatInfo.messages.add(MINI_MESSAGE.deserialize(s));
        }

        getServer().getPluginManager().registerEvents(new ChatInfo(),this);
    }
}
