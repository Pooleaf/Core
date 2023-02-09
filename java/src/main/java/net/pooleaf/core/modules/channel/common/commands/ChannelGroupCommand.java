package net.pooleaf.core.modules.channel.common.commands;

import net.pooleaf.core.modules.annocommand.common.Command;
import net.pooleaf.core.modules.annocommand.common.CommandResult;
import net.pooleaf.core.modules.annocommand.common.HelpCommandResult;
import net.pooleaf.core.modules.channel.common.channel.Channel;
import net.pooleaf.core.modules.channel.common.channel.ChannelStatus;
import net.pooleaf.core.modules.channel.common.channelgroup.ChannelGroup;
import net.pooleaf.core.modules.commonsender.common.CommonCommandSender;
import net.pooleaf.core.modules.support.common.CommonChatColor;
import net.pooleaf.core.modules.support.common.component.SimpleComponentBuilder;
import net.pooleaf.core.modules.channel.ChannelModule;
import net.pooleaf.core.modules.channel.common.ChannelPermission;
import org.bukkit.ChatColor;

public class ChannelGroupCommand {

  @Command(
          parent = {"", "core"},
          name = {"channelGroup", "채널그룹"},
          description = "채널 그룹 명령어 목록을 확인합니다.",
          helpCommand = true,
          permission = ChannelPermission.ADMIN
  )
  public void channelGroup(CommonCommandSender sender, HelpCommandResult result) {
  }

  @Command(
          parent = {"channelGroup", "core channelGroup"},
          name = {"목록", "list"},
          description = "채널 그룹 목록을 확인합니다.",
          permission = ChannelPermission.ADMIN
  )
  public void channelGroup_list(CommonCommandSender sender, CommandResult result) {
    sender.sendMessage("§e[ 채널 그룹 목록 ]");
    for (ChannelGroup channelGroup : ChannelModule.getChannelGroups()) {
      sender.sendMessage(new SimpleComponentBuilder(channelGroup.getName() + "§e(" + channelGroup.getDisplayName() + ") (" + channelGroup.getPlayerCount() + ")")
              .hoverShowText("클릭 시 " + channelGroup.getName() + " 채널 그룹으로 이동합니다.")
              .clickRunCommand("/channelGroup join " + channelGroup.getName())
              .build());
    }
  }

  @Command(
          parent = {"channelGroup", "core channelGroup"},
          name = {"정보", "info"},
          arguments = "<채널그룹>",
          description = "채널 정보를 확인합니다.",
          permission = ChannelPermission.ADMIN
  )
  public void channelGroup_info(CommonCommandSender sender, CommandResult result) {
    ChannelGroup channelGroup = ChannelModule.getChannelGroup(result.getEnteredArguments());
    if (channelGroup == null) {
      sender.sendWarning("존재하지 않는 채널 그룹입니다.");
      return;
    }

    sender.sendMessage("§e[ 채널 그룹 정보 ]");
    sender.sendMessage("§e이름: §f" + channelGroup.getName());
    sender.sendMessage("§e이름 표기: §f" + (channelGroup.hasDisplayName() ? channelGroup.getDisplayName() : "없음"));
    sender.sendMessage("§e채널 수: §f" + channelGroup.getChannels().size() + "개 중 " + channelGroup.getOnlineChannels().size() + "개 온라인");
    sender.sendMessage("§e채널 목록: §f" + (channelGroup.getChannels().isEmpty() ? "없음" : ""));
    if (!channelGroup.getChannels().isEmpty()) {
      for (Channel channel : channelGroup.getChannels()) {
        if (channel.isOnline()) {
          sender.sendMessage("§a" + channel.getName() + "(" + ChannelStatus.getMessage(channel.getChannelStatus()) + "): " + channel.getPlayerCount() + " / " + channel.getMaxPlayerCount());
        } else {
          sender.sendMessage("§7" + channel.getName());
        }
      }
    }
    sender.sendMessage("§e접속자 수: §f" + channelGroup.getPlayerCount());
    sender.sendMessage("§e접속자 목록: §f" + channelGroup.getPlayerNames());
    Channel fastJoinTarget = channelGroup.getFastJoinChannel();
    sender.sendMessage("§e빠른접속 타겟: §f" + (fastJoinTarget == null ? "없음" : fastJoinTarget.getName()));
  }

  @Command(
          parent = {"channelGroup", "core channelGroup"},
          name = {"이름표기설정", "setDisplayName"},
          arguments = "<채널그룹> <표기>",
          description = "채널 그룹의 이름 표기를 설정합니다.",
          permission = ChannelPermission.ADMIN
  )
  public void channelGroup_setDisplayName(CommonCommandSender sender, CommandResult result) {
    ChannelGroup channelGroup = ChannelModule.getChannelGroup(result.getEnteredArguments());
    if (channelGroup == null) {
      sender.sendWarning("존재하지 않는 채널 그룹입니다.");
      return;
    }

    String displayName = CommonChatColor.translateAlternateColorCodes('&', result.subArgument(1));
    channelGroup.setDisplayName(displayName);
    sender.sendMessage(channelGroup.getName() + " §e채널의 이름 표기를 §f" + displayName + "§e(으)로 설정했습니다.");
  }

