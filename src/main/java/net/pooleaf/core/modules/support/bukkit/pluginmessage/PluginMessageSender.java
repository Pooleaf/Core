package net.pooleaf.core.modules.support.bukkit.pluginmessage;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class PluginMessageSender {

  /**
   * 플러그인 메시지를 전송합니다.
   * @param plugin 플러그인
   * @param sender 보낼 플레이어
   * @param messageChannel 플러그인 메시지 채널
   * @param objects 전송할 데이터
   */
  public static void sendPluginMessage(JavaPlugin plugin, Player sender, String messageChannel, Object... objects) {
    ByteArrayDataOutput out = ByteStreams.newDataOutput();
    writeObject(out, objects);

    Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> sender.sendPluginMessage(plugin, messageChannel, out.toByteArray()));
  }

  /**
   * 플러그인 메시지를 전송합니다.
   * @param plugin 플러그인
   * @param messageChannel 플러그인 메시지 채널
   * @param objects 전송할 데이터
   */
  public static void sendPluginMessage(JavaPlugin plugin, String messageChannel, Object... objects) {
    if (Bukkit.getOnlinePlayers().size() < 1) return;

    Player sender = Bukkit.getOnlinePlayers().stream().findFirst().get();

    sendPluginMessage(plugin, sender, messageChannel, objects);
  }

  private static void writeObject(ByteArrayDataOutput out, Object... objects) {
    for (int i = 0; i < objects.length; i++) {
      Object object = objects[i];
      if (object instanceof String) {
        out.writeUTF((String) object);
      } else if (object instanceof Integer) {
        out.writeInt((Integer) object);
      } else if (object instanceof Short) {
        out.writeShort((Short) object);
      } else if (object instanceof Long) {
        out.writeLong((Long) object);
      } else if (object instanceof Float) {
        out.writeFloat((Float) object);
      } else if (object instanceof Double) {
        out.writeDouble((Double) object);
      } else if (object instanceof Boolean) {
        out.writeBoolean((Boolean) object);
      }
    }
  }

}
