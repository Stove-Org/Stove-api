package gg.stove.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class DateUtil {

    public static final DateTimeFormatter DATE_TIME_FORMATTER_UNTIL_MINUTE =
        DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm").withLocale(Locale.KOREA);
    public static final DateTimeFormatter DATE_TIME_FORMATTER_WITH_WEEK =
        DateTimeFormatter.ofPattern("yyyy.MM.dd a hh:mm").withLocale(Locale.KOREA);

    public static LocalDateTime convertWithUntilMinuteString(String s) {
        return LocalDateTime.parse(s, DATE_TIME_FORMATTER_UNTIL_MINUTE);
    }

    public static String convertToWithWeekString(LocalDateTime localDateTime) {
        return localDateTime.format(DATE_TIME_FORMATTER_WITH_WEEK);
    }
}
