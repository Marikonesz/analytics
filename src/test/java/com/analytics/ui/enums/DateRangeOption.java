package com.analytics.ui.enums;

public enum DateRangeOption {
  NOT_SELECTED("Not selected", "none"),
  LAST_7_DAYS("Last 7 days", "7"),
  LAST_30_DAYS("Last 30 days", "30"),
  LAST_90_DAYS("Last 90 days", "90");

  private final String label;
  private final String value;

  DateRangeOption(String label, String value) {
    this.label = label;
    this.value = value;
  }

  public String getLabel() {
    return label;
  }

  public String getValue() {
    return value;
  }

  public static DateRangeOption fromString(String text) {
    for (DateRangeOption option : values()) {
      if (option.label.equalsIgnoreCase(text)) {
        return option;
      }
    }
    throw new IllegalArgumentException("Unknown date range option: " + text);
  }
}