package app.utils;

import jakarta.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

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
    
    public static Map<String, String[]> cloneParamsMap(Map<String, String[]> queryParamsMap) {
        return new HashMap<>(queryParamsMap);
    }
    
    public static String getQueryParamsString(Map<String, String> queryParamsMap) {
        StringBuilder builder = new StringBuilder();
        
        //keeps track of whether this is the first parameter while looping
        //because the prefix of the first param will be '?' and the rest will be '&'
        boolean firstParam = true;
        
        for (Entry<String, String> queryParam : queryParamsMap.entrySet()) {
            if (firstParam) {
                builder.append('?');
                firstParam = false;
            } else {
                builder.append('&');
            }
            
            builder.append(queryParam.getKey());
            builder.append('=');
            builder.append(queryParam.getValue());
        }
        
        return builder.toString();
    } 
}
