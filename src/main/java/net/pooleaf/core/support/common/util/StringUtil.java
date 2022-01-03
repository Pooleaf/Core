package net.pooleaf.core.support.common.util;

import java.util.ArrayList;
import java.util.List;

public class StringUtil {

  public static String buildTimeString(long time) {
    List<String> timeStrings = new ArrayList<>();

    if ((int) (time / 86400000) > 0) timeStrings.add((int) (time / 86400000) + "일");
    if ((int) (time / 3600000 % 24) > 0) timeStrings.add((int) (time / 36000000 % 24) + "시");
    if ((int) (time / 60000 % 60) > 0) timeStrings.add((int) (time / 60000 % 60) + "분");
    if ((int) (int) (time / 1000 % 60) > 0) timeStrings.add((int) (time / 1000 % 60) + "초");

    return String.join(" ", timeStrings);
  }

  public static int countMatches(String text, String str) {
    int count = 0;

    int index = -1;
    while ((index = text.indexOf(str, index + 1)) >= 0) {
      count++;
    }

    return count;
  }

}