package com.ciarasouthgate.wizardscorekeeper;

public class Constants {
    public static final String RULES_PREF = "RULES_PREF";
    public static final String CDN_RULE = "CDN_RULE";
    public static final String NO_EVEN = "NO_EVEN";
    public static final String ONE_TO_X = "ONE_TO_X";

    public static int getRoundCounts(int numPlayers) {
        switch (numPlayers) {
            case 3:
                return 20;
            case 4:
                return 15;
            case 5:
                return 12;
            case 6:
                return 10;
            default:
                return -1;
        }
    }
}
