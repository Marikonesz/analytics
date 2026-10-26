package com.analytics.ui.enums;

public enum FilterType {
  REGION("region"),
  CATEGORY("category"),
  DATE_RANGE("date range"),
  SORT("sort by");

  private final String value;

  FilterType(String value) {
    this.value = value;
  }

  public static FilterType fromString(String text) {
    for (FilterType ft : FilterType.values()) {
      if (ft.value.equalsIgnoreCase(text)) {
        return ft;
      }
    }
    throw new IllegalArgumentException("Unknown filter type: " + text);
  }
}