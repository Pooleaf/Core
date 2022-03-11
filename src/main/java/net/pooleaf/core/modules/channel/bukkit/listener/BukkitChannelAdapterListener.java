package net.pooleaf.core.modules.channel.bukkit.listener;

import java.util.Arrays;
import net.pooleaf.core.modules.channel.ChannelModule;
import net.pooleaf.core.modules.channel.common.event.ChannelMessageEvent;
import net.pooleaf.core.modules.commonevent.CommonEventModule;
import net.pooleaf.core.modules.commonevent.common.CommonEventHandler;
import net.pooleaf.core.modules.commonevent.common.CommonEventListener;
import net.pooleaf.core.modules.redislib.common.event.RedisMessageEvent;
import net.pooleaf.core.modules.support.common.logger.Logger;
import org.bukkit.Bukkit;

public class BukkitChannelAdapterListener implements CommonEventListener {

  @CommonEventHandler
  public void onAdapterMessageReceived(RedisMessageEvent event) {
    if (!event.getMessageChannel().equals(ChannelModule.MESSAGE_CHANNEL)) {
      return;
    }

    String task = (String) event.getDatas().get(0);
    if (task.equals("RemoteCommand")) {
      String senderName = (String) event.getDatas().get(1);
      String commandLine = (String) event.getDatas().get(2);

      Logger.nlog("§e[원격 명령] §f" + senderName + ": " + commandLine);

      Bukkit.dispatchCommand(Bukkit.getConsoleSender(), commandLine);
    } else if (task.equals("SendData")) {
      String dataTask = (String) event.getDatas().get(1);
      Object[] datas = (Object[]) event.getDatas().get(2);

      ChannelMessageEvent channelMessageEvent = new ChannelMessageEvent(dataTask, Arrays.asList(datas));
      CommonEventModule.callEvent(channelMessageEvent);
    }
  }

}
