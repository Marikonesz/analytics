package com.analytics.core;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigManager {

  private static final Properties properties = new Properties();

  static {
    try (InputStream input = ConfigManager.class.getClassLoader()
        .getResourceAsStream("config.properties")) {
      if (input != null) {
        properties.load(input);
      } else {
        throw new RuntimeException("config.properties not found in classpath");
      }
    } catch (IOException e) {
      throw new RuntimeException("Failed to load config.properties", e);
    }
  }

  public static String get(String key) {
    String systemProp = System.getProperty(key);
    return systemProp != null ? systemProp : properties.getProperty(key);
  }
}