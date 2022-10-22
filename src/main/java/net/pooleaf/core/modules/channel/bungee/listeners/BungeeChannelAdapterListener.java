package net.pooleaf.core.modules.channel.bungee.listeners;

import java.util.Arrays;
import java.util.UUID;
import net.pooleaf.core.modules.channel.ChannelModule;
import net.pooleaf.core.modules.channel.common.channel.Channel;
import net.pooleaf.core.modules.channel.common.events.ChannelMessageEvent;
import net.pooleaf.core.modules.commonevent.CommonEventModule;
import net.pooleaf.core.modules.commonevent.common.CommonEventHandler;
import net.pooleaf.core.modules.commonevent.common.CommonEventListener;
import net.pooleaf.core.modules.redislib.common.events.RedisMessageEvent;
import net.pooleaf.core.modules.support.common.logger.Logger;
import net.pooleaf.core.modules.support.common.platform.Platform;

public class BungeeChannelAdapterListener implements CommonEventListener {

  @CommonEventHandler
  public void onAdapterMessageReceived(RedisMessageEvent event) {
    if (!Platform.getCurrentPlatform().equals(Platform.BUNGEECORD)) {
      return;
    }
    if (!event.getMessageChannel().equals(ChannelModule.MESSAGE_CHANNEL)) {
      return;
    }

    String task = (String) event.getDatas().get(0);
    if (task.equals("JoinByPlayerName")) {
      String channelName = (String) event.getDatas().get(1);
      String playerName = (String) event.getDatas().get(2);

      Channel channel = ChannelModule.getChannel(channelName);
      if (channel != null) {
        channel.join(playerName);
      }
    } else if (task.equals("JoinByPlayerUuid")) {
      String channelName = (String) event.getDatas().get(1);
      UUID playerUuid = UUID.fromString((String) event.getDatas().get(2));

      Channel channel = ChannelModule.getChannel(channelName);
      if (channel != null) {
        channel.join(playerUuid);
      }
    } else if (task.equals("Broadcast")) {
      String channelName = (String) event.getDatas().get(1);
      String senderName = (String) event.getDatas().get(2);
      String message = (String) event.getDatas().get(3);

      Channel channel = ChannelModule.getChannel(channelName);
      if (channel != null) {
        Logger.log("§e[원격 공지] §f" + senderName + " §e→ §f" + channel.getName() + ": " + message);
      }
    } else if (task.equals("RemoteCommand")) {
      String channelName = (String) event.getDatas().get(1);
      String senderName = (String) event.getDatas().get(2);
      String commandLine = (String) event.getDatas().get(3);

      Channel channel = ChannelModule.getChannel(channelName);
      if (channel != null) {
        Logger.log("§e[원격 명령] §f" + senderName + " §e→ §f" + channel.getName() + ": " + commandLine);
      }
    } else if (task.equals("SendData")) {
      String channelName = (String) event.getDatas().get(1);
      String dataTask = (String) event.getDatas().get(2);
      Object[] datas = (Object[]) event.getDatas().get(3);

      // 번지코드에 보낸거면 번지코드에서 처리
      if (channelName.equalsIgnoreCase(ChannelModule.getRedisManager().BUNGEECORD_CHANNEL)) {
        ChannelMessageEvent channelMessageEvent = new ChannelMessageEvent(dataTask, Arrays.asList(datas));
        CommonEventModule.callEvent(channelMessageEvent);
      }
    }
  }

}
