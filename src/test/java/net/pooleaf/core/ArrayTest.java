package net.pooleaf.core;

import java.util.Arrays;
import org.junit.jupiter.api.Test;

public class ArrayTest {

  @Test
  public void test() {
    testMethod(1, 2, 3);

    Object[] testArr = new Object[5];
    testMethod(testArr);

    System.out.println(Arrays.asList(testArr).size());
  }

  public void testMethod(Object... objs) {
    System.out.println("length: " + objs.length);
  }

}
