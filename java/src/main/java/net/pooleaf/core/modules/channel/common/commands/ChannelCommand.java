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

import java.util.stream.Collectors;

public class ChannelCommand {

  @Command(
          parent = {"", "core"},
          name = {"channel", "채널"},
          description = "채널 명령어 목록을 확인합니다.",
          helpCommand = true,
          permission = ChannelPermission.ADMIN
  )
  public void channel(CommonCommandSender sender, HelpCommandResult result) {
  }

  @Command(
          parent = {"channel", "core channel"},
          name = {"list", "목록"},
          description = "채널 목록을 확인합니다.",
          permission = ChannelPermission.ADMIN
  )
  public void channel_list(CommonCommandSender sender, CommandResult result) {
    sender.sendMessage("§e[ 채널 목록 ]");
    for (Channel channel : ChannelModule.getChannels()) {
      if (channel.isOnline()) {
        sender.sendMessage(new SimpleComponentBuilder("§a" + channel.getName() + " (" + channel.getPlayerCount() + " / " + channel.getMaxPlayerCount() + "): §f"
                + channel.getPlayerNames().stream().collect(Collectors.joining(", ")))
                .hoverShowText("클릭 시 " + channel.getName() + " 채널로 이동합니다.")
                .clickRunCommand("/channel join " + channel.getName())
                .build());
      } else {
        sender.sendMessage("§7" + channel.getName());
      }
    }
  }

  @Command(
          parent = {"channel", "core channel"},
          name = {"info", "정보"},
          arguments = "<채널>",
          description = "채널 정보를 확인합니다.",
          permission = ChannelPermission.ADMIN
  )
  public void channel_info(CommonCommandSender sender, CommandResult result) {
    Channel channel = ChannelModule.getChannel(result.getEnteredArguments());
    if (channel == null) {
      sender.sendWarning("존재하지 않는 채널입니다.");
      return;
    }

    sender.sendMessage("§e[ 채널 정보 ]");
    sender.sendMessage("§e이름: §f" + channel.getName());
    sender.sendMessage("§e이름 표기: §f" + (channel.hasDisplayName() ? channel.getDisplayName() : "없음"));
    sender.sendMessage("§e그룹: §f" + (channel.hasGroup() ? channel.getGroupName() : "없음"));
    sender.sendMessage("§e온라인 :§f" + (channel.isOnline() ? "§a온라인" : "§7오프라인"));
    sender.sendMessage("§e상태 :§f" + ChannelStatus.getMessage(channel.getChannelStatus()) + "(" + channel.getChannelStatus() + ")");
    sender.sendMessage("§e빠른접속 허용 :§f" + channel.isAllowFastJoin());
    sender.sendMessage("§e접속자 수: §f" + channel.getPlayerCount() + " / " + channel.getMaxPlayerCount());
    sender.sendMessage("§e접속자 목록: §f" + channel.getPlayerNames());
    sender.sendMessage("§e데이터: §f" + (channel.getDatas().isEmpty() ? "없음" : ""));
    if (!channel.getDatas().isEmpty()) {
      channel.getDatas().forEach((key, value) -> sender.sendMessage("§eㄴ " + key + ": §f" + value));
    }
  }

  @Command(
          parent = {"channel", "core channel"},
          name = {"setDisplayName", "이름표기설정"},
          arguments = "<채널> <표기>",
          description = "채널의 이름 표기를 설정합니다.",
          permission = ChannelPermission.ADMIN
  )
  public void channel_setDisplayName(CommonCommandSender sender, CommandResult result) {
    Channel channel = ChannelModule.getChannel(result.getArgument(0));
    if (channel == null) {
      sender.sendWarning("존재하지 않는 채널입니다.");
      return;
    }

    String displayName = CommonChatColor.translateAlternateColorCodes('&', result.subArgument(1));
    channel.setDisplayName(displayName);
    sender.sendMessage(channel.getName() + " §e채널의 이름 표기를 §f" + displayName + "§e(으)로 설정했습니다.");
  }

  @Command(
          parent = {"channel", "core channel"},
          name = {"setGroup", "그룹설정"},
          arguments = "<채널> <채널그룹>",
          description = "채널의 그룹을 설정합니다.",
          permission = ChannelPermission.ADMIN
  )
  public void channel_setGroup(CommonCommandSender sender, CommandResult result) {
    Channel channel = ChannelModule.getChannel(result.getArgument(0));
    if (channel == null) {
      sender.sendWarning("존재하지 않는 채널입니다.");
      return;
    }

    ChannelGroup channelGroup = ChannelModule.getChannelGroup(result.getArgument(1));
    if (channelGroup == null) {
      sender.sendWarning("존재하지 않는 채널 그룹입니다.");
      return;
    }

    channel.setGroupName(channelGroup.getName());
    sender.sendMessage(channel.getName() + " §e채널의 그룹을 §f" + channelGroup.getName() + "§e(으)로 설정했습니다.");
  }

