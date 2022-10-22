package net.pooleaf.core.commands;

import net.pooleaf.core.Core;
import net.pooleaf.core.CorePermission;
import net.pooleaf.core.module.CoreModule;
import net.pooleaf.core.modules.annocommand.common.Command;
import net.pooleaf.core.modules.annocommand.common.CommandResult;
import net.pooleaf.core.modules.annocommand.common.HelpCommandResult;
import net.pooleaf.core.modules.commonsender.common.CommonCommandSender;
import net.pooleaf.core.modules.support.common.CommonChatColor;
import net.pooleaf.core.modules.support.common.pageable.PageableCommand;

import java.util.stream.Collectors;

public class ModuleManagerCommand {

    @Command(
            parent = {"", "core"},
            name = {"moduleManager", "모듈관리", "모듈관리자"},
            description = "모듈 관리 명령어 목록을 확인합니다.",
            helpCommand = true,
            permission = CorePermission.ADMIN
    )
    public static void module(CommonCommandSender sender, HelpCommandResult commandResult) {
    }

    @Command(
            parent = {"moduleManager", "core moduleManager"},
            name = {"list", "목록"},
            arguments = "(페이지)",
            description = "모듈 목록을 확인합니다.",
            permission = CorePermission.ADMIN
    )
    public static void module_list(CommonCommandSender sender, CommandResult commandResult) {
        new PageableCommand<CoreModule>(commandResult.getEntered(), Core.getModuleManager().values().stream().collect(Collectors.toList()), 7) {
            @Override
            public CommonChatColor getHeaderColor() {
                return CommonChatColor.YELLOW;
            }

            @Override
            public String getHeaderMessage() {
                return "모듈 목록";
            }

            @Override
            public Object handleValue(CoreModule value, int index) {
                return (value.isEnabled() ? "§a" : "§c") + value.getName();
            }
        }.sendPage(sender, commandResult.getArgumentAsInt(0));
    }

}