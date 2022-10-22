package net.pooleaf.core.modules.commonsender.common.commands;

import net.pooleaf.core.CorePermission;
import net.pooleaf.core.modules.annocommand.common.Command;
import net.pooleaf.core.modules.annocommand.common.CommandResult;
import net.pooleaf.core.modules.commonsender.CommonSenderModule;
import net.pooleaf.core.modules.commonsender.common.CommonCommandSender;
import net.pooleaf.core.modules.commonsender.common.CommonPlayer;
import net.pooleaf.core.modules.support.common.CommonChatColor;

public class DisplayNameCommand {

    @Command(
            parent = {"", "core"},
            name = {"displayName", "nick", "닉네임"},
            arguments = "<플레이어> <닉네임>",
            description = "플레이어의 표기되는 닉네임을 변경합니다.",
            permission = CorePermission.ADMIN
    )
    public static void displayName(CommonCommandSender sender, CommandResult result) {
        String name = result.getArgument(0);
        String displayName = CommonChatColor.translateAlternateColorCodes('&', result.subArgument(1));

        CommonPlayer targetPlayer = CommonSenderModule.getPlayerByName(name);
        if (targetPlayer == null) {
            sender.nwarning("존재하지 않는 플레이어입니다.");
            return;
        }

        if (name.equalsIgnoreCase(displayName)) { // 원래 닉네임과 같게 입력할 경우 삭제
            targetPlayer.setDisplayName(null);
            displayName = name;
        } else { // 닉네임 설정
            targetPlayer.setDisplayName(displayName);
        }
        CommonSenderModule.getSqlManager().commonPlayer().insertPlayerInfo(targetPlayer);

        sender.nmessage(targetPlayer.getName() + " §e님의 닉네임을 §f" + displayName + "§e(으)로 설정했습니다.");
    }

}
