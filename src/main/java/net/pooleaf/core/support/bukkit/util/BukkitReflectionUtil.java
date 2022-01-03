package net.pooleaf.core.support.bukkit.util;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import lombok.SneakyThrows;
import net.pooleaf.core.support.bukkit.nms.NmsVersion;
import net.pooleaf.core.support.common.util.ReflectionUtil;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandMap;
import org.bukkit.command.PluginCommand;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.SimplePluginManager;

public class BukkitReflectionUtil {

  @SneakyThrows()
  public static Class<?> getNmsClass(String name) {
    return Class.forName("net.minecraft.server." + NmsVersion.getCurrentVersion().getName() + "." + name);
  }

  @SneakyThrows()
  public static Class<?> getCraftBukkitClass(String name) {
    return Class.forName("org.bukkit.craftbukkit." + NmsVersion.getCurrentVersion().getName() + "." + name);
  }

  @SneakyThrows()
  public static Object getHandle(Object obj) {
    return ReflectionUtil.getMethod(obj.getClass(), "getHandle").invoke(obj, null);
  }

  @SneakyThrows()
  public static Object getPlayerConnection(Player player) {
    Object handle = getHandle(player);
    return handle.getClass().getField("playerConnection").get(handle);
  }

  @SneakyThrows()
  public static Object getNetworkManager(Player player) {
    Object playerConnection = getPlayerConnection(player);
    return playerConnection.getClass().getField("networkManager").get(playerConnection);
  }

  @SneakyThrows()
  public static void sendPacket(Player player, Object packet) {
    Object playerConnection = getPlayerConnection(player);
    ReflectionUtil.getMethod(playerConnection.getClass(), "sendPacket").invoke(playerConnection, packet);
  }

  @SneakyThrows()
  public static PluginCommand getCommand(String name, Plugin plugin) {
    Constructor<PluginCommand> constructor = PluginCommand.class.getDeclaredConstructor(String.class, Plugin.class);
    constructor.setAccessible(true);

    return constructor.newInstance(name, plugin);
  }

  @SneakyThrows()
  public static CommandMap getCommandMap() {
    Field field = SimplePluginManager.class.getDeclaredField("commandMap");
    field.setAccessible(true);

    return (CommandMap) field.get(Bukkit.getPluginManager());
  }

  @SneakyThrows()
  public static Object getBukkitEntity(Object nmsEntity) {
    return ReflectionUtil.getMethodAll(nmsEntity.getClass(), "getBukkitEntity").invoke(nmsEntity, null);
  }

}