package net.pooleaf.core.modules.channel.common.channel;

public class KnownChannelStatus {

  public static final ChannelStatus OFFLINE = new ChannelStatus(0, "오프라인", "§7", "351:8");
  public static final ChannelStatus STARTING = new ChannelStatus(1, "채널 준비 중", "§6", "351:13"); // 서버 플러그인 로딩
  public static final ChannelStatus ONLINE = new ChannelStatus(2, "온라인", "§a", "351:10");
  public static final ChannelStatus CRASHED = new ChannelStatus(3, "크래시", "§c", "351:5");

  public static final ChannelStatus GAME_WAITING = new ChannelStatus(301, "대기 중", "§b", "351:10");
  public static final ChannelStatus GAME_STARTING = new ChannelStatus(302, "게임 시작 중", "§6", "351:9");
  public static final ChannelStatus GAME_PLAYING = new ChannelStatus(303, "게임 중", "§c", "351:13");
  public static final ChannelStatus GAME_ENDING = new ChannelStatus(304, "게임을 끝내는 중", "§7", "351:13");

}
