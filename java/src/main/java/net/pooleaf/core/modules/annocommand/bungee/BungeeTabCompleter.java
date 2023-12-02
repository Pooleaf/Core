package net.pooleaf.core.modules.annocommand.bungee;

import net.md_5.bungee.api.event.TabCompleteEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import net.pooleaf.core.modules.annocommand.AnnoCommandModule;
import net.pooleaf.core.modules.commonsender.CommonSenderModule;
import net.pooleaf.core.modules.support.common.logger.Logger;

import java.util.List;
import java.util.stream.Collectors;

public class BungeeTabCompleter implements Listener {

    @EventHandler
    public void onTabComplete(TabCompleteEvent e) {
        if (e.getCursor().equals("/")) return;

        String commandLine = e.getCursor().substring(1, e.getCursor().length());

        List<String> suggestions = AnnoCommandModule.getCommandManager().getSuggestions(e.getSender(), commandLine);

        // 추천 명령어가 없을 경우 닉네임을 추천함
        if (suggestions.isEmpty()) {
            String[] commandLineSplit = commandLine.split(" ");
            String lastArg = commandLineSplit[commandLineSplit.length - 1].toLowerCase();

            suggestions = CommonSenderModule.getOnlinePlayers().stream()
                    .filter(commonPlayer -> commonPlayer.getName().toLowerCase().startsWith(lastArg))
                    .map(commonPlayer -> commonPlayer.getName())
                    .collect(Collectors.toList());
        }

        e.getSuggestions().addAll(suggestions);
    }

}
