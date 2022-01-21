package net.pooleaf.core.modules.support.bukkit.messager;

import com.google.common.base.Preconditions;
import net.pooleaf.core.modules.support.common.messager.MessagerAdapter;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class BukkitMessagerAdapter implements MessagerAdapter {

    @Override
    public void message(Object sender, Object message) {
        Preconditions.checkArgument(sender instanceof CommandSender, "sender가 CommandSender가 아닙니다.");

        // 플레이어가 접속 중이 아닐 경우 무시
        if (sender instanceof Player && !((Player) sender).isOnline()) return;

        ((CommandSender) sender).sendMessage((String) message);
    }

    @Override
    public void broadcast(Object message) {
        Bukkit.broadcastMessage((String) message);
    }

}
