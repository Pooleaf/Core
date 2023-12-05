package net.pooleaf.core.modules.channel.common.commands;

import net.pooleaf.core.modules.annocommand.common.Command;
import net.pooleaf.core.modules.annocommand.common.CommandResult;
import net.pooleaf.core.modules.channel.ChannelModule;
import net.pooleaf.core.modules.channel.common.ChannelPermission;
import net.pooleaf.core.modules.channel.common.channelgroup.LobbyChannelGroup;
import net.pooleaf.core.modules.commonsender.common.CommonPlayer;

public class LobbyCommand {

  @Command(
          parent = {"", "core channel"},
          name = {"로비", "lobby", "fhql"},
          description = "로비 채널로 이동합니다."
  )
  public void lobby(CommonPlayer player, CommandResult result) {
    LobbyChannelGroup lobbyChannelGroup = ChannelModule.getLobbyChannelGroup();
    if (lobbyChannelGroup == null) {
      player.sendWarning("로비가 설정되지 않았습니다.");
      return;
    }

    if (lobbyChannelGroup.fastJoin(player.getUuid()) == null) {
      player.sendWarning("접속 가능한 로비 채널이 없습니다.");
      return;
    }

    player.sendMessage("§e로비 채널로 이동합니다.");
  }

}