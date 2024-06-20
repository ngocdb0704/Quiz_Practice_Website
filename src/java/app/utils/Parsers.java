package app.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Arrays;

public class Parsers {
    public static int parseIntOrDefault(String value, int defaultValue) {
        int ret = defaultValue;
        
        if (value == null) return ret;
        
        try {
            ret = Integer.parseInt(value);
        } catch (NumberFormatException ex) {
            ex.printStackTrace();
        }
                
        return ret;
    }

    public static Integer[] parseInts(String[] values) {
        if (values == null) return new Integer[0];

        int i = 0;
        Integer[] temp = new Integer[values.length];

        for (String val : values) {
            try {
                int num = Integer.parseInt(val);
                temp[i++] = num;
            } catch (NumberFormatException ex) {
                ex.printStackTrace();
            }
        }

        return Arrays.copyOfRange(temp, 0, i);
    }
    
    public static LocalDate parseDateOrDefault(String value, String format, LocalDate defaultValue) {
        LocalDate ret = defaultValue;
        if (value == null) return ret;
        
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern(format);
        
        try {
            ret = LocalDate.parse(value, fmt);
        } catch (DateTimeParseException ex) {
            ex.printStackTrace();
        }
        
        return ret;
    }
}
