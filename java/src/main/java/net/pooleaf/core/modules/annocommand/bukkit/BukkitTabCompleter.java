package net.pooleaf.core.modules.annocommand.bukkit;

import net.pooleaf.core.modules.annocommand.AnnoCommandModule;
import net.pooleaf.core.modules.commonsender.CommonSenderModule;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.List;
import java.util.stream.Collectors;

public class BukkitTabCompleter implements TabCompleter {

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        String commandLine = label;
        if (args.length > 0) {
            commandLine += " " + String.join(" ", args);
        }

        List<String> suggestions = AnnoCommandModule.getCommandManager().getSuggestions(sender, commandLine);

        // 추천 명령어가 없을 경우 닉네임을 추천함
        if (suggestions.isEmpty()) {
            String[] commandLineSplit = commandLine.split(" ");
            String lastArg = commandLineSplit[commandLineSplit.length - 1].toLowerCase();

            suggestions = CommonSenderModule.getOnlinePlayers().stream()
                    .filter(commonPlayer -> commonPlayer.getName().toLowerCase().startsWith(lastArg))
                    .map(commonPlayer -> commonPlayer.getName())
                    .collect(Collectors.toList());
        }

        if (suggestions.size() > 0) {
            return suggestions;
        }

        return null;
    }

}
