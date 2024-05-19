package app.utils;

import jakarta.servlet.http.HttpServletRequest;

public class URLUtils {
    public static String getBaseURL(HttpServletRequest req) {
        return String.format(
                "%s://%s:%s%s",
                req.getScheme(),
                req.getServerName(),
                req.getServerPort(),
                req.getContextPath()
        );
    }
}
