package net.pooleaf.core.modules.commonsender.bukkit;

import net.pooleaf.core.modules.commonsender.common.CommonConsoleSender;
import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;

public class BukkitConsoleSender extends CommonConsoleSender<ConsoleCommandSender> {

    @Override
    public ConsoleCommandSender getPlatformSender() {
        return Bukkit.getConsoleSender();
    }

}
