package net.pooleaf.core.modules.support.common.util;

import lombok.experimental.UtilityClass;

import java.util.ArrayList;
import java.util.List;

@UtilityClass
public class NumberUtil {

  public static long getDayTick() {
    return 86400000;
  }

  public static long getHourTick() {
    return 3600000;
  }

  public static long getMinuteTick() {
    return 60000;
  }

  public static boolean isInteger(Object o) {
    try {
      Integer.parseInt(o.toString());
      return true;
    } catch(NumberFormatException e) {
      return false;
    }
  }

  public static boolean isLong(Object o) {
    try {
      Long.parseLong(o.toString());
      return true;
    } catch (NumberFormatException e) {
      return false;
    }
  }

  public static boolean isFloat(Object o) {
    try {
      Float.parseFloat(o.toString());
      return true;
    } catch (NumberFormatException e) {
      return false;
    }
  }

  public static boolean isDouble(Object o) {
    try {
      Double.parseDouble(o.toString());
      return true;
    } catch (NumberFormatException e) {
      return false;
    }
  }

  public static boolean isShort(Object o) {
    try {
      Short.parseShort(o.toString());
      return true;
    } catch (NumberFormatException e) {
      return false;
    }
  }

  public static Integer getInteger(Object o) {
    return isInteger(o) ? Integer.parseInt(o.toString()) : null;
  }

  public static Long getLong(Object o) {
    return isLong(o) ? Long.parseLong(o.toString()) : null;
  }

  public static Float getFloat(Object o) {
    return isFloat(o) ? Float.parseFloat(o.toString()) : null;
  }

  public static Double getDouble(Object o) {
    return isDouble(o) ? Double.parseDouble(o.toString()) : null;
  }

  public static Short getShort(Object o) {
    return isShort(o) ? Short.parseShort(o.toString()) : null;
  }

  /**
   *
   * @param range 숫자1~숫자2 또는 숫자
   * @return 숫자1~숫자2 범위의 숫자 List를 반환합니다.
   */
  public static List<Integer> getIntegers(String range) {
    List<Integer> list = new ArrayList<>();

    if(range.contains("~")) {
      String[] s = range.split("~");

      int min = Integer.parseInt(s[0]);
      int max = Integer.parseInt(s[1]);

      for(; min <= max; min++) {
        list.add(min);
      }
    } else {
      list.add(Integer.parseInt(range));
    }

    return list;
  }

  public static double random() {
    return Math.random();
  }

  /**
   * 0부터 i까지의 랜덤 숫자를 불러옵니다.
   * @param i 최대 랜덤 범위
   * @return 0부터 i까지의 랜덤 숫자
   */
  public static int random(int i) {
    return (int) (random() * i);
  }

  /**
   * min부터 max까지의 랜덤 숫자를 반환합니다.
   * @param min 최소 숫자
   * @param max 최대 숫자
   * @return min부터 max까지의 랜덤 숫자
   */
  public static int random(int min, int max) {
    return (int) (Math.random() * (max - min + 1)) + min;
  }

  /**
   * 숫자를 로마 숫자로 변환합니다.
   * @param number 변환할 숫자
   * @return 변환된 로마 숫자
   */
  public static String numberToRoman(int number) {
    String roman = "";

    for (int i = 0; i < RomanNumeral.values().length; i++) {
      int count = (int) ((float) number / RomanNumeral.values()[i].getNumber());

      if (count > 0) {
        number %= RomanNumeral.values()[i].getNumber();
        roman += RomanNumeral.values()[i].name().repeat(count);
      }
    }

    return roman;
  }

}