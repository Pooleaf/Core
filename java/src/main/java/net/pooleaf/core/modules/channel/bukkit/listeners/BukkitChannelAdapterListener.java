package net.pooleaf.core.modules.channel.bukkit.listeners;

import net.pooleaf.core.Core;
import net.pooleaf.core.modules.channel.ChannelModule;
import net.pooleaf.core.modules.channel.common.events.ChannelMessageEvent;
import net.pooleaf.core.modules.commonevent.CommonEventModule;
import net.pooleaf.core.modules.commonevent.common.CommonEventHandler;
import net.pooleaf.core.modules.commonevent.common.CommonEventListener;
import net.pooleaf.core.modules.commonscheduler.CommonSchedulerModule;
import net.pooleaf.core.modules.redislib.common.events.RedisMessageEvent;
import net.pooleaf.core.modules.support.common.logger.Logger;
import net.pooleaf.core.modules.support.common.platform.Platform;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.List;

public class BukkitChannelAdapterListener implements CommonEventListener {

  @CommonEventHandler
  public void onAdapterMessageReceived(RedisMessageEvent event) {
    if (!Platform.getCurrentPlatform().equals(Platform.BUKKIT)) {
      return;
    }
    if (!event.getMessageChannel().equals(ChannelModule.MESSAGE_CHANNEL)) {
      return;
    }

    String task = (String) event.getDatas().get(0);
    if (task.equals("Broadcast")) {
      String senderName = (String) event.getDatas().get(1);
      String message = (String) event.getDatas().get(2);

      Logger.nlog("§e[원격 공지] §f" + senderName + ": " + message);

      for (Player player : Bukkit.getOnlinePlayers()) {
        player.sendMessage(message);
      }
    } else if (task.equals("RemoteCommand")) {
      String senderName = (String) event.getDatas().get(1);
      String commandLine = (String) event.getDatas().get(2);

      Logger.nlog("§e[원격 명령] §f" + senderName + ": " + commandLine);

      CommonSchedulerModule.bukkit().getScheduler().runSync(Core.getPlugin(),
          () -> Bukkit.dispatchCommand(Bukkit.getConsoleSender(), commandLine));
    } else if (task.equals("SendData")) {
      String dataTask = (String) event.getDatas().get(1);
      List<Object> datas = (List<Object>) event.getDatas().get(2);

      ChannelMessageEvent channelMessageEvent = new ChannelMessageEvent(dataTask, datas);
      CommonEventModule.callEvent(channelMessageEvent);
    }
  }

}
