package net.pooleaf.core.modules.support.common.util;

import java.util.Comparator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 숫자가 섞인 문자열을 숫자순으로 제대로 정렬해줍니다.
 * 적용 전: test.1 test.11 test.2 test.3
 * 적용 후: test.1 test.2 test.3 test.11
 */
public class StringNumberComparator implements Comparator<String> {

    private static final Pattern stringPattern = Pattern.compile("[^0-9]+");
    private static final Pattern numberPattern = Pattern.compile("[0-9]+");


    @Override
    public int compare(String o1, String o2) {
        Matcher numberMatcher1 = numberPattern.matcher(o1);
        Matcher numberMatcher2 = numberPattern.matcher(o2);
        String number1 = numberMatcher1.find() ? numberMatcher1.group() : null;
        String number2 = numberMatcher2.find() ? numberMatcher2.group() : null;

        Matcher stringMatcher1 = stringPattern.matcher(o1);
        Matcher stringMatcher2 = stringPattern.matcher(o2);
        String string1 = stringMatcher1.find() ? stringMatcher1.group() : null;
        String string2 = stringMatcher2.find() ? stringMatcher2.group() : null;

        int stringCompare = string1.compareTo(string2);
        if (stringCompare == 0) {
            return Integer.parseInt(number1) - (Integer.parseInt(number2));
        }

        return stringCompare;
    }

}
