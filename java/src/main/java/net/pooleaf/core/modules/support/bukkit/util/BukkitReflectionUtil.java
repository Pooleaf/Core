package net.pooleaf.core.modules.support.bukkit.util;

import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;
import net.pooleaf.core.modules.support.bukkit.nms.NmsVersion;
import net.pooleaf.core.modules.support.common.AutoRegisterExclude;
import net.pooleaf.core.modules.support.common.util.ReflectionUtil;
import net.pooleaf.core.plugin.CorePlugin;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandMap;
import org.bukkit.command.PluginCommand;
import org.bukkit.command.SimpleCommandMap;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.SimplePluginManager;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.Map;

@UtilityClass
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
    return ReflectionUtil.getMethod(obj.getClass(), "getHandle").invoke(obj);
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
    return ReflectionUtil.getMethodAll(nmsEntity.getClass(), "getBukkitEntity").invoke(nmsEntity);
  }

  public static int registerListeners(CorePlugin plugin) {
    int count = 0;

    for (Class targetClass : ReflectionUtil.getClasses(plugin)) {
      try {
        // Listener 클래스인지 확인
        if (!Listener.class.isAssignableFrom(targetClass)) {
          continue;
        }

        Listener listener = (Listener) targetClass.newInstance();

        // 자동 등록 제외 Listener
        if (listener.getClass().getAnnotation(AutoRegisterExclude.class) != null) {
          continue;
        }

        Bukkit.getPluginManager().registerEvents(listener, (Plugin) plugin);
        count++;
      } catch (Exception e) {
      } catch (Error e) {
      }
    }

    return count;
  }

  /**
   * 해당 Listener을 등록 해제시킵니다.
   */
  public static void unregisterListener(Listener listener) {
    HandlerList.unregisterAll(listener);
  }

  /**
   * 해당 플러그인의 Listener들을 모두 등록 해제시킵니다.
   */
  public static void unregisterListeners(Plugin plugin) {
    HandlerList.unregisterAll(plugin);
  }

  /**
   * 해당 플러그인의 명령어들을 모두 등록 해제시킵니다.
   */
  @SneakyThrows
  public static void unregisterCommands(Plugin plugin) {
    SimpleCommandMap commandMap = (SimpleCommandMap) getCommandMap();

    Field knownCommandsField = SimpleCommandMap.class.getDeclaredField("knownCommands");
    knownCommandsField.setAccessible(true);
    Map<String, Command> commands = (Map<String, Command>) knownCommandsField.get(commandMap);

    Iterator<Map.Entry<String, Command>> it = commands.entrySet().iterator();
    while (it.hasNext()) {
      Map.Entry<String, Command> entry = it.next();

      if (entry.getValue() instanceof PluginCommand) {
        PluginCommand pluginCommand = (PluginCommand) entry.getValue();

        if (pluginCommand.getPlugin().equals(plugin)) {
          pluginCommand.unregister(commandMap);
          it.remove();
        }
      }
    }
  }

  @SneakyThrows
  public static Object getNmsBlock(Object block) {
    Method getNMSBlockMethod = ReflectionUtil.getMethodAll(block.getClass(), "getNMSBlock");
    getNMSBlockMethod.setAccessible(true);
    return getNMSBlockMethod.invoke(block);
  }

  @SneakyThrows
  public static Object getNmsBlock(int blockTypeId) {
    Object nmsClass = getNmsClass("Block");
    Method getNMSBlockMethod = getNmsClass("Block").getMethod("getById", int.class);
    return getNMSBlockMethod.invoke(null, blockTypeId);
  }

  @SneakyThrows
  public static Object getStepSoundObject(Object nmsBlock) {
    return nmsBlock.getClass().getField("stepSound").get(nmsBlock);
  }

  @SneakyThrows
  public static String getBlockBreakSound(Object block) {
    Object stepSoundObject = getStepSoundObject(block);
    Method getBreakSoundMethod = stepSoundObject.getClass().getMethod("getBreakSound");
    getBreakSoundMethod.setAccessible(true);
    return (String) getBreakSoundMethod.invoke(stepSoundObject);
  }

  @SneakyThrows
  public static String getBlockPlaceSound(Object block) {
    // 설치 소리는 부시는 소리와 같음
    return getBlockBreakSound(block);
  }

  @SneakyThrows
  public static String getBlockStepSound(Object block) {
    Object stepSoundObject = getStepSoundObject(block);
    return (String) stepSoundObject.getClass().getMethod("getStepSound").invoke(stepSoundObject);
  }

  @SneakyThrows
  public static float getBlockSoundVolume(Object block) {
    Object stepSoundObject = getStepSoundObject(block);
    return (float) stepSoundObject.getClass().getMethod("getVolume1").invoke(stepSoundObject);
  }

  @SneakyThrows
  public static float getBlockSoundPitch(Object block) {
    Object stepSoundObject = getStepSoundObject(block);
    return (float) stepSoundObject.getClass().getMethod("getVolume2").invoke(stepSoundObject);
  }

}