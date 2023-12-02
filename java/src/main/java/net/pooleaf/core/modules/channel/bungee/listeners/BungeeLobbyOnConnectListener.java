package net.pooleaf.core.modules.channel.bungee.listeners;

import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.event.LoginEvent;
import net.md_5.bungee.api.event.PlayerDisconnectEvent;
import net.md_5.bungee.api.event.ServerConnectEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import net.pooleaf.core.modules.channel.ChannelModule;
import net.pooleaf.core.modules.channel.common.channel.Channel;
import net.pooleaf.core.modules.support.common.logger.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class BungeeLobbyOnConnectListener implements Listener {

    private List<UUID> connectedPlayerUuids = new ArrayList<>();


    @EventHandler
    public void onLogin(LoginEvent event) {
        Channel lobbyChannel = ChannelModule.getLobbyChannelGroup().getFastJoinChannel();
        if (lobbyChannel == null) {
            event.setCancelled(true);
            event.setCancelReason("접속 가능한 로비가 없습니다. 잠시 후에 다시 시도해주세요.");
        }
    }

    @EventHandler
    public void onServerConnect(ServerConnectEvent event) {
        if (!connectedPlayerUuids.contains(event.getPlayer().getUniqueId())) {
            connectedPlayerUuids.add(event.getPlayer().getUniqueId());

            Channel lobbyChannel = ChannelModule.getLobbyChannelGroup().getFastJoinChannel();
            if (lobbyChannel == null) {
                return;
            }

            event.setTarget(ProxyServer.getInstance().getServerInfo(lobbyChannel.getName()));
        }
    }

    @EventHandler
    public void onQuit(PlayerDisconnectEvent event) {
        connectedPlayerUuids.remove(event.getPlayer().getUniqueId());
    }

}
