package net.pooleaf.core.modules.option.common.commands;

import net.pooleaf.core.modules.annocommand.common.CommandResult;
import net.pooleaf.core.modules.annocommand.common.HelpCommandResult;
import net.pooleaf.core.modules.commonsender.common.CommonCommandSender;
import net.pooleaf.core.CorePermission;
import net.pooleaf.core.modules.annocommand.common.Command;
import net.pooleaf.core.modules.option.OptionModule;

import java.util.Map;

public class ServerOptionCommand {

    @Command(
            parent = {"", "core"},
            name = {"serverOption", "so", "서버옵션"},
            description = "서버 옵션 명령어 목록을 확인합니다.",
            helpCommand = true,
            permission = CorePermission.ADMIN
    )
    public void serverOption(CommonCommandSender sender, HelpCommandResult result) {
    }

    @Command(
            parent = {"serverOption", "core serverOption"},
            name = {"set", "설정"},
            arguments = "<옵션이름> <값>",
            description= "서버 옵션을 설정합니다.",
            permission = CorePermission.ADMIN
    )
    public void serverOption_set(CommonCommandSender sender, CommandResult result) {
        String optionName = result.getArgument(0);
        String optionValue = result.subArgument(1);

        OptionModule.getServerOption()
                .set(optionName, optionValue)
                .save();

        sender.nmessage("§e서버 옵션 §f" + optionName + "§e(을)를 §f" + optionValue + "§e로 설정했습니다.");
    }

    @Command(
            parent = {"serverOption", "core serverOption"},
            name = {"delete", "삭제"},
            arguments = "<옵션이름>",
            description= "서버 옵션을 삭제합니다.",
            permission = CorePermission.ADMIN
    )
    public void serverOption_delete(CommonCommandSender sender, CommandResult result) {
        String optionName = result.getArgument(0);

        OptionModule.getServerOption()
                .delete(optionName)
                .save();

        sender.nmessage("§e서버 옵션 §f" + optionName + "§e를 삭제했습니다.");
    }

    @Command(
            parent = {"serverOption", "core serverOption"},
            name = {"check", "확인"},
            arguments = "<옵션이름>",
            description= "서버 옵션 값을 확인합니다.",
            permission = CorePermission.ADMIN
    )
    public void serverOption_check(CommonCommandSender sender, CommandResult result) {
        String optionName = result.getArgument(0);

        String value = OptionModule.getServerOption().getString(optionName);
        if (value == null) {
            sender.nwarning("존재하지 않는 옵션입니다.");
            return;
        }

        sender.nmessage("§e서버 옵션 §f" + optionName + "§e의 값: §f" + value);
    }

    @Command(
            parent = {"serverOption", "core serverOption"},
            name = {"list", "목록"},
            description= "서버 옵션을 설정합니다.",
            permission = CorePermission.ADMIN
    )
    public void serverOption_list(CommonCommandSender sender, CommandResult result) {
        sender.nmessage("§e[ 서버 옵션 목록 ]");
        for (Map.Entry<String, String> entry : OptionModule.getServerOption().getDatas().entrySet()) {
            sender.nmessage("§e" + entry.getKey() + ": §f" + entry.getValue());
        }
    }

}