package net.pooleaf.core.modules.support.common.util;

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

  /**
   * 배열을 특정 문자로 이어줍니다.
   * @param array 이어줄 배열
   * @param connectChar 이어줄 문자
   * @return 배열을 이어준 문자열
   */
  public static String joinArray(String connectChar, Object[] array) {
    StringBuilder builder = new StringBuilder();

    for (Object object : array) {
      if (builder.length() < 1) {
        builder.append(object);
      } else {
        builder.append(connectChar + object);
      }
    }

    return builder.toString();
  }

  /**
   * SnakeCase문자를 LowerCamelCase 문자로 변환합니다.
   * @param snake 변환할 SnakeCase 문자
   * @return 변환된 LowerCamelCase 문자
   */
  public static String convertSnakeCaseToLowerCamelCase(String snake) {
    StringBuilder builder = new StringBuilder();

    boolean requireUpperCase = false;
    for (char c : snake.toCharArray()) {
      String charString = Character.toString(c);
      if ("_".equals(charString)) {
        requireUpperCase = true;
      } else if (requireUpperCase) {
        builder.append(charString.toUpperCase());
        requireUpperCase = false;
      } else {
        builder.append(charString);
      }
    }

    return builder.toString();
  }

}