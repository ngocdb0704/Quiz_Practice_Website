package app.utils;

import jakarta.servlet.ServletContext;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Properties;

public class Config {
    public static Properties getConfig(ServletContext ctx) {
        try {
            InputStream stream = ctx.getResourceAsStream("/WEB-INF/config.properties");
            Properties prop = new Properties();
            prop.load(stream); 
            
            return prop;
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return null;
    }
}
