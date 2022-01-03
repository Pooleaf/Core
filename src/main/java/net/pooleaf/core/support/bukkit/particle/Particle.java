package net.pooleaf.core.support.bukkit.particle;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.SneakyThrows;
import net.pooleaf.core.support.bukkit.nms.NmsVersion;
import net.pooleaf.core.support.bukkit.util.BukkitReflectionUtil;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

@AllArgsConstructor
public enum Particle {

  EXPLODE_NORMAL("explode", 0, NmsVersion.UNKNOWN),
  EXPLODE_LARGE("largeexplode", 1, NmsVersion.UNKNOWN),
  EXPLODE_HUGE("hugeeexplosion", 2, NmsVersion.UNKNOWN),
  FIREWORKS_SPARK("fireworksSpark", 3, NmsVersion.UNKNOWN),
  WATER_BUBBLE("bubble", 4, NmsVersion.UNKNOWN),
  WATER_SPLASH("splash", 5, NmsVersion.UNKNOWN),
  WATER_WAKE("wake", 6, NmsVersion.v1_7_R1),
  SUSPENDED("suspended", 7, NmsVersion.v1_7_R1),
  SUSPENDED_DEPTH("depthSuspend", 8, NmsVersion.v1_8_R1),
  CRIT("crit", 9, NmsVersion.UNKNOWN),
  CIRT_MAGIC("magicCrit", 10, NmsVersion.UNKNOWN),
  SMOKE("smoke", 11, NmsVersion.UNKNOWN),
  SMOKE_LARGE("largesmoke", 12, NmsVersion.UNKNOWN),
  SPELL("spell", 13, NmsVersion.UNKNOWN),
  SPELL_INSTANT("instantSpell", 14, NmsVersion.UNKNOWN),
  SPELL_MOB("mobSpell", 15, NmsVersion.UNKNOWN),
  SPELL_MOB_AMBIENT("mobSpellAmbient", 16, NmsVersion.UNKNOWN),
  SPELL_WITCH("witchMagic", 17, NmsVersion.UNKNOWN),
  DRIP_WATER("dripWater", 18, NmsVersion.UNKNOWN),
  DRIP_LAVA("dripLava", 19, NmsVersion.UNKNOWN),
  VILLAGER_ANGRY("angryVillager", 20, NmsVersion.UNKNOWN),
  VILLAGER_HAPPY("happyVillager", 21, NmsVersion.UNKNOWN),
  TOWN_AURA("townarua", 22, NmsVersion.UNKNOWN),
  NOTE("note", 23, NmsVersion.UNKNOWN),
  PORTAL("portal", 24, NmsVersion.UNKNOWN),
  ENCHANT_TABLE("enchantmenttable", 25, NmsVersion.UNKNOWN),
  FLAME("flame", 26, NmsVersion.UNKNOWN),
  LAVA("lava", 27, NmsVersion.UNKNOWN),
  FOOTSTEP("footstep", 28, NmsVersion.UNKNOWN),
  CLOUD("cloud", 29, NmsVersion.UNKNOWN),
  RED_DUST("reddust", 30, NmsVersion.UNKNOWN),
  SNOWBALL("snowballpoof", 31, NmsVersion.UNKNOWN),
  SNOW_SHOVEL("snowshovel", 32, NmsVersion.UNKNOWN),
  SLIME("slime", 33, NmsVersion.UNKNOWN),
  HEART("heart", 34, NmsVersion.UNKNOWN),
  BARRIER("barrier", 35, NmsVersion.v1_8_R1),
  ITEM_CRACK("iconcrack", 36, NmsVersion.UNKNOWN),
  BLOCK_CRACK("blockcrack", 37, NmsVersion.UNKNOWN),
  BLOCK_DUST("blockdust", 38, NmsVersion.v1_7_R1),
  WATER_DROP("droplet", 39, NmsVersion.v1_8_R1),
  ITEM_TAKE("take", 40, NmsVersion.v1_8_R1),
  MOB_APPEARANCE("mobappearance", 41, NmsVersion.v1_8_R1),
  DRAGON_BREATH("dragonbreath", 42, NmsVersion.v1_9_R1),
  END_ROD("endRod", 43, NmsVersion.v1_9_R1),
  DAMAGE_INDICATOR("damageIndicator", 44, NmsVersion.v1_9_R1),
  SWEEP_ATTACK("sweepAttack", 45, NmsVersion.v1_9_R1),
  FALLING_DUST("fallingdust", 46, NmsVersion.v1_10_R1);


