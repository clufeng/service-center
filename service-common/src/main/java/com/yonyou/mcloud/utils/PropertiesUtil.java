package com.yonyou.mcloud.utils;

import java.util.*;

/**
 * Created by hubo on 16/1/27
 */
public class PropertiesUtil {

    public static Properties createProperties(String propertiesFileName) {
        Properties props = new Properties();
        ResourceBundle resourceBundle = ResourceBundle.getBundle(propertiesFileName);
        Enumeration<String> enumeration = resourceBundle.getKeys();
        while (enumeration.hasMoreElements()) {
            String key = enumeration.nextElement();
            props.put(key, resourceBundle.getString(key));
        }
        return props;
    }

    public static Map<String, String> createMapByProperties(String propertiesFileName) {
        Map<String, String> config = new HashMap<>();
        ResourceBundle resourceBundle = ResourceBundle.getBundle(propertiesFileName);
        Enumeration<String> enumeration = resourceBundle.getKeys();
        while (enumeration.hasMoreElements()) {
            String key = enumeration.nextElement();
            config.put(key, resourceBundle.getString(key));
        }
        return config;
    }

}
