package net.pooleaf.core.support.bukkit.nms;

import com.google.common.base.Preconditions;
import org.bukkit.Bukkit;

public class NmsDetector {

  public static void detectNmsVersion() {
    String nmsPackage = Bukkit.getServer().getClass().getPackage().getName();
    String nmsString = nmsPackage.substring(nmsPackage.lastIndexOf(".") + 1);
    NmsVersion nmsVersion = NmsVersion.getByName(nmsString);

    Preconditions.checkNotNull(nmsVersion, "알 수 없는 NMS 버전입니다.");

    NmsVersion.setCurrentVersion(nmsVersion);
  }

}