  @Command(
          parent = {"channelGroup", "core channelGroup"},
          name = {"이동", "join", "send"},
          arguments = "<채널그룹> (플레이어 | 현재 | 전체)",
          description = "채널 그룹으로 빠른접속 시키거나 플레이어를 해당 채널로 빠른접속 시킵니다.",
          permission = ChannelPermission.ADMIN
  )
  public void channelGroup_join(CommonCommandSender sender, CommandResult result) {
    // 콘솔이면 반드시 이동시킬 타겟을 입력해야함
    if (sender.isConsole() && result.getArgumentsLength() < 2) {
      result.sendUsage(sender);
      return;
    }

    ChannelGroup channelGroup = ChannelModule.getChannelGroup(result.getArgument(0));
    if (channelGroup == null) {
      sender.sendWarning("존재하지 않는 채널 그룹입니다.");
      return;
    }

    String target = result.getArgument(1);
    // 현재 채널 플레이어 이동시키기
    if (target.equals("현재") || target.equalsIgnoreCase("current")) {
      if (sender.isConsole()) {
        sender.sendWarning("콘솔에서는 현재를 사용할 수 없습니다.");
        return;
      }

      Channel currentChannel = ChannelModule.getChannelHasPlayer(sender.getName());

      int joinRequestCount = currentChannel.getPlayerNames().size();
      int joinSuccessCount = 0;
      for (String playerName : currentChannel.getPlayerNames()) {
        if (channelGroup.fastJoin(playerName) != null) {
          joinSuccessCount++;
        }
      }

      sender.sendMessage(joinRequestCount + "§e명의 플레이어 중 §f" + joinSuccessCount + "§e명의 플레이어를 §f" + channelGroup.getName() + " §e그룹의 채널로 빠른접속 시키는데 성공했습니다.");
    }
    // 모든 플레이어 이동시키기
    else if (target.equals("전체") || target.equalsIgnoreCase("all")) {
      int joinRequestCount = ChannelModule.getAllPlayerNames().size();
      int joinSuccessCount = 0;
      for (String playerName : ChannelModule.getAllPlayerNames()) {
        if (channelGroup.fastJoin(playerName) != null) {
          joinSuccessCount++;
        }
      }

      sender.sendMessage(joinRequestCount + "§e명의 플레이어 중 §f" + joinSuccessCount + "§e명의 플레이어를 §f" + channelGroup.getName() + " §e그룹의 채널로 빠른접속 시키는데 성공했습니다.");
    }
    // 내가 채널에 접속하기
    else {
      channelGroup.fastJoin(sender.getName());
    }
  }

  @Command(
          parent = {"channelGroup", "core channelGroup"},
          name = {"공지", "broadcast", "bc"},
          arguments = "<채널그룹> <메시지>",
          description = "채널 그룹에 공지 메시지를 보냅니다.",
          permission = ChannelPermission.ADMIN
  )
  public void channel_broadcast(CommonCommandSender sender, CommandResult result) {
    String message = ChatColor.translateAlternateColorCodes('&', result.subArgument(1));

    ChannelGroup channelGroup = ChannelModule.getChannelGroup(result.getArgument(0));
    if (channelGroup == null) {
      sender.sendWarning("존재하지 않는 채널 그룹입니다.");
      return;
    }

    channelGroup.broadcast(sender.getName(), message);
    sender.sendMessage(channelGroup.getName() + " §e채널 그룹에 §f'" + message + "' §e메시지를 공지했습니다.");
  }

  @Command(
          parent = {"channelGroup", "core channelGroup"},
          name = {"원격명령어", "remoteCommand", "rcmd"},
          arguments = "<채널그룹> <명령어>",
          description = "채널 그룹의 콘솔에 원격 명령어를 보냅니다.",
          permission = ChannelPermission.ADMIN
  )
  public void channel_remoteCommand(CommonCommandSender sender, CommandResult result) {
    String commandLine = result.subArgument(1);

    ChannelGroup channelGroup = ChannelModule.getChannelGroup(result.getArgument(0));
    if (channelGroup == null) {
      sender.sendWarning("존재하지 않는 채널 그룹입니다.");
      return;
    }

    channelGroup.remoteCommand(sender.getName(), commandLine);
    sender.sendMessage(channelGroup.getName() + " §e채널 그룹에 §f'" + commandLine + "' §e명령어를 보냈습니다.");
  }

}