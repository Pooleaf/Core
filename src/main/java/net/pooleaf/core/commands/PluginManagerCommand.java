package net.pooleaf.core.commands;

import net.md_5.bungee.api.chat.ClickEvent;
import net.pooleaf.core.Core;
import net.pooleaf.core.CorePermission;
import net.pooleaf.core.modules.annocommand.AnnoCommandModule;
import net.pooleaf.core.modules.annocommand.common.AnnoCommand;
import net.pooleaf.core.modules.annocommand.common.Command;
import net.pooleaf.core.modules.annocommand.common.CommandResult;
import net.pooleaf.core.modules.annocommand.common.HelpCommandResult;
import net.pooleaf.core.modules.commonsender.common.CommonCommandSender;
import net.pooleaf.core.modules.support.common.CommonChatColor;
import net.pooleaf.core.modules.support.common.component.SimpleComponentBuilder;
import net.pooleaf.core.modules.support.common.pageable.PageableCommand;
import net.pooleaf.core.modules.support.common.util.ReflectionUtil;
import net.pooleaf.core.plugin.CorePlugin;

import java.util.List;
import java.util.stream.Collectors;

public class PluginManagerCommand {

    @Command(
            parent = {"", "core"},
            name = {"pluginManager", "pm", "플러그인관리", "플러그인관리자"},
            description = "Core 기반 플러그인 관리 명령어 목록을 확인합니다.",
            helpCommand = true,
            permission = CorePermission.ADMIN
    )
    public void pluginManager(CommonCommandSender sender, HelpCommandResult commandResult) {
    }

    @Command(
            parent = {"pluginManager", "core pluginManager"},
            name = {"list", "목록"},
            arguments = "(페이지)",
            description = "Core 기반 플러그인 목록과 상태를 확인합니다.",
            permission = CorePermission.ADMIN
    )
    public void pluginManager_list(CommonCommandSender sender, CommandResult commandResult) {
        new PageableCommand<CorePlugin>(commandResult.getEntered(), Core.getPluginManager().values().stream().collect(Collectors.toList()), 18) {
            @Override
            public CommonChatColor getHeaderColor() {
                return CommonChatColor.YELLOW;
            }

            @Override
            public String getHeaderMessage() {
                return "플러그인 목록";
            }

            @Override
            public Object handleValue(CorePlugin value, int index) {
                return (value.isEnabled() ? "§a" : "§c") + value.getName() + ": §ev§f" + value.getVersion() + "";
            }
        }.sendPage(sender, commandResult.getArgumentAsInt(0));
    }

    @Command(
            parent = {"pluginManager", "core pluginManager"},
            name = {"reloadConfig", "설정리로드"},
            arguments = "<플러그인>",
            description = "Core 기반 플러그인의 설정을 다시 불러옵니다.",
            permission = CorePermission.ADMIN
    )
    public void pluginManager_reloadConfig(CommonCommandSender sender, CommandResult commandResult) {
        CorePlugin plugin = Core.getPluginManager().getPluginByName(commandResult.getArgument(0));
        if (plugin == null) {
            sender.nwarning("존재하지 않는 플러그인입니다.");
            return;
        }

        // 메소드 구현 여부 확인
        if (ReflectionUtil.getMethod(plugin.getClass(), "onConfigLoaded") == null) {
            sender.nwarning("설정 리로드를 지원하지 않는 플러그인입니다.");
            return;
        }

        // 설정 불러오기. 만약 설정 리로드를 고려하지 않고 플러그인을 만들었을 경우 설정이 꼬일 수 있음
        plugin.loadConfig(sender);
    }

    @Command(
            parent = {"pluginManager", "core pluginManager"},
            name = {"commandList", "cmdList", "명령어목록"},
            arguments = "<플러그인> (페이지)",
            description = "Core 기반 플러그인의 명령어 목록을 확인합니다.",
            permission = CorePermission.ADMIN
    )
    public void pluginManager_commandList(CommonCommandSender sender, CommandResult commandResult) {
        CorePlugin plugin = Core.getPluginManager().getPluginByName(commandResult.getArgument(0));
        if (plugin == null) {
            sender.nwarning("존재하지 않는 플러그인입니다.");
            return;
        }

        // 플러그인에 맞는 명령어 불러오기
        List<AnnoCommand> commands = AnnoCommandModule.getCommandManager().getCommands(plugin);

        new PageableCommand<AnnoCommand>(commandResult.getEntered() + " " + plugin.getName(), commands, 10) {
            @Override
            public CommonChatColor getHeaderColor() {
                return CommonChatColor.YELLOW;
            }

            @Override
            public String getHeaderMessage() {
                return plugin.getName() + " 명령어 목록";
            }

            @Override
            public Object handleValue(AnnoCommand value, int index) {
                SimpleComponentBuilder builder = new SimpleComponentBuilder();
                String text = "/";

                // 부모 명령어 추가
                if (value.hasParent()) {
                    text += value.getParent() + " ";
                }

                // 명령어 추가
                text += value.getName().get(0);
                // Alias가 있을 경우
                if (value.getName().size() > 1) {
                    StringBuilder aliasesBuilder = new StringBuilder();
                    for (int i = 1; i < value.getName().size(); i++) {
                        aliasesBuilder.append(aliasesBuilder.length() < 1 ? value.getName().get(i) : ", " + value.getName().get(i));
                    }

                    text += "(" + aliasesBuilder.toString() + ")";
                }

                // 펄미션 추가
                if (value.hasPermission()) {
                    text += " §e[" + value.getPermission() + "]";
                }

                builder.text(text);
                builder.hoverShowText("클릭 시 명령어가 입력됩니다.");
                builder.clickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/" + value.getCommandLine());

                return builder.build();
            }
        }.sendPage(sender, commandResult.getArgumentAsInt(1));
    }

}