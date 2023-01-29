package net.pooleaf.core.commands;

import net.pooleaf.core.modules.annocommand.common.HelpCommandResult;
import net.pooleaf.core.modules.commonsender.common.CommonCommandSender;
import net.pooleaf.core.CorePermission;
import net.pooleaf.core.modules.annocommand.common.Command;

public class CoreCommand {

    @Command(
            name = {"core", "c"},
            helpCommand = true,
            permission = CorePermission.ADMIN
    )
    public void core(CommonCommandSender sender, HelpCommandResult result) {
    }

}