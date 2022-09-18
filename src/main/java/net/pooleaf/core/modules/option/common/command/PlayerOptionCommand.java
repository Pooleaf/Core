package net.pooleaf.core.modules.option.common.command;

import net.pooleaf.core.modules.annocommand.common.Command;
import net.pooleaf.core.modules.annocommand.common.CommandResult;
import net.pooleaf.core.modules.annocommand.common.HelpCommandResult;
import net.pooleaf.core.modules.commonsender.CommonSenderModule;
import net.pooleaf.core.modules.commonsender.common.CommonCommandSender;
import net.pooleaf.core.modules.option.OptionModule;
import net.pooleaf.core.modules.option.common.Option;
import net.pooleaf.core.modules.option.common.OptionPermission;

import java.util.Map;

public class PlayerOptionCommand {

    @Command(
            name = {"플레이어옵션", "playerOption", "po"},
            permission = OptionPermission.ADMIN,
            helpCommand = true
    )
    public static void playerOption(CommonCommandSender sender, HelpCommandResult result) {
    }

    @Command(
            parent = "플레이어옵션",
            name = {"설정", "set"},
            arguments = "<플레이어> <옵션이름> <값>",
            description= "플레이어의 옵션을 설정합니다.",
            permission = OptionPermission.ADMIN
    )
    public static void playerOptionSet(CommonCommandSender sender, CommandResult result) {
        String playerName = result.getArgument(0);
        String optionName = result.getArgument(1);
        String optionValue = result.subArgument(2);

        if (!CommonSenderModule.existsPlayerByName(playerName)) {
            sender.warning("존재하지 않는 플레이어입니다.");
            return;
        }

        OptionModule.getPlayerOptionByName(playerName)
                .set(optionName, optionValue)
                .save();

        sender.message("§f" + playerName + " §e플레이어의 옵션 §f" + optionName + "§e를 §f" + optionValue + "§e로 설정했습니다.");
    }

    @Command(
            parent = "플레이어옵션",
            name = {"삭제", "delete"},
            arguments = "<플레이어> <옵션이름>",
            description= "플레이어의 옵션을 삭제합니다.",
            permission = OptionPermission.ADMIN
    )
    public static void playerOptionDelete(CommonCommandSender sender, CommandResult result) {
        String playerName = result.getArgument(0);
        String optionName = result.getArgument(1);

        if (!CommonSenderModule.existsPlayerByName(playerName)) {
            sender.warning("존재하지 않는 플레이어입니다.");
            return;
        }

        OptionModule.getPlayerOptionByName(playerName)
                .delete(optionName)
                .save();

        sender.message("§f" + playerName + " §e플레이어의 옵션 §f" + optionName + "§e를 삭제했습니다.");
    }

    @Command(
            parent = "플레이어옵션",
            name = {"확인", "check"},
            arguments = "<플레이어> <옵션이름>",
            description= "플레이어의 옵션 값을 확인합니다.",
            permission = OptionPermission.ADMIN
    )
    public static void playerOptionCheck(CommonCommandSender sender, CommandResult result) {
        String playerName = result.getArgument(0);
        String optionName = result.getArgument(1);

        if (!CommonSenderModule.existsPlayerByName(playerName)) {
            sender.warning("존재하지 않는 플레이어입니다.");
            return;
        }

        String value = OptionModule.getPlayerOptionByName(playerName).getString(optionName);
        if (value == null) {
            sender.warning("존재하지 않는 옵션입니다.");
            return;
        }

        sender.message("§f" + playerName + " §e플레이어의 옵션 §f" + optionName + "§e의 값: §f" + value);
    }

    @Command(
            parent = "플레이어옵션",
            name = {"목록", "list"},
            arguments = "<플레이어>",
            description= "플레이어의 옵션을 설정합니다.",
            permission = OptionPermission.ADMIN
    )
    public static void playerOptionList(CommonCommandSender sender, CommandResult result) {
        String playerName = result.getArgument(0);
        if (!CommonSenderModule.existsPlayerByName(playerName)) {
            sender.warning("존재하지 않는 플레이어입니다.");
            return;
        }

        Option playerOption = OptionModule.getPlayerOptionByName(playerName);

        sender.nmessage("§e[ §f" + playerName + " §e플레이어 옵션 목록 ]");
        for (Map.Entry<String, String> entry : playerOption.getDatas().entrySet()) {
            sender.nmessage("§e" + entry.getKey() + ": §f" + entry.getValue());
        }
    }

}