package net.pooleaf.core.modules.option.common.commands;

import net.pooleaf.core.modules.commonsender.CommonSenderModule;
import net.pooleaf.core.modules.commonsender.common.CommonCommandSender;
import net.pooleaf.core.CorePermission;
import net.pooleaf.core.modules.annocommand.common.Command;
import net.pooleaf.core.modules.annocommand.common.CommandResult;
import net.pooleaf.core.modules.annocommand.common.HelpCommandResult;
import net.pooleaf.core.modules.option.OptionModule;
import net.pooleaf.core.modules.option.common.Option;

import java.util.Map;

public class PlayerOptionCommand {

    @Command(
            parent = {"", "core"},
            name = {"playerOption", "po", "플레이어옵션"},
            description = "플레이어 옵션 명령어 목록을 확인합니다.",
            helpCommand = true,
            permission = CorePermission.ADMIN
    )
    public void playerOption(CommonCommandSender sender, HelpCommandResult result) {
    }

    @Command(
            parent = {"playerOption", "core playerOption"},
            name = {"set", "설정"},
            arguments = "<플레이어> <옵션이름> <값>",
            description= "플레이어의 옵션을 설정합니다.",
            permission = CorePermission.ADMIN
    )
    public void playerOption_set(CommonCommandSender sender, CommandResult result) {
        String playerName = result.getArgument(0);
        String optionName = result.getArgument(1);
        String optionValue = result.subArgument(2);

        if (!CommonSenderModule.existsPlayerByName(playerName)) {
            sender.sendWarning("존재하지 않는 플레이어입니다.");
            return;
        }

        OptionModule.getPlayerOptionByName(playerName)
                .set(optionName, optionValue)
                .save();

        sender.sendMessage("§f" + playerName + " §e플레이어의 옵션 §f" + optionName + "§e를 §f" + optionValue + "§e로 설정했습니다.");
    }

    @Command(
            parent = {"playerOption", "core playerOption"},
            name = {"delete", "삭제"},
            arguments = "<플레이어> <옵션이름>",
            description= "플레이어의 옵션을 삭제합니다.",
            permission = CorePermission.ADMIN
    )
    public void playerOption_delete(CommonCommandSender sender, CommandResult result) {
        String playerName = result.getArgument(0);
        String optionName = result.getArgument(1);

        if (!CommonSenderModule.existsPlayerByName(playerName)) {
            sender.sendWarning("존재하지 않는 플레이어입니다.");
            return;
        }

        OptionModule.getPlayerOptionByName(playerName)
                .delete(optionName)
                .save();

        sender.sendMessage("§f" + playerName + " §e플레이어의 옵션 §f" + optionName + "§e를 삭제했습니다.");
    }

    @Command(
            parent = {"playerOption", "core playerOption"},
            name = {"check", "확인"},
            arguments = "<플레이어> <옵션이름>",
            description= "플레이어의 옵션 값을 확인합니다.",
            permission = CorePermission.ADMIN
    )
    public void playerOption_check(CommonCommandSender sender, CommandResult result) {
        String playerName = result.getArgument(0);
        String optionName = result.getArgument(1);

        if (!CommonSenderModule.existsPlayerByName(playerName)) {
            sender.sendWarning("존재하지 않는 플레이어입니다.");
            return;
        }

        String value = OptionModule.getPlayerOptionByName(playerName).getString(optionName);
        if (value == null) {
            sender.sendWarning("존재하지 않는 옵션입니다.");
            return;
        }

        sender.sendMessage("§f" + playerName + " §e플레이어의 옵션 §f" + optionName + "§e의 값: §f" + value);
    }

    @Command(
            parent = {"playerOption", "core playerOption"},
            name = {"list", "목록"},
            arguments = "<플레이어>",
            description= "플레이어의 옵션을 설정합니다.",
            permission = CorePermission.ADMIN
    )
    public void playerOption_list(CommonCommandSender sender, CommandResult result) {
        String playerName = result.getArgument(0);
        if (!CommonSenderModule.existsPlayerByName(playerName)) {
            sender.sendWarning("존재하지 않는 플레이어입니다.");
            return;
        }

        Option playerOption = OptionModule.getPlayerOptionByName(playerName);

        sender.sendMessage("§e[ §f" + playerName + " §e플레이어 옵션 목록 ]");
        for (Map.Entry<String, String> entry : playerOption.getDatas().entrySet()) {
            sender.sendMessage("§e" + entry.getKey() + ": §f" + entry.getValue());
        }
    }

}