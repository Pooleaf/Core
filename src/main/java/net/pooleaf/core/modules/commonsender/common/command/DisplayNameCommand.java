package net.pooleaf.core.modules.commonsender.common.command;

import net.pooleaf.core.modules.annocommand.common.Command;
import net.pooleaf.core.modules.annocommand.common.CommandResult;
import net.pooleaf.core.modules.commonsender.CommonSenderModule;
import net.pooleaf.core.modules.commonsender.common.CommonCommandSender;
import net.pooleaf.core.modules.commonsender.common.CommonPlayer;

public class DisplayNameCommand {

    @Command(
            name = {"닉네임", "displayName", "nick"},
            arguments = "<플레이어> <닉네임>",
            description = "플레이어의 표기되는 닉네임을 변경합니다.",
            permission = "core.admin"
    )
    public static void displayName(CommonCommandSender sender, CommandResult result) {
        String name = result.getArgument(0);
        String displayName = result.subArgument(1);

        CommonPlayer targetPlayer = CommonSenderModule.getPlayerByName(name);
        if (targetPlayer == null) {

        }

        if (name.equalsIgnoreCase(displayName)) { // 원래 닉네임과 같게 입력할 경우 삭제
            targetPlayer.setDisplayName(null);
            displayName = name;
        } else { // 닉네임 설정
            targetPlayer.setDisplayName(displayName);
        }
        CommonSenderModule.getPlayerInfoDao().insertPlayerInfo(targetPlayer);

        sender.message(targetPlayer.getName() + " 님의 닉네임을 " + displayName + " (으)로 설정했습니다.");
    }

}
