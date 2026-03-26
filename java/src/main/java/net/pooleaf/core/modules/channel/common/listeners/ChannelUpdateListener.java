package net.pooleaf.core.modules.channel.common.listeners;

import net.pooleaf.core.modules.commonevent.common.CommonEventHandler;
import net.pooleaf.core.modules.commonevent.common.CommonEventListener;
import net.pooleaf.core.modules.redislib.common.events.RedisKeySpaceEvent;
import net.pooleaf.core.Core;
import net.pooleaf.core.modules.channel.ChannelModule;
import net.pooleaf.core.modules.channel.common.channel.Channel;
import net.pooleaf.core.modules.channel.common.channel.KnownChannelStatus;
import net.pooleaf.core.modules.channel.common.channelgroup.ChannelGroup;
import net.pooleaf.core.modules.channel.common.events.ChannelGroupUpdateEvent;
import net.pooleaf.core.modules.channel.common.events.ChannelUpdateEvent;
import net.pooleaf.core.modules.commonevent.CommonEventModule;
import net.pooleaf.core.modules.commonscheduler.CommonSchedulerModule;

public class ChannelUpdateListener implements CommonEventListener {

  @CommonEventHandler
  public void onChannelUpdate(RedisKeySpaceEvent event) {
    // Channel 업데이트
    if (event.getKey().startsWith(ChannelModule.getRedisManager().channel().CHANNEL_INFO_KEY)) {

      String channelName = event.getKey().substring(ChannelModule.getRedisManager().channel().CHANNEL_INFO_KEY.length());
      Channel channel = ChannelModule.getRedisManager().channel().loadChannel(channelName);

      // Redis에서 해당 채널이 삭제되거나 TTL 만료되면 오프라인 처리
      if (event.getTask().equals("del") || event.getTask().equals("expired")) {
        Channel existingChannel = ChannelModule.getChannelManager().get(channelName);
        if (existingChannel != null) {
          existingChannel.setOnline(false);
          existingChannel.setChannelStatus(KnownChannelStatus.OFFLINE);
          existingChannel.setPlayerCount(0);
          existingChannel.setMaxPlayerCount(0);
          existingChannel.getPlayerNames().clear();
          existingChannel.getPlayerUuids().clear();
        }
        ChannelModule.getChannelManager().remove(channelName);
        CommonEventModule.callEvent(new ChannelUpdateEvent(existingChannel != null ? existingChannel : channel));
      }
      // 업데이트 됐으면 Redis에서 새로 불러오기
      else {
        if (channel == null) {
          return;
        }

        CommonSchedulerModule.getScheduler().runAsync(Core.getPlugin(), () -> {
          channel.load();
          CommonEventModule.callEvent(new ChannelUpdateEvent(channel));
        });
      }
    }

    // ChannelGroup 업데이트
    else if (event.getKey().startsWith(ChannelModule.getRedisManager().channelGroup().CHANNEL_GROUP_INFO_KEY)) {
      String channelGroupName = event.getKey().substring(ChannelModule.getRedisManager().channelGroup().CHANNEL_GROUP_INFO_KEY.length());
      ChannelGroup channelGroup = ChannelModule.getRedisManager().channelGroup().loadGroup(channelGroupName);

      // Redis에서 해당 채널 그룹이 삭제되면 객체 삭제
      if (event.getTask().equals("del")) {
        ChannelModule.getChannelGroupManager().remove(channelGroupName);
      }
      // 업데이트 됐으면 Redis에서 새로 불러오기
      else {
        if (channelGroup == null) {
          return;
        }

        CommonSchedulerModule.getScheduler().runAsync(Core.getPlugin(), () -> {
          channelGroup.load();
          CommonEventModule.callEvent(new ChannelGroupUpdateEvent(channelGroup));
        });
      }
    }
  }

}
