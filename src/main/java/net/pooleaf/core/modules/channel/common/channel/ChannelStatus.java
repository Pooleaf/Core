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


  static {
    messages.put(OFFLINE, "§7오프라인");
    messages.put(CRASHED, "§c크래시");
    messages.put(PREPARING, "§6준비 중");
    messages.put(RUNNING, "§a온라인");
  }


  public static void getMessage(int statusCode, String statusMessage) {
    messages.put(statusCode, statusMessage);
  }

  public static String getMessage(int statusCode) {
    return messages.get(statusCode);
  }

  public static String getOfflineMessage() {
    return messages.get(OFFLINE);
  }

}