  @Command(
          parent = {"channel", "core channel"},
          name = {"join", "send", "이동"},
          arguments = "<채널> (플레이어 | 현재 | 전체)",
          description = "채널로 이동하거나 플레이어를 해당 채널로 이동시킵니다.",
          permission = ChannelPermission.ADMIN
  )
  public void channel_join(CommonCommandSender sender, CommandResult result) {
    // 콘솔이면 반드시 이동시킬 타겟을 입력해야함
    if (sender.isConsole() && result.getArgumentsLength() < 2) {
      result.sendUsage(sender);
      return;
    }

    Channel channel = ChannelModule.getChannel(result.getArgument(0));
    if (channel == null) {
      sender.sendWarning("존재하지 않는 채널입니다.");
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
        if (channel.join(playerName)) {
          joinSuccessCount++;
        }
      }

      sender.sendMessage(joinRequestCount + "§e명의 플레이어 중 §f" + joinSuccessCount + "§e명의 플레이어를 §f" + channel.getName() + " §e채널로 이동시키는데 성공했습니다.");
    }
    // 모든 플레이어 이동시키기
    else if (target.equals("전체") || target.equalsIgnoreCase("all")) {
      int joinRequestCount = ChannelModule.getAllPlayerNames().size();
      int joinSuccessCount = 0;
      for (String playerName : ChannelModule.getAllPlayerNames()) {
        if (channel.join(playerName)) {
          joinSuccessCount++;
        }
      }

      sender.sendMessage(joinRequestCount + "§e명의 플레이어 중 §f" + joinSuccessCount + "§e명의 플레이어를 §f" + channel.getName() + " §e채널로 이동시키는데 성공했습니다.");
    }
    // 내가 채널에 접속하기
    else {
      channel.join(sender.getName());
    }
  }

  @Command(
          parent = {"channel", "core channel"},
          name = {"broadcast", "bc", "공지"},
          arguments = "<채널 | 전체> <메시지>",
          description = "채널에 공지 메시지를 보냅니다.",
          permission = ChannelPermission.ADMIN
  )
  public void channel_broadcast(CommonCommandSender sender, CommandResult result) {
    String message = ChatColor.translateAlternateColorCodes('&', result.subArgument(1));

    // 전체 채널에 공지하기
    if (result.getArgument(0).equals("전체") || result.getArgument(0).equalsIgnoreCase("all")) {
      for (Channel channel : ChannelModule.getChannels()) {
        channel.broadcast(sender.getName(), message);
      }

      sender.sendMessage(ChannelModule.getChannels().size() + "§e개의 채널에 §f'" + message + "' §e메시지를 공지했습니다.");
    }
    // 특정 채널에 공지하기
    else {
      Channel channel = ChannelModule.getChannel(result.getArgument(0));
      if (channel == null) {
        sender.sendWarning("존재하지 않는 채널입니다.");
        return;
      }

      channel.broadcast(sender.getName(), message);
      sender.sendMessage(channel.getName() + " §e채널에 §f'" + message + "' §e메시지를 공지했습니다.");
    }
  }

  @Command(
          parent = {"channel", "core channel"},
          name = {"remoteCommand", "rcmd", "원격명령어"},
          arguments = "<채널 | 전체> <명령어>",
          description = "채널의 콘솔에 원격 명령어를 보냅니다.",
          permission = ChannelPermission.ADMIN
  )
  public void channel_remoteCommand(CommonCommandSender sender, CommandResult result) {
    String commandLine = result.subArgument(1);

    // 전체 채널에 명령어 보내기
    if (result.getArgument(0).equals("전체") || result.getArgument(0).equalsIgnoreCase("all")) {
      for (Channel channel : ChannelModule.getChannels()) {
        channel.remoteCommand(sender.getName(), commandLine);
      }

      sender.sendMessage(ChannelModule.getChannels().size() + "§e개의 채널에 §f'" + commandLine + "' §e명령어를 보냈습니다.");
    }
    // 특정 채널에 명령어 보내기
    else {
      Channel channel = ChannelModule.getChannel(result.getArgument(0));
      if (channel == null) {
        sender.sendWarning("존재하지 않는 채널입니다.");
        return;
      }

      channel.remoteCommand(sender.getName(), commandLine);
      sender.sendMessage(channel.getName() + " §e채널에 §f'" + commandLine + "' §e명령어를 보냈습니다.");
    }
  }

}