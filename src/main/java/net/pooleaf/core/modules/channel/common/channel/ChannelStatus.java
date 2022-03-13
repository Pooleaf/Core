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
  private static Map<Integer, String> names = new HashMap<>();


  {
    names.put(OFFLINE, "오프라인");
    names.put(CRASHED, "크래쉬");
    names.put(PREPARING, "준비 중");
    names.put(RUNNING, "작동 중");
  }


  public static void setName(int statusCode, String statusName) {
    names.put(statusCode, statusName);
  }

  public static String getName(int statusCode) {
    return names.get(statusCode);
  }

}
