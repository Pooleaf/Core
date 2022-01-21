package net.pooleaf.core.modules.support.common.platform;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
public enum Platform {

  BUKKIT("버킷"),
  BUNGEECORD("번지코드");


  @Getter
  private final String name;

  @Setter(AccessLevel.PROTECTED)
  @Getter
  private static Platform currentPlatform;

}
