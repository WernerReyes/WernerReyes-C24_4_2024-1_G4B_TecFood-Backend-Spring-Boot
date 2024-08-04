package com.backend.app.utilities;

public class ValidationsUtility {

    public static String hasText(String text) {
        if (text == null || text.trim().isEmpty()) return null;
        return text;
    }
}
