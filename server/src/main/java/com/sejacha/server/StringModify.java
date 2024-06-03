package com.sejacha.server;

public class StringModify {
    public static boolean areStringsEqual(String s1, String s2) {
        String cleanedStr1 = cleanString(s1);
        String cleanedStr2 = cleanString(s2);
        return cleanedStr1.equals(cleanedStr2);
    }

    public static String cleanString(String s) {
        if (s == null) {
            return "";
        }
        // Entfernt führende und nachfolgende Leerzeichen und konvertiert zu
        // Großbuchstaben
        return s.trim().toLowerCase();
    }
}
