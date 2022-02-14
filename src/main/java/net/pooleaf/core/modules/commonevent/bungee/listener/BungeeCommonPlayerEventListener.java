package net.pooleaf.core.modules.commonevent.bungee.listener;

import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ChatEvent;
import net.md_5.bungee.api.event.LoginEvent;
import net.md_5.bungee.api.event.ServerConnectedEvent;
import net.md_5.bungee.api.event.ServerDisconnectEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import net.pooleaf.core.modules.commonevent.CommonEventModule;
import net.pooleaf.core.modules.commonevent.common.events.player.CommonPlayerChatEvent;
import net.pooleaf.core.modules.commonevent.common.events.player.CommonPlayerJoinEvent;
import net.pooleaf.core.modules.commonevent.common.events.player.CommonPlayerLoginEvent;
import net.pooleaf.core.modules.commonevent.common.events.player.CommonPlayerQuitEvent;
import net.pooleaf.core.modules.commonplayer.CommonPlayerModule;
import net.pooleaf.core.modules.commonplayer.common.CommonPlayer;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class BungeeCommonPlayerEventListener implements Listener {

    private List<UUID> joined = new ArrayList<>();


    @EventHandler
    public void onLogin(LoginEvent e) {
        CommonPlayer player = CommonPlayerModule.getPlayer(e.getConnection().getUniqueId());

        CommonPlayerLoginEvent event = new CommonPlayerLoginEvent(player);
        CommonEventModule.callEvent(event);

        if (event.isDisallow()) {
            e.setCancelled(true);
            e.setCancelReason(event.getDisallowMessage());
        }
    }

    @EventHandler
    public void onJoin(ServerConnectedEvent e) {
        if (joined.contains(e.getPlayer().getUniqueId())) {
            return;
        }

        CommonPlayer player = CommonPlayerModule.getPlayer(e.getPlayer().getUniqueId());

        CommonPlayerJoinEvent event = new CommonPlayerJoinEvent(player);
        CommonEventModule.callEvent(event);

        joined.add(e.getPlayer().getUniqueId());
    }

    @EventHandler
    public void onQuit(ServerDisconnectEvent e) {
        CommonPlayer player = CommonPlayerModule.getPlayer(e.getPlayer().getUniqueId());

        CommonPlayerQuitEvent event = new CommonPlayerQuitEvent(player);
        CommonEventModule.callEvent(event);
    }


    @EventHandler
    public void onChat(ChatEvent e) {
        if (e.isCommand()) {
            return;
        }

        CommonPlayer player = CommonPlayerModule.getPlayer(((ProxiedPlayer) e.getSender()).getUniqueId());

        CommonPlayerChatEvent event = new CommonPlayerChatEvent(player, e.getMessage());
        CommonEventModule.callEvent(event);

        e.setMessage(event.getMessage());
        e.setCancelled(event.isCancelled());
    }

}
