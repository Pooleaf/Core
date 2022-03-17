package net.pooleaf.core;

import net.pooleaf.core.modules.channel.common.channel.ChannelStatus;
import org.junit.jupiter.api.Test;

public class StaticTest {

  @Test
  public void test() {
    System.out.println("test\n1234".split("\n").length);
    System.out.println(ChannelStatus.getMessages());
  }

}
