package app.utils;

import jakarta.servlet.ServletRequest;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Date;

public class BasicFieldExtractor {
    public static <T> T extractFields(ServletRequest request, T target) throws IllegalAccessException {
        for (Field field : target.getClass().getFields()) {
            var name = field.getName();
            var type = field.getType();

            if (field.canAccess(target)) {
                field.set(target, parseAs(
                        request.getParameter(name),
                        type
                ));
            }
        }

        return target;
    }

    public static <T> T extractFieldsFailSilently(ServletRequest request, T target) {
        try {
            return extractFields(request, target);
        } catch (IllegalAccessException e) {
            System.out.println(e.getMessage());
        }

        return target;
    }

    private static Object parseAs(String data, Class<?> type) {
        try {
            if (type.equals(int.class)) {
                return Integer.parseInt(data);
            } else if (type.equals(double.class)) {
                return Double.parseDouble(data);
            } else if (type.equals(Date.class)) {
                var formatter = new SimpleDateFormat("yyyy-MM-dd");
                formatter.setLenient(false);
                return formatter.parse(data);
            } else if (type.equals(String.class)) {
                return data;
            } else if (type.equals(boolean.class)) {
                return Boolean.parseBoolean(data);
            }
        } catch (Exception e) {
            System.out.printf(
                    "Error while parsing %s, got %s\n",
                    data,
                    e.getMessage()
            );
        }

        return null;
    }
}
