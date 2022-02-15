package net.pooleaf.core.modules.commonsender.common.command;

import net.pooleaf.core.modules.annocommand.common.Command;
import net.pooleaf.core.modules.annocommand.common.CommandResult;
import net.pooleaf.core.modules.commonsender.CommonSenderModule;
import net.pooleaf.core.modules.commonsender.common.CommonCommandSender;
import net.pooleaf.core.modules.commonsender.common.CommonPlayer;

import java.time.format.DateTimeFormatter;

public class PlayerInfoCommand {

    @Command(
            name = {"플레이어정보", "playerInfo", "pi"},
            arguments = "<닉네임>",
            permission = "core.admin"
    )
    public static void playerInfo(CommonCommandSender sender, CommandResult result) {
        CommonPlayer targetPlayer = CommonSenderModule.getPlayerByName(result.getArgument(0));
        if (targetPlayer == null) {
            targetPlayer.warning("존재하지 않는 플레이어입니다.");
            return;
        }

        sender.message("§e[ §f" + targetPlayer.getName() + " §e정보 ]");
        sender.message("§eUUID §f:" + targetPlayer.getUuid());
        sender.message("§e닉네임: §f" + targetPlayer.getName());
        sender.message("§e닉네임 표기: §f" + (targetPlayer.hasDisplayName() ? targetPlayer.getDisplayName() : "없음"));
        sender.message("§eIP: §f" + targetPlayer.getIp());
        sender.message("§e마지막 로그인: §f" + targetPlayer.getLastLogin().format(DateTimeFormatter.ofPattern("yyyy년 MM월 dd일 a hh시 mm분 ss초")));
    }

}
