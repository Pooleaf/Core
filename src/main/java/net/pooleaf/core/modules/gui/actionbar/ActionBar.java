package net.pooleaf.core.modules.gui.actionbar;

import lombok.SneakyThrows;
import net.pooleaf.core.modules.support.bukkit.nms.NmsVersion;
import net.pooleaf.core.modules.support.bukkit.util.BukkitReflectionUtil;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class ActionBar {

    /**
     * 플레이어에게 ActionBar를 전송합니다.
     * @param player 대상 플레이어
     * @param message ActionBar에 출력될 메시지
     */
    @SneakyThrows
    public static void sendActionBar(Player player, String message) {
        message = ChatColor.translateAlternateColorCodes('&', message);

        Class<?> chatComponentClass = BukkitReflectionUtil.getNmsClass("IChatBaseComponent");
        Class<?> chatPacketClass = BukkitReflectionUtil.getNmsClass("PacketPlayOutChat");

        Object chatComponentText = BukkitReflectionUtil.getNmsClass("ChatComponentText").getConstructor(String.class).newInstance(message);
        Object chatPacket;
        if (NmsVersion.getCurrentVersion().isBefore(NmsVersion.v1_11_R1)) {
            chatPacket = chatPacketClass
                    .getConstructor(chatComponentClass, Byte.TYPE)
                    .newInstance(chatComponentText, (byte) 2);
        } else {
            Class<?> chatMessageTypeClass = BukkitReflectionUtil.getNmsClass("ChatMessageType");

            chatPacket = chatPacketClass
                    .getConstructor(chatComponentClass, chatMessageTypeClass)
                    .newInstance(chatComponentText, chatMessageTypeClass.getField("GAME_INFO").get(null));
        }

        BukkitReflectionUtil.sendPacket(player, chatPacket);
    }

    /**
     * 플레이어의 ActionBar를 제거합니다.
     * @param player 대상 플레이어
     */
    public static void removeActionBar(Player player) {
        sendActionBar(player, null);
    }

}
