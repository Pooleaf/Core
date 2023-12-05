package net.pooleaf.core.modules.channel.bungee.commands;

import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.pooleaf.core.modules.annocommand.common.Command;
import net.pooleaf.core.modules.annocommand.common.CommandResult;
import net.pooleaf.core.modules.commonsender.CommonSenderModule;
import net.pooleaf.core.modules.commonsender.common.CommonPlayer;
import net.pooleaf.core.modules.support.common.messager.Messager;

public class GotoCommand {

  @Command(
          parent = {"", "core"},
          name = {"찾아가기", "goto"},
          arguments = "<플레이어>",
          description = "."
  )
  public void goTo(ProxiedPlayer player, CommandResult result) {
    CommonPlayer targetCommonPlayer = CommonSenderModule.getOnlinePlayerByDisplayName(result.getArgument(0));
    if (targetCommonPlayer == null) {
      Messager.sendWarning(player, "접속 중이 아닌 플레이어입니다.");
      return;
    }

    ProxiedPlayer targetPlayer = (ProxiedPlayer) targetCommonPlayer.getPlatformSender();
    if (player.getServer().getInfo().equals(targetPlayer.getServer().getInfo())) {
      Messager.sendWarning("이미 같은 채널에 접속 중입니다.");
      return;
    }

    player.connect(targetPlayer.getServer().getInfo());
    Messager.sendMessage(player, targetPlayer.getDisplayName() + " §e님이 접속 중인 채널로 이동했습니다.");
  }

}