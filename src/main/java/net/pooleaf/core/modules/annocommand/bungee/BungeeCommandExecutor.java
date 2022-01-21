package net.pooleaf.core.modules.annocommand.bungee;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.plugin.Command;
import net.pooleaf.core.modules.annocommand.AnnoCommandModule;
import net.pooleaf.core.modules.annocommand.CommandManager;

public class BungeeCommandExecutor extends Command {

    public BungeeCommandExecutor(String name, String permission, String... aliases) {
        super(name, permission, aliases);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        String commandLine = getName();
        if (args.length > 0) {
            commandLine += " " + String.join(" ", args);
        }

        AnnoCommandModule.getCommandManager().executeCommand(sender, commandLine);
    }

}
