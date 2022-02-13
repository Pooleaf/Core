package net.pooleaf.core.modules.annocommand.bukkit;

import net.pooleaf.core.modules.annocommand.AnnoCommandModule;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.List;

public class BukkitTabCompleter implements TabCompleter {

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        String commandLine = label;
        if (args.length > 0) {
            commandLine += " " + String.join(" ", args);
        }

        List<String> suggestions = AnnoCommandModule.getCommandManager().getSuggestions(sender, commandLine);
        if (suggestions.size() > 0) {
            return suggestions;
        }

        return null;
    }

}
