package com.analytics.core;

import com.analytics.core.enums.ContextKey;
import java.util.HashMap;
import java.util.Map;

public class TestContext {

  private final Map<String, Object> context = new HashMap<>();

  public <T> void set(ContextKey key, T value) {
    context.put(key.name(), value);
  }

  @SuppressWarnings("unchecked")
  public <T> T get(ContextKey key, Class<T> clazz) {
    Object value = context.get(key.name());
    if (value == null) {
      throw new RuntimeException("Key not found in TestContext: " + key);
    }
    return (T) value;
  }

  public boolean contains(ContextKey key) {
    return context.containsKey(key.name());
  }
}