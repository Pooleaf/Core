package net.pooleaf.core.commands;

import net.pooleaf.core.CorePermission;
import net.pooleaf.core.modules.annocommand.common.Command;
import net.pooleaf.core.modules.annocommand.common.HelpCommandResult;
import net.pooleaf.core.modules.commonsender.common.CommonCommandSender;

public class CoreCommand {

    @Command(
            name = {"core", "c"},
            helpCommand = true,
            permission = CorePermission.ADMIN
    )
    public static void core(CommonCommandSender sender, HelpCommandResult result) {
    }

}