package net.pooleaf.core.modules.annocommand.bungee;

import net.md_5.bungee.api.event.TabCompleteEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import net.pooleaf.core.modules.annocommand.AnnoCommandModule;

import java.util.List;

public class BungeeTabCompleter implements Listener {

    @EventHandler
    public void onTabComplete(TabCompleteEvent e) {
        if (e.getCursor().equals("/")) return;

        String commandLine = e.getCursor().substring(1, e.getCursor().length());

        List<String> suggestions = AnnoCommandModule.getCommandManager().getSuggestions(e.getSender(), commandLine);
        e.getSuggestions().addAll(suggestions);
    }

}
