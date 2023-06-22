package net.pooleaf.core.modules.commonevent.bukkit.listeners;

import net.pooleaf.core.modules.commonevent.common.events.player.CommonPlayerChatEvent;
import net.pooleaf.core.modules.commonevent.common.events.player.CommonPlayerJoinEvent;
import net.pooleaf.core.modules.commonevent.common.events.player.CommonPlayerLoginEvent;
import net.pooleaf.core.modules.commonevent.common.events.player.CommonPlayerQuitEvent;
import net.pooleaf.core.modules.commonsender.CommonSenderModule;
import net.pooleaf.core.modules.commonsender.common.CommonPlayer;
import net.pooleaf.core.modules.commonevent.CommonEventModule;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.*;

public class BukkitCommonPlayerEventListener implements Listener {

    @EventHandler(priority = EventPriority.LOW)
    public void onLogin(PlayerLoginEvent e) {
        CommonPlayer player = CommonSenderModule.getOnlinePlayer(e.getPlayer().getUniqueId());

        CommonPlayerLoginEvent event = new CommonPlayerLoginEvent(player);
        CommonEventModule.callEvent(event);

        if (event.isDisallow()) {
            e.disallow(PlayerLoginEvent.Result.KICK_OTHER, event.getDisallowMessage());
        }
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        CommonPlayer player = CommonSenderModule.getOnlinePlayer(e.getPlayer().getUniqueId());

        CommonPlayerJoinEvent event = new CommonPlayerJoinEvent(player);
        CommonEventModule.callEvent(event);
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        CommonPlayer player = CommonSenderModule.getOnlinePlayer(e.getPlayer().getUniqueId());

        CommonPlayerQuitEvent event = new CommonPlayerQuitEvent(player);
        CommonEventModule.callEvent(event);
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent e) {
        CommonPlayer player = CommonSenderModule.getOnlinePlayer(e.getPlayer().getUniqueId());

        CommonPlayerChatEvent event = new CommonPlayerChatEvent(player, e.getMessage());
        CommonEventModule.callEvent(event);

        e.setMessage(event.getMessage());
        e.setCancelled(event.isCancelled());
    }

}
