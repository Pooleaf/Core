package net.pooleaf.core.commands;

import net.pooleaf.core.CorePermission;
import net.pooleaf.core.modules.annocommand.common.Command;
import net.pooleaf.core.modules.annocommand.common.CommandResult;
import net.pooleaf.core.modules.gui.GuiModule;
import net.pooleaf.core.modules.support.common.messager.Messager;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class QuickBarCommand {

    @Command(
            parent = {"", "core"},
            name = {"removeQuickBar", "rqb", "퀵바제거"},
            arguments = "(플레이어)",
            description = "퀵바를 강제로 제거합니다.",
            permission = CorePermission.ADMIN
    )
    public void core_removeQuickBar(CommandSender sender, CommandResult result) {
        // 콘솔에서는 플레이어를 입력해야 사용 가능
        if (result.getArgumentsLength() < 1 && !(sender instanceof Player)) {
            result.sendUsage(sender);
            return;
        }

        // 타겟 계산
        Player target = null;
        if (result.getArgumentsLength() < 1) {
            target = (Player) sender;
        } else {
            String playerName = result.getArgument(0);
            target = Bukkit.getPlayer(playerName);
        }

        if (target == null) {
            Messager.sendWarning(sender, "접속 중이 아닌 플레이어입니다.");
            return;
        }

        // 퀵바 제거
        boolean success = GuiModule.getQuickBarManager().removeTo(target);
        if (success) {
            Messager.sendMessage(sender, target.getName() + " §e님의 퀵바를 제거했습니다.");
        } else {
            Messager.sendWarning(sender, target.getName() + " 님은 이미 퀵바를 사용하고 있지 않습니다.");
        }
    }

}
