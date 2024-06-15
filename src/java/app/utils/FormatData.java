/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author admin
 */
public class FormatData {
    //add dateFormat to show data in dd/MM/yyyyadd 
    public String dateFormat(Date d){
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        return sdf.format(d);
    }
    public String stringSqlFormat(String input){
        return input.replace("!", "!!")
                .replace("%", "!%")
                .replace("_", "!_")
                .replace("[", "![");
    }
    
    /**
     * This function splits the text by key into 3 parts
     * String[0] contains the substring from the beginning upto the key
     * String[1] contains the key
     * String[2] contains the rest of the string
     * @param str
     * @param substring
     * @return 
     */
    public String[] splitBySubstring(String str, String key) {
        String[] ret = new String[] { "", "", "" };
        
        int beginningIndex = str.toLowerCase().indexOf(key.toLowerCase());
        
        // the key is not found
        if (beginningIndex == -1) {
            ret[0] = str;
        } else {
            int endIndex = beginningIndex + key.length();
            
            ret[0] = str.substring(0, beginningIndex);
            ret[1] = str.substring(beginningIndex, endIndex);
            ret[2] = str.substring(endIndex);
        }
        
        return ret;
    }
}
