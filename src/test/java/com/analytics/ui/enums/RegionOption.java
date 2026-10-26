package com.analytics.ui.enums;

public enum RegionOption {
  NOT_SELECTED("Not selected", "none"),
  EUROPE("Europe", "Europe"),
  NORTH_AMERICA("North America", "North America"),
  ASIA("Asia", "Asia");

  private final String label;
  private final String value;

  RegionOption(String label, String value) {
    this.label = label;
    this.value = value;
  }

  public String getLabel() {
    return label;
  }

  public String getValue() {
    return value;
  }

  public static RegionOption fromString(String text) {
    for (RegionOption option : values()) {
      if (option.label.equalsIgnoreCase(text)) {
        return option;
      }
    }
    throw new IllegalArgumentException("Unknown region option: " + text);
  }
}