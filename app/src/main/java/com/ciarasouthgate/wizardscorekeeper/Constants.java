package com.ciarasouthgate.wizardscorekeeper;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

public final class Constants {
    public static final String RULES_PREF = "RULES_PREF";
    public static final String CDN_RULE = "CDN_RULE";
    public static final String NO_EVEN = "NO_EVEN";
    public static final String ONE_TO_X = "ONE_TO_X";
    public static final String SAVED_GAMES = "SAVED_GAMES";
    public static final String GAME_ID = "GAME_ID";

    public static final DateTimeFormatter SAVE_FORMAT = DateTimeFormatter.ofPattern("yyMMddHHmmss");
    public static final DateTimeFormatter PRINT_FORMAT =
            DateTimeFormatter.ofLocalizedDateTime(FormatStyle.LONG, FormatStyle.SHORT);

    private Constants() {
    }

    static LocalDateTime stringToDate(String dateString) {
        return LocalDateTime.parse(dateString, SAVE_FORMAT);
    }

    static String prettyPrintDate(LocalDateTime date) {
        return date.format(PRINT_FORMAT);
    }

    static String prettyPrintDate(String dateString) {
        return prettyPrintDate(stringToDate(dateString));
    }
}
