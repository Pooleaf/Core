package net.pooleaf.core.modules.support.common.platform;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
public enum Platform {

  BUKKIT("버킷", "k"),
  BUNGEECORD("번지코드", "g");


  @Getter
  private final String name;

  @Getter
  private final String prefix;


  @Setter(AccessLevel.PROTECTED)
  @Getter
  private static Platform currentPlatform;

}
