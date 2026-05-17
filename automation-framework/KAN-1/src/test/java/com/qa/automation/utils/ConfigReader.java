package com.qa.automation.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigReader {

    private static final Properties props = new Properties();

    static {
        try (InputStream in = ConfigReader.class.getClassLoader()
                .getResourceAsStream("config/config.properties")) {
            if (in == null) throw new RuntimeException("config.properties not found on classpath");
            props.load(in);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load config.properties", e);
        }
    }

    public static String get(String key) {
        String sysVal = System.getProperty(key);
        return sysVal != null ? sysVal : props.getProperty(key, "");
    }

    public static String getAppUrl() { return get("app.url"); }
    public static String getBrowser() { return get("browser.name"); }
    public static boolean isHeadless() { return Boolean.parseBoolean(get("browser.headless")); }
    public static int getImplicitTimeout() { return Integer.parseInt(get("browser.timeout.implicit")); }
    public static int getExplicitTimeout() { return Integer.parseInt(get("browser.timeout.explicit")); }
    public static String getValidUsername() { return get("test.data.valid.username"); }
    public static String getValidPassword() { return get("test.data.valid.password"); }
    public static String getScreenshotPath() { return get("screenshot.path"); }
}
