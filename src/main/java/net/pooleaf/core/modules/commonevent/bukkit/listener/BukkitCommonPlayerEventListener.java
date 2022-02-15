package net.pooleaf.core.modules.commonevent.bukkit.listener;

import net.pooleaf.core.modules.commonevent.CommonEventModule;
import net.pooleaf.core.modules.commonevent.common.events.player.CommonPlayerChatEvent;
import net.pooleaf.core.modules.commonevent.common.events.player.CommonPlayerJoinEvent;
import net.pooleaf.core.modules.commonevent.common.events.player.CommonPlayerLoginEvent;
import net.pooleaf.core.modules.commonevent.common.events.player.CommonPlayerQuitEvent;
import net.pooleaf.core.modules.commonsender.CommonSenderModule;
import net.pooleaf.core.modules.commonsender.common.CommonPlayer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class BukkitCommonPlayerEventListener implements Listener {

    @EventHandler
    public void onLogin(AsyncPlayerPreLoginEvent e) {
        CommonPlayer player = CommonSenderModule.getPlayer(e.getUniqueId());

        CommonPlayerLoginEvent event = new CommonPlayerLoginEvent(player);
        CommonEventModule.callEvent(event);

        if (event.isDisallow()) {
            e.disallow(AsyncPlayerPreLoginEvent.Result.KICK_OTHER, event.getDisallowMessage());
        }
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        CommonPlayer player = CommonSenderModule.getPlayer(e.getPlayer().getUniqueId());

        CommonPlayerJoinEvent event = new CommonPlayerJoinEvent(player);
        CommonEventModule.callEvent(event);
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        CommonPlayer player = CommonSenderModule.getPlayer(e.getPlayer().getUniqueId());

        CommonPlayerQuitEvent event = new CommonPlayerQuitEvent(player);
        CommonEventModule.callEvent(event);
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent e) {
        CommonPlayer player = CommonSenderModule.getPlayer(e.getPlayer().getUniqueId());

        CommonPlayerChatEvent event = new CommonPlayerChatEvent(player, e.getMessage());
        CommonEventModule.callEvent(event);

        e.setMessage(event.getMessage());
        e.setCancelled(event.isCancelled());
    }

}
