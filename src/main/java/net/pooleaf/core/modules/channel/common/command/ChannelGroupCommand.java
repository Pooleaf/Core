package net.pooleaf.core.modules.channel.common.command;

import net.pooleaf.core.Core;
import net.pooleaf.core.modules.annocommand.common.Command;
import net.pooleaf.core.modules.annocommand.common.CommandResult;
import net.pooleaf.core.modules.annocommand.common.HelpCommandResult;
import net.pooleaf.core.modules.channel.ChannelModule;
import net.pooleaf.core.modules.channel.common.ChannelPermission;
import net.pooleaf.core.modules.channel.common.channel.Channel;
import net.pooleaf.core.modules.commonsender.common.CommonCommandSender;
import net.pooleaf.core.modules.support.common.messager.Messager;

public class ChannelGroupCommand {

  @Command(
      name = {"채널", "channel"},
      permission = ChannelPermission.ADMIN,
      helpCommand = true
  )
  public static void channel(CommonCommandSender sender, HelpCommandResult result) {
    Messager.message(sender, Core.getPlugin().getPrefix() + " " + result.getPage() + " / " + result.getMaxPage() + " 페이지");
  }

  @Command(
      parent = "채널",
      name = {"목록", "list"},
      description = "채널 목록을 확인합니다.",
      permission = ChannelPermission.ADMIN
  )
  public static void channelList(CommonCommandSender sender, CommandResult result) {
    sender.nmessage("§e[ 채널 목록 ]");
    for (Channel channel : ChannelModule.getChannels()) {
      if (channel.isOnline()) {

      } else {
        sender.nmessage("§7" + channel.getName() + ": 오프라인");
      }
    }
  }

  @Command(
      parent = "채널",
      name = {"정보", "info"},
      arguments = "<채널>",
      description = "채널 정보를 확인합니다.",
      permission = ChannelPermission.ADMIN
  )
  public static void channelInfo(CommonCommandSender sender, CommandResult result) {

  }

  @Command(
      parent = "채널",
      name = {"이동", "join"},
      arguments = "<채널> (<플레이어 | 현재 | 전체>)",
      description = "채널로 이동하거나 플레이어를 해당 채널로 이동시킵니다.",
      permission = ChannelPermission.ADMIN
  )
  public static void channelJoin(CommonCommandSender sender, CommandResult result) {

  }

  @Command(
      parent = "채널",
      name = {"공지", "broadcast", "bc"},
      arguments = "<채널 | 전체> <메시지>",
      description = "채널에 공지 메시지를 보냅니다.",
      permission = ChannelPermission.ADMIN
  )
  public static void channelBroadcast(CommonCommandSender sender, CommandResult result) {

  }

  @Command(
      parent = "채널",
      name = {"원격명령어", "remoteCommand", "rcmd"},
      arguments = "<채널 | 전체> <명령어>",
      description = "채널의 콘솔에 원격 명령어를 보냅니다.",
      permission = ChannelPermission.ADMIN
  )
  public static void channelRemoteCommand(CommonCommandSender sender, CommandResult result) {

  }


}