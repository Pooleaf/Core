package net.pooleaf.core.modules.commonsender.common.commands;

import net.pooleaf.core.modules.commonsender.common.CommonPlayer;
import net.pooleaf.core.CorePermission;
import net.pooleaf.core.modules.annocommand.common.Command;
import net.pooleaf.core.modules.annocommand.common.CommandResult;
import net.pooleaf.core.modules.commonsender.CommonSenderModule;
import net.pooleaf.core.modules.commonsender.common.CommonCommandSender;

import java.time.format.DateTimeFormatter;

public class PlayerInfoCommand {

    @Command(
            parent = {"", "core"},
            name = {"playerInfo", "pi", "플레이어정보"},
            arguments = "<닉네임>",
            description = "플레이어 정보를 확인합니다.",
            permission = CorePermission.ADMIN
    )
    public void playerInfo(CommonCommandSender sender, CommandResult result) {
        CommonPlayer targetPlayer = CommonSenderModule.getOfflinePlayerByName(result.getArgument(0));
        if (targetPlayer == null) {
            sender.sendWarning("존재하지 않는 플레이어입니다.");
            return;
        }

        sender.sendMessage("§e[ §f" + targetPlayer.getName() + " §e정보 ]");
        sender.sendMessage("§eUUID §f:" + targetPlayer.getUuid());
        sender.sendMessage("§e닉네임: §f" + targetPlayer.getName());
        sender.sendMessage("§e닉네임 표기: §f" + (targetPlayer.hasDisplayName() ? targetPlayer.getDisplayName() : "없음"));
        sender.sendMessage("§eIP: §f" + targetPlayer.getIp());
        sender.sendMessage("§e마지막 로그인: §f" + targetPlayer.getLastLogin().format(DateTimeFormatter.ofPattern("yyyy년 MM월 dd일 a hh시 mm분 ss초")));
    }

}
