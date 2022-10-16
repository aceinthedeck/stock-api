package com.rbc.stocks.services;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Formatter {

    public static LocalDate stringToDate(String date, String format) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        return LocalDate.parse(date, formatter);
    }

    public static String removeCurrencySymbolFromPrice(String price) {
        return price.replaceAll("[^0-9\\.-]+", "");
    }

    public static Double parseDoubleOrNull(String str) {
        if(str.isBlank() || str == null) {
            return null;
        }
        return Double.parseDouble(str);
    }

    public static Long parseLongOrNull(String str) {
        if(str.isBlank() || str == null) {
            return null;
        }
        return Long.parseLong(str);
    }

    public static Integer parseIntOrNull(String str) {
        if(str.isBlank() || str == null) {
            return null;
        }
        return Integer.parseInt(str);
    }

}
