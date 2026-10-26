package com.analytics.core;

import com.analytics.ui.enums.CategoryOption;
import com.analytics.ui.enums.DateRangeOption;
import com.analytics.ui.enums.RegionOption;
import com.analytics.ui.enums.SortOption;
import io.cucumber.java.ParameterType;

public class CucumberParameterTypes {

  @ParameterType("Europe|North America|Asia")
  public RegionOption regionOption(String value) {
    return RegionOption.fromString(value);
  }

  @ParameterType("Furniture|Appliances|Lighting")
  public CategoryOption categoryOption(String value) {
    return CategoryOption.fromString(value);
  }

  @ParameterType("Not selected|Last 7 days|Last 30 days|Last 90 days")
  public DateRangeOption dateRangeOption(String value) {
    return DateRangeOption.fromString(value);
  }

  @ParameterType("Date: Oldest → Newest|Date: Newest → Oldest|Revenue: Low → High|Revenue: High → Low")
  public SortOption sortOption(String value) {
    return SortOption.fromString(value);
  }
}