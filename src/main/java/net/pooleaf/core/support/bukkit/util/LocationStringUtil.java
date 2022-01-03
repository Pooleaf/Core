package net.pooleaf.core.support.bukkit.util;

import org.bukkit.Bukkit;
import org.bukkit.Location;

public class LocationStringUtil {

  public static String locationToString(Location location) {
    return location.getWorld().getName() + ", " + location.getX() + ", " + location.getY() + ", " + location.getZ() + ", " + location.getYaw() + ", " + location.getPitch();
  }

  public static String locationToSimpleString(Location location) {
    return location.getWorld().getName() + ", " + location.getX() + ", " + location.getY() + ", " + location.getZ();
  }

  public static Location stringToLocation(String str) {
    String[] strs = str.split(", ");

    if (strs.length == 4) {
      return new Location(Bukkit.getWorld(strs[0]), Double.parseDouble(strs[1]), Double.parseDouble(strs[2]), Double.parseDouble(strs[3]));
    } else {
      return new Location(Bukkit.getWorld(strs[0]), Double.parseDouble(strs[1]), Double.parseDouble(strs[2]), Double.parseDouble(strs[3]), Float.parseFloat(strs[4]), Float.parseFloat(strs[5]));
    }
  }

}
