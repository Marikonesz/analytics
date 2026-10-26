package com.analytics.ui.enums;

public enum SortOption {
  DATE_OLDEST_NEWEST("Date: Oldest → Newest", "date_asc"),
  DATE_NEWEST_OLDEST("Date: Newest → Oldest", "date_desc"),
  REVENUE_LOW_HIGH("Revenue: Low → High", "rev_asc"),
  REVENUE_HIGH_LOW("Revenue: High → Low", "rev_desc");

  private final String label;
  private final String value;

  SortOption(String label, String value) {
    this.label = label;
    this.value = value;
  }

  public String getLabel() {
    return label;
  }

  public String getValue() {
    return value;
  }

  public static SortOption fromString(String text) {
    for (SortOption option : values()) {
      if (option.label.equalsIgnoreCase(text)) {
        return option;
      }
    }
    throw new IllegalArgumentException("Unknown sort option: " + text);
  }
}