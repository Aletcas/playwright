package aletca.task_6_9.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Config {
    private static final Properties properties = new Properties();

    static {
        try (InputStream input = Config.class.getClassLoader()
                .getResourceAsStream("config.properties")) {
            if (input == null) {
                throw new RuntimeException("unable to find config.properties");
            }
            properties.load(input);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load config.properties", e);
        }
    }

    public static String getBaseUrl() {
        return properties.getProperty("base.url");
    }


    public static String getPage(String pageKey) {
        return properties.getProperty(pageKey + ".page.path");
    }

    public static String getAlertsPage() {
        return getPage("alerts");
    }

    public static String getDynamicPage() {
        return getPage("dynamic");
    }




    public static int getTimeout() {
        return Integer.parseInt(properties.getProperty("timeout"));
    }

    public static boolean isHeadless() {
        return Boolean.parseBoolean(properties.getProperty("headless"));
    }
}
