package net.pooleaf.core.support.common.util;

import java.util.ArrayList;
import java.util.List;

public class StringUtil {

  /**
   * 밀리초를 n일 n시 n분 n초로 반환합니다.
   * n이 0일 경우 제외됩니다.
   * 예) 1일 1초
   * @param timeMillis 변환할 밀리초
   * @return n일 n시 n분 n초
   */
  public static String buildTimeStringFromMillis(long timeMillis) {
    List<String> timeStrings = new ArrayList<>();

    if ((int) (timeMillis / 86400000) > 0) timeStrings.add((int) (timeMillis / 86400000) + "일");
    if ((int) (timeMillis / 3600000 % 24) > 0) timeStrings.add((int) (timeMillis / 36000000 % 24) + "시");
    if ((int) (timeMillis / 60000 % 60) > 0) timeStrings.add((int) (timeMillis / 60000 % 60) + "분");
    if ((int) (int) (timeMillis / 1000 % 60) > 0) timeStrings.add((int) (timeMillis / 1000 % 60) + "초");

    return String.join(" ", timeStrings);
  }

  /**
   * 초를 n일 n시 n분 n초로 반환합니다.
   * n이 0일 경우 제외됩니다.
   * 예) 1일 1초
   * @param seconds 변환할 초
   * @return n일 n시 n분 n초
   */
  public static String buildTimeStringFromSeconds(long seconds) {
    return buildTimeStringFromMillis(seconds * 1000);
  }

  /**
   * text에 str이 몇 번 들어가있는지 반환합니다.
   * @param text 확인할 텍스트
   * @param str 셀 문자열
   * @return 들어간 횟수
   */
  public static int countMatches(String text, String str) {
    int count = 0;

    int index = -1;
    while ((index = text.indexOf(str, index + 1)) >= 0) {
      count++;
    }

    return count;
  }

}