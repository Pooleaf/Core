package net.pooleaf.core.modules.channel.common.channel;

import java.util.HashMap;
import java.util.Map;
import lombok.Getter;

public class ChannelStatus {

  public static final int OFFLINE = 0;
  public static final int CRASHED = 4;

  public static final int PREPARING = 100;
  public static final int RUNNING = 200;


  @Getter
  private static Map<Integer, String> messages = new HashMap<>();

  @Getter
  private static Map<Integer, String> itemCodes = new HashMap<>();


  static {
    messages.put(OFFLINE, "§7오프라인");
    messages.put(PREPARING, "§6준비 중");
    messages.put(CRASHED, "§c크래시");
    messages.put(RUNNING, "§a온라인");

    itemCodes.put(OFFLINE, "351:8");
    itemCodes.put(PREPARING, "351:8");
    itemCodes.put(CRASHED, "351:5");
    itemCodes.put(RUNNING, "351:10");
  }


  public static void setMessage(int statusCode, String statusMessage) {
    messages.put(statusCode, statusMessage);
  }

  public static String getMessage(int statusCode) {
    return messages.get(statusCode);
  }

  public static String getOfflineMessage() {
    return messages.get(OFFLINE);
  }

  public static void setItemCode(int statusCode, String itemCode) {
    itemCodes.put(statusCode, itemCode);
  }

  public static String getItemCode(int statusCode) {
    return itemCodes.get(statusCode);
  }

  public static String getOfflineItemCode() {
    return itemCodes.get(OFFLINE);
  }

}
