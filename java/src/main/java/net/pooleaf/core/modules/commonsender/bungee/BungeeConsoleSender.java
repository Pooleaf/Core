package net.pooleaf.core.modules.commonsender.bungee;

import net.pooleaf.core.modules.commonsender.common.CommonConsoleSender;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;

public class BungeeConsoleSender extends CommonConsoleSender<CommandSender> {

    @Override
    public CommandSender getPlatformSender() {
        return ProxyServer.getInstance().getConsole();
    }

}
