package net.pooleaf.core.commands;

import net.pooleaf.core.CorePermission;
import net.pooleaf.core.modules.annocommand.common.Command;
import net.pooleaf.core.modules.annocommand.common.CommandResult;
import net.pooleaf.core.modules.commonsender.common.CommonCommandSender;
import net.pooleaf.core.modules.support.common.debugger.Debugger;

public class DebugCommand {

    @Command(
            parent = {"core"},
            name = {"debug", "debugMode", "디버그"},
            description = "디버그모드로 전환하거나 해제합니다.",
            permission = CorePermission.ADMIN
    )
    public void debug(CommonCommandSender sender, CommandResult commandResult) {
        if (Debugger.isListening(sender)) {
            Debugger.removeListener(sender);
            sender.sendMessage("§c디버그 모드가 해제되었습니다.");
        } else {
            Debugger.addListener(sender);
            sender.sendMessage("§a디버그 모드로 전환했습니다.");
        }
    }

}