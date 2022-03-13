package net.pooleaf.core.modules.support.bungee.messager;

import com.google.common.base.Preconditions;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.pooleaf.core.modules.support.common.messager.MessagerAdapter;

public class BungeeMessagerAdapter implements MessagerAdapter {

    @Override
    public void message(Object sender, Object message) {
        Preconditions.checkArgument(sender instanceof CommandSender, "sender가 CommandSender가 아닙니다.");

        // 플레이어가 접속 중이 아닐 경우 무시
        if (sender instanceof ProxiedPlayer && !((ProxiedPlayer) sender).isConnected()) return;

        ((CommandSender) sender).sendMessage((String) message);
    }

    @Override
    public void broadcast(Object message) {
        ProxyServer.getInstance().broadcast((String) message);
    }

}