  @Getter
  private String name;

  @Getter
  private int id;

  @Getter
  private NmsVersion minVersion;

  private static final int SHOW_DISTANCE = 20;


  public boolean isSupported() {
    return !NmsVersion.getCurrentVersion().isBefore(minVersion);
  }

  public static List<Particle> getSupportedParticles() {
    return Arrays.stream(values())
        .filter(Particle::isSupported)
        .collect(Collectors.toList());
  }

  public static Particle getByName(String name) {
    for (Particle particle : values()) {
      if (particle.getName().equalsIgnoreCase(name)) return particle;
    }

    return null;
  }

  public static Particle getById(int id) {
    for (Particle particle : values()) {
      if (particle.getId() == id) return particle;
    }

    return null;
  }

  public void spawn(Player player, Location location, float speed, int count) {
    if (!player.getWorld().equals(location.getWorld())) return;

    spawn(player, location.getX(), location.getY(), location.getZ(), 0, 0, 0, speed, count);
  }

  public void spawn(Player player, Location location, float offsetX, float offsetY, float offsetZ, float speed, int count) {
    if (!player.getWorld().equals(location.getWorld())) return;

    spawn(player, location.getX(), location.getY(), location.getZ(), offsetX, offsetY, offsetZ, speed, count);
  }

  public void spawn(Location location, float speed, int count) {
    spawn(location, 0, 0, 0, speed, count);
  }

  public void spawn(Location location, float offsetX, float offsetY, float offsetZ, float speed, int count) {
    for (Player player : Bukkit.getOnlinePlayers()) {
      if (!player.getWorld().equals(location.getWorld())) continue;

      spawn(player, location.getX(), location.getY(), location.getZ(), offsetX, offsetY, offsetZ, speed, count);
    }
  }

  public void spawn(double x, double y, double z, float speed, int count) {
    for (Player player : Bukkit.getOnlinePlayers()) {
      spawn(player, x, y, z, 0, 0, 0, speed, count);
    }
  }

  public void spawn(double x, double y, double z, float offsetX, float offsetY, float offsetZ, float speed, int count) {
    for (Player player : Bukkit.getOnlinePlayers()) {
      spawn(player, x, y, z, offsetX, offsetY, offsetZ, speed, count);
    }
  }

  @SneakyThrows
  public void spawn(Player player, double x, double y, double z, float offsetX, float offsetY, float offsetZ, float speed, int count) {
    if (player.getLocation().distance(new Location(player.getWorld(), x, y, z)) > SHOW_DISTANCE) return;

    Object packet = BukkitReflectionUtil.getNmsClass("PacketPlayOutWorldParticles").newInstance();

    for (Field field : packet.getClass().getDeclaredFields()) {
      field.setAccessible(true);

      switch(field.getName()) {
        case "a": field.set(packet, BukkitReflectionUtil.getNmsClass("EnumParticle").getEnumConstants()[id]); break;
        case "b": field.setFloat(packet, (float) x); break;
        case "c": field.setFloat(packet, (float) y); break;
        case "d": field.setFloat(packet, (float) z); break;
        case "e": field.setFloat(packet, offsetX); break;
        case "f": field.setFloat(packet, offsetY); break;
        case "g": field.setFloat(packet, offsetZ); break;
        case "h": field.setFloat(packet, speed); break;
        case "i": field.setInt(packet, count); break;
      }
    }

    BukkitReflectionUtil.sendPacket(player, packet);
  }

}
