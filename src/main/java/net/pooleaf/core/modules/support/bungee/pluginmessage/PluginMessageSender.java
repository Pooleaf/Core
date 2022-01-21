package net.pooleaf.core.modules.support.bungee.pluginmessage;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Plugin;

public class PluginMessageSender {

  /**
   * 서버로 플러그인 메시지를 전송합니다.
   * 전송받는 서버에 플레이어가 1명 이상 접속 중일 때만 전송됩니다.
   * @param serverInfo 데이터를 받을 서버
   * @param plugin 플러그인
   * @param messageChannel 플러그인 메시지 채널
   * @param bytes 전송할 데이터
   */
  private static void send(Plugin plugin, ServerInfo serverInfo, String messageChannel, byte[] bytes) {
    ProxyServer.getInstance().getScheduler().runAsync(plugin, () -> serverInfo.sendData(messageChannel, bytes));
  }

  /**
   * 서버로 플러그인 메시지를 전송합니다.
   * 전송받는 서버에 플레이어가 1명 이상 접속 중일 때만 전송됩니다.
   * @param serverInfo 데이터를 받을 서버
   * @param plugin 플러그인
   * @param messageChannel 플러그인 메시지 채널
   * @param objects 전송할 데이터
   */
  public static void send(Plugin plugin, ServerInfo serverInfo, String messageChannel, Object... objects) {
    ByteArrayDataOutput out = ByteStreams.newDataOutput();

    writeObject(out, objects);

    send(plugin, serverInfo, messageChannel, out.toByteArray());
  }

  /**
   * 플레이어가 1명 이상 접속 중인 모든 서버로 데이터를 전송합니다.
   * @param plugin 플러그인
   * @param messageChannel 플러그인 메시지 채널
   * @param bytes 전송할 데이터
   */
  private static void sendToAll(Plugin plugin, String messageChannel, byte[] bytes) {
    for (ServerInfo serverInfo : ProxyServer.getInstance().getServers().values()) {
      send(plugin, serverInfo, messageChannel, bytes);
    }
  }

  /**
   * 플레이어가 1명 이상 접속 중인 모든 서버로 데이터를 전송합니다.
   * @param plugin 플러그인
   * @param messageChannel 플러그인 메시지 채널
   * @param objects 전송할 데이터
   */
  public static void sendToAll(Plugin plugin, String messageChannel, Object... objects) {
    ByteArrayDataOutput out = ByteStreams.newDataOutput();
    writeObject(out, objects);

    byte[] newBytes = out.toByteArray();

    for (ServerInfo serverInfo : ProxyServer.getInstance().getServers().values()) {
      if (serverInfo.getPlayers().size() < 1) continue;

      send(plugin, serverInfo, messageChannel, newBytes);
    }
  }

  /**
   * 해당 플레이어가 접속 중인 서버로 데이터를 전송합니다.
   * @param playerName 전송 받을 플레이어
   * @param plugin 플러그인
   * @param messageChannel 플러그인 메시지 채널
   * @param bytes 전송할 데이터
   */
  private static void sendHasPlayer(Plugin plugin, String playerName, String messageChannel, byte[] bytes) {
    ProxiedPlayer player = ProxyServer.getInstance().getPlayer(playerName);
    if (player == null) return;

    send(plugin, player.getServer().getInfo(), messageChannel, bytes);
  }

  /**
   * 해당 플레이어가 접속 중인 서버로 데이터를 전송합니다.
   * @param playerName 전송 받을 플레이어
   * @param plugin 플러그인
   * @param messageChannel 플러그인 메시지 채널
   * @param objs 전송할 데이터
   */
  public static void sendHasPlayer(Plugin plugin, String playerName, String messageChannel, Object...objs) {
    ProxiedPlayer player = ProxyServer.getInstance().getPlayer(playerName);
    if (player == null) return;

    ByteArrayDataOutput out = ByteStreams.newDataOutput();
    writeObject(out, objs);

    send(plugin, player.getServer().getInfo(), messageChannel, out.toByteArray());
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
