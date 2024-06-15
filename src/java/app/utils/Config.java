package app.utils;

import jakarta.servlet.ServletContext;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Config {

    private Properties prop;

    public Config(ServletContext ctx) {
        InputStream stream = ctx.getResourceAsStream("/WEB-INF/config.properties");
        Properties prop = new Properties();

        try {
            prop.load(stream); 
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        this.prop = prop;
    }

    public Properties getConfig() {
        return prop;
    }
    
    public int getIntOrDefault(String key, int defaultValue) {
        try {
            String val = prop.getProperty(key);
            if (val == null) return defaultValue;

            return Integer.parseInt(val);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

        return defaultValue;
    }

    public String getStringOrDefault(String key, String defaultValue) {
        String value = prop.getProperty(key);
        return value == null ? defaultValue : value;
    }
}
