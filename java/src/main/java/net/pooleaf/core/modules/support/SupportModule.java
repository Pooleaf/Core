package net.pooleaf.core.modules.support;

import com.google.gson.internal.bind.ReflectiveTypeAdapterFactory;
import net.pooleaf.core.module.CoreModule;
import net.pooleaf.core.modules.support.bukkit.nms.NmsDetector;
import net.pooleaf.core.modules.support.bukkit.util.BukkitGsonUtil;
import net.pooleaf.core.modules.support.common.logger.LoggerInitializer;
import net.pooleaf.core.modules.support.common.messager.MessagerInitializer;
import net.pooleaf.core.modules.support.common.platform.Platform;
import net.pooleaf.core.modules.support.common.platform.PlatformDetector;
import net.pooleaf.core.modules.support.common.util.GsonUtil;
import net.pooleaf.core.plugin.CorePlugin;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.Location;
import org.bukkit.block.banner.Pattern;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.util.BlockVector;
import org.bukkit.util.Vector;

import java.util.Arrays;
import java.util.List;

public class SupportModule extends CoreModule {

  @Override
  public String getName() {
    return "Support";
  }

  @Override
  public void onEnable(CorePlugin plugin) {
    // Platform
    PlatformDetector.detectPlatform();

    // Nms
    if (Platform.getCurrentPlatform() == Platform.BUKKIT) {
      NmsDetector.detectNmsVersion();
    }

    // Logger
    LoggerInitializer.init();

    // Messager
    MessagerInitializer.init();

    // GsonUtil
    if (Platform.getCurrentPlatform() == Platform.BUKKIT) {
      GsonUtil.gsonBuilder.registerTypeHierarchyAdapter(ConfigurationSerializable.class, new BukkitGsonUtil.ConfigurationSerializableSerializer());
      GsonUtil.gsonBuilder.registerTypeHierarchyAdapter(ConfigurationSerializable.class, new BukkitGsonUtil.ConfigurationDeserializableSerializer());

      GsonUtil.createGson();
    }
  }

}