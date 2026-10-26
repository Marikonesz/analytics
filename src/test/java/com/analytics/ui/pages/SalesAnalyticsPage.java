package com.analytics.ui.pages;

import com.analytics.core.DriverFactory;
import com.analytics.ui.enums.CategoryOption;
import com.analytics.ui.enums.DateRangeOption;
import com.analytics.ui.enums.FilterType;
import com.analytics.ui.enums.RegionOption;
import com.analytics.ui.enums.SortOption;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import com.microsoft.playwright.options.SelectOption;

public class SalesAnalyticsPage {

  private final Page page;
  private final Locator dateRangeSelect;
  private final Locator regionSelect;
  private final Locator categorySelect;
  private final Locator sortSelect;
  private final Locator applyButton;
  private final Locator clearButton;
  private final Locator tableRows;
  private final Locator noDataMessage;

  public SalesAnalyticsPage() {
    this.page = DriverFactory.getPage();

    this.dateRangeSelect = page.getByLabel("Date range");
    this.regionSelect = page.getByLabel("Region");
    this.categorySelect = page.getByLabel("Category");
    this.sortSelect = page.getByLabel("Sort by");

    this.applyButton = page.getByRole(AriaRole.BUTTON,
        new Page.GetByRoleOptions().setName("Apply"));
    this.clearButton = page.getByRole(AriaRole.BUTTON,
        new Page.GetByRoleOptions().setName("Clear"));

    this.tableRows = page.locator("#rows tr");
    this.noDataMessage = page.getByText("No data available");
  }

  public void navigate(String url) {
    page.navigate(url);
  }

  public void selectFilterOption(FilterType filterType, String optionText) {
    String label = getOptionLabel(filterType, optionText);
    switch (filterType) {
      case DATE_RANGE -> dateRangeSelect.selectOption(new SelectOption().setLabel(label));
      case REGION -> regionSelect.selectOption(new SelectOption().setLabel(label));
      case CATEGORY -> categorySelect.selectOption(new SelectOption().setLabel(label));
      case SORT -> sortSelect.selectOption(new SelectOption().setLabel(label));
    }
  }

  public String getOptionLabel(FilterType filterType, String optionText) {
    return switch (filterType) {
      case DATE_RANGE -> DateRangeOption.fromString(optionText).getLabel();
      case REGION -> RegionOption.fromString(optionText).getLabel();
      case CATEGORY -> CategoryOption.fromString(optionText).getLabel();
      case SORT -> SortOption.fromString(optionText).getLabel();
    };
  }

  public String getOptionValue(FilterType filterType, String optionText) {
    return switch (filterType) {
      case DATE_RANGE -> DateRangeOption.fromString(optionText).getValue();
      case REGION -> RegionOption.fromString(optionText).getValue();
      case CATEGORY -> CategoryOption.fromString(optionText).getValue();
      case SORT -> SortOption.fromString(optionText).getValue();
    };
  }

  public Locator getSelectByFilterType(FilterType filterType) {
    return switch (filterType) {
      case DATE_RANGE -> dateRangeSelect;
      case REGION -> regionSelect;
      case CATEGORY -> categorySelect;
      case SORT -> sortSelect;
    };
  }

  public void clickApply() {
    applyButton.click();
  }

  public void clickClear() {
    clearButton.click();
  }

  public Locator getNoDataMessage() {
    return noDataMessage;
  }

  public Locator getCellsByColumnName(String columnName) {
    int columnIndex = switch (columnName.toLowerCase()) {
      case "date" -> 1;
      case "region" -> 2;
      case "category" -> 3;
      case "revenue", "amount" -> 4;
      default -> throw new IllegalArgumentException("Unknown column: " + columnName);
    };
    return tableRows.locator("td:nth-child(" + columnIndex + ")");
  }
}