package net.pooleaf.core.modules.option.common.command;

import net.pooleaf.core.modules.annocommand.common.Command;
import net.pooleaf.core.modules.annocommand.common.CommandResult;
import net.pooleaf.core.modules.annocommand.common.HelpCommandResult;
import net.pooleaf.core.modules.commonsender.common.CommonCommandSender;
import net.pooleaf.core.modules.option.OptionModule;
import net.pooleaf.core.modules.option.common.OptionPermission;

import java.util.Map;

public class ServerOptionCommand {

    @Command(
            name = {"서버옵션", "serverOption", "so"},
            permission = OptionPermission.ADMIN,
            helpCommand = true
    )
    public static void serverOption(CommonCommandSender sender, HelpCommandResult result) {
    }

    @Command(
            parent = "서버옵션",
            name = {"설정", "set"},
            arguments = "<옵션이름> <값>",
            description= "서버 옵션을 설정합니다.",
            permission = OptionPermission.ADMIN
    )
    public static void serverOptionSet(CommonCommandSender sender, CommandResult result) {
        String optionName = result.getArgument(0);
        String optionValue = result.subArgument(1);

        OptionModule.getServerOption()
                .set(optionName, optionValue)
                .save();

        sender.message("§e서버 옵션 §f" + optionName + "§e(을)를 §f" + optionValue + "§e로 설정했습니다.");
    }

    @Command(
            parent = "서버옵션",
            name = {"삭제", "delete"},
            arguments = "<옵션이름>",
            description= "서버 옵션을 삭제합니다.",
            permission = OptionPermission.ADMIN
    )
    public static void serverOptionDelete(CommonCommandSender sender, CommandResult result) {
        String optionName = result.getArgument(0);

        OptionModule.getServerOption()
                .delete(optionName)
                .save();

        sender.message("§e서버 옵션 §f" + optionName + "§e를 삭제했습니다.");
    }

    @Command(
            parent = "서버옵션",
            name = {"확인", "check"},
            arguments = "<옵션이름>",
            description= "서버 옵션 값을 확인합니다.",
            permission = OptionPermission.ADMIN
    )
    public static void serverOptionCheck(CommonCommandSender sender, CommandResult result) {
        String optionName = result.getArgument(0);

        String value = OptionModule.getServerOption().getString(optionName);
        if (value == null) {
            sender.warning("존재하지 않는 옵션입니다.");
            return;
        }

        sender.message("§e서버 옵션 §f" + optionName + "§e의 값: §f" + value);
    }

    @Command(
            parent = "서버옵션",
            name = {"목록", "list"},
            description= "서버 옵션을 설정합니다.",
            permission = OptionPermission.ADMIN
    )
    public static void serverOptionList(CommonCommandSender sender, CommandResult result) {
        sender.nmessage("§e[ 서버 옵션 목록 ]");
        for (Map.Entry<String, String> entry : OptionModule.getServerOption().getDatas().entrySet()) {
            sender.nmessage("§e" + entry.getKey() + ": §f" + entry.getValue());
        }
    }

}