package com.analytics.ui.enums;

public enum CategoryOption {
  NOT_SELECTED("Not selected", "none"),
  FURNITURE("Furniture", "Furniture"),
  APPLIANCES("Appliances", "Appliances"),
  LIGHTING("Lighting", "Lighting");

  private final String label;
  private final String value;

  CategoryOption(String label, String value) {
    this.label = label;
    this.value = value;
  }

  public String getLabel() {
    return label;
  }

  public String getValue() {
    return value;
  }

  public static CategoryOption fromString(String text) {
    for (CategoryOption option : values()) {
      if (option.label.equalsIgnoreCase(text)) {
        return option;
      }
    }
    throw new IllegalArgumentException("Unknown category option: " + text);
  }
}