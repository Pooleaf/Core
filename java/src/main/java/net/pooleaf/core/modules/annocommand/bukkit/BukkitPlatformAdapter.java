package net.pooleaf.core.modules.annocommand.bukkit;

import java.util.ArrayList;
import java.util.List;

import net.pooleaf.core.modules.annocommand.common.AnnoCommand;
import net.pooleaf.core.modules.annocommand.common.PlatformAdapter;
import net.pooleaf.core.modules.commonsender.common.CommonCommandSender;
import net.pooleaf.core.modules.commonsender.common.CommonPlayer;
import net.md_5.bungee.api.chat.BaseComponent;
import net.pooleaf.core.plugin.CorePlugin;
import net.pooleaf.core.modules.support.bukkit.util.BukkitReflectionUtil;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class BukkitPlatformAdapter implements PlatformAdapter {

    private BukkitCommandExecutor commandExecutor = new BukkitCommandExecutor();
    private BukkitTabCompleter tabCompleter = new BukkitTabCompleter();

    private Plugin plugin;


    @Override
    public void init(CorePlugin plugin) {
        this.plugin = (Plugin) plugin;
    }

    @Override
    public boolean registerCommand(AnnoCommand command) {
        Class firstParameterType = command.getExecuteMethod().getParameterTypes()[0];

        /* Player Only */
        if (firstParameterType.equals(Player.class) || CommonPlayer.class.isAssignableFrom(firstParameterType)) {
            command.setPlayerOnly(true);
        }
        /* Console Only */
        else if (firstParameterType.equals(ConsoleCommandSender.class) || CommonCommandSender.class.isAssignableFrom(firstParameterType)) {
            command.setConsoleOnly(true);
        }
        /* Invalid Parameter Type */
        else if (!firstParameterType.isAssignableFrom(CommandSender.class)) return false;


        /* Register to Bukkit */
        if (!command.hasParent()) {
            PluginCommand pluginCommand = BukkitReflectionUtil.getCommand(command.getCommandLine(), plugin);

            /* Alias */
            if (command.getName().size() > 1) {
                List<String> aliases = new ArrayList<>();
                for (String alias : command.getName()) {
                    aliases.add(alias.toLowerCase());
                }
                aliases.remove(0);

                pluginCommand.setAliases(aliases);
            }

            /* Description */
            if (command.getDescription() != null) {
                pluginCommand.setDescription(command.getDescription());
            }

            /* Usage */
            pluginCommand.setUsage(command.getUsage(null).toPlainText());

            /* Permission */
            pluginCommand.setPermission(command.getPermission());

            /* Command Executor */
            pluginCommand.setExecutor(commandExecutor);

            /* Tab Completer */
            pluginCommand.setTabCompleter(tabCompleter);

            BukkitReflectionUtil.getCommandMap().register(plugin.getName(), pluginCommand);
        }

        return true;
    }

    @Override
    public boolean hasPermission(Object sender, String permission) {
        return ((sender instanceof Player) && ((Player) sender).isOp()) || ((CommandSender) sender).hasPermission(permission);
    }

    @Override
    public boolean isPlayer(Object sender) {
        return sender instanceof Player;
    }

    @Override
    public boolean isConsole(Object sender) {
        return sender instanceof ConsoleCommandSender;
    }

    @Override
    public void sendMessage(Object sender, String message) {
        if (sender instanceof CommonCommandSender) {
            ((CommonCommandSender) sender).sendMessage(message);
        } else {
            ((CommandSender) sender).sendMessage(message);
        }
    }

    @Override
    public void sendMessage(Object sender, BaseComponent... component) {
        if (sender instanceof CommonCommandSender) {
            ((CommonCommandSender) sender).sendMessage(component);
        } else if (sender instanceof Player) {
            ((Player) sender).sendMessage(component);
        } else {
            ((CommandSender) sender).sendMessage(BaseComponent.toPlainText(component));
        }
    }

}
