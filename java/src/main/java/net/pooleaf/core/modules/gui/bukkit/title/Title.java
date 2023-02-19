package net.pooleaf.core.modules.gui.bukkit.title;

import net.pooleaf.core.modules.support.bukkit.util.BukkitReflectionUtil;
import lombok.Data;
import lombok.SneakyThrows;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

@Data
public class Title {

    private String title;
    private String subTitle;
    private int fadeIn;
    private int stay;
    private int fadeOut;


    @SneakyThrows
    public void send(Player player) {
        // Color
        if (title != null) {
            title = ChatColor.translateAlternateColorCodes('&', title);
        }

        if (subTitle != null) {
            subTitle = ChatColor.translateAlternateColorCodes('&', subTitle);
        }
        // 서브 타이틀을 보내지 않으면 이전 타이틀이 끝나지 않았을 때 새로운 타이틀을 보내면 이전 서브 타이틀이 적용됨
        else {
            subTitle = "";
        }

        // Get nms class
        Class packetClass = BukkitReflectionUtil.getNmsClass("PacketPlayOutTitle");
        Class packetEnumClass = packetClass.getDeclaredClasses()[0];
        Class componentClass = BukkitReflectionUtil.getNmsClass("IChatBaseComponent");

        // Send time packet
        Object timePacket = packetClass.getConstructor(int.class, int.class, int.class).newInstance(fadeIn, stay, fadeOut);
        BukkitReflectionUtil.sendPacket(player, timePacket);

        // Send sub title packet
        if (subTitle != null) {
            Object subTitleValue = packetEnumClass.getField("SUBTITLE").get(null);
            Object subTitleChatComponent = componentClass.getDeclaredClasses()[0].getMethod("a", String.class)
                .invoke(null, "{\"text\":\"" + subTitle + "\"}");
            Object titlePacket = packetClass.getConstructor(packetEnumClass, componentClass).newInstance(subTitleValue, subTitleChatComponent);
            BukkitReflectionUtil.sendPacket(player, titlePacket);
        }

        // Send title packet
        Object titleValue = packetEnumClass.getField("TITLE").get(null);
        Object chatComponent = componentClass.getDeclaredClasses()[0].getMethod("a", String.class)
            .invoke(null, "{\"text\":\"" + title + "\"}");
        Object titlePacket = packetClass.getConstructor(packetEnumClass, componentClass).newInstance(titleValue, chatComponent);
        BukkitReflectionUtil.sendPacket(player, titlePacket);
    }

    public void sendSafely(Player player) {
        if (player != null && player.isOnline()) {
            send(player);
        }
    }

}
