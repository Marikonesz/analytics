package com.analytics.api.enums;

public enum ApiEndpoints {
  TRANSACTIONS("/api/transactions");

  private final String path;

  ApiEndpoints(String path) {
    this.path = path;
  }

  public String getPath() {
    return path;
  }
}