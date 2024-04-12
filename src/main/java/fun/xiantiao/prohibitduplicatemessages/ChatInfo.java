package fun.xiantiao.prohibitduplicatemessages;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author xiantiao
 * @date 2024/4/11
 * ProhibitDuplicateMessages
 */
public class ChatInfo implements Listener {
    static long limitTime = 10000;
    static Map<Player,Map<Long,String>> chatInfo;

    @EventHandler
    public void chat(AsyncPlayerChatEvent event) {
        // 初始化变量
        Player player = event.getPlayer();


        // 从内存获取玩家历史消息记录
        Map<Long,String> playerChatInfo = chatInfo.get(player);
        // 如果为空就创建一个playerChatInfo放入chatInfo
        if (playerChatInfo == null) {
            Map<Long,String> now = new HashMap<>();
            now.put(System.currentTimeMillis(),event.getMessage());
            chatInfo.put(player,now);
            return;
        }

        player.sendMessage(ChatColor.YELLOW + "debug-001");

        // debug
        if ("debug-1".equals(event.getMessage())) {
            event.setCancelled(true);
            player.sendMessage(ChatColor.YELLOW + "debug-1");
            player.sendMessage(playerChatInfo.toString());
            return;
        }
        // debug

        player.sendMessage(ChatColor.YELLOW + "debug-002");

        // 历遍玩家的历史聊天消息，清理过期的数据
        Set<Map.Entry<Long, String>> entries = new HashSet<>(playerChatInfo.entrySet()); // 防并行修改
        for (Map.Entry<Long, String> entry : entries) {
            Long messageSendTime = entry.getKey(); // 这条消息的发送时间

            // 判断这条消息的发送时间是否超过了limitTime，是的话删除
            // 这条消息发送的时间+limitTime < 现在的时间 就是过期了
            //  100               5           110
            if (messageSendTime + limitTime < System.currentTimeMillis()) {
                playerChatInfo.remove(messageSendTime);
            }
        }

        player.sendMessage(ChatColor.YELLOW + "debug-003");


        // 如果列表里面有玩家发送过的消息，取消事件 并且提醒
        // 否则向历史聊天记录添加记录与设置上一次消息
        if (playerChatInfo.containsValue(event.getMessage())) {
            event.setCancelled(true);
            player.sendMessage(ProhibitDuplicateMessages.getInstance().getConfig().getString("message"));
        } else {
            player.sendMessage(ChatColor.YELLOW + "debug-004");
            playerChatInfo.put(System.currentTimeMillis(),event.getMessage());
        }
    }
}
