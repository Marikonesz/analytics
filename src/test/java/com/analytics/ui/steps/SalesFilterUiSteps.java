package com.analytics.ui.steps;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import com.analytics.core.ConfigManager;
import com.analytics.ui.enums.CategoryOption;
import com.analytics.ui.enums.DateRangeOption;
import com.analytics.ui.enums.FilterType;
import com.analytics.ui.enums.RegionOption;
import com.analytics.ui.enums.SortOption;
import com.analytics.ui.pages.SalesAnalyticsPage;
import com.microsoft.playwright.Locator;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class SalesFilterUiSteps {

  private final SalesAnalyticsPage analyticsPage;

  public SalesFilterUiSteps(SalesAnalyticsPage analyticsPage) {
    this.analyticsPage = analyticsPage;
  }

  @Given("I open the Sales Analytics app")
  public void iOpenTheSalesAnalyticsApp() {
    String url = ConfigManager.get("base.url");
    analyticsPage.navigate(url);
  }

  @When("I select \"{dateRangeOption}\" for {string} filter")
  public void iSelectDateRangeFilter(DateRangeOption option, String filterTypeName) {
    FilterType filterType = FilterType.fromString(filterTypeName);
    analyticsPage.selectFilterOption(filterType, option.getLabel());
    assertThat(analyticsPage.getSelectByFilterType(filterType)).hasValue(option.getValue());
  }

  @When("I select \"{regionOption}\" for {string} filter")
  public void iSelectRegionFilter(RegionOption option, String filterTypeName) {
    FilterType filterType = FilterType.fromString(filterTypeName);
    analyticsPage.selectFilterOption(filterType, option.getLabel());
    assertThat(analyticsPage.getSelectByFilterType(filterType)).hasValue(option.getValue());
  }

  @When("I select \"{categoryOption}\" for {string} filter")
  public void iSelectCategoryFilter(CategoryOption option, String filterTypeName) {
    FilterType filterType = FilterType.fromString(filterTypeName);
    analyticsPage.selectFilterOption(filterType, option.getLabel());
    assertThat(analyticsPage.getSelectByFilterType(filterType)).hasValue(option.getValue());
  }

  @When("I select \"{sortOption}\" for {string} filter")
  public void iSelectSortFilter(SortOption option, String filterTypeName) {
    FilterType filterType = FilterType.fromString(filterTypeName);
    analyticsPage.selectFilterOption(filterType, option.getLabel());
    assertThat(analyticsPage.getSelectByFilterType(filterType)).hasValue(option.getValue());
  }

  @When("I click apply filter")
  public void iClickApplyFilter() {
    analyticsPage.clickApply();
  }

  @When("I click clear filters")
  public void iClickClearFilters() {
    analyticsPage.clickClear();
  }

  @Then("all rows in the table should display {string} in the {string} column")
  public void allRowsInTheTableShouldDisplayValueInColumn(String expectedValue, String columnName) {
    Locator columnCells = analyticsPage.getCellsByColumnName(columnName);

    assertThat(columnCells.first()).isVisible();
    for (int i = 0; i < columnCells.count(); i++) {
      assertThat(columnCells.nth(i)).hasText(expectedValue);
    }
  }

  @Then("the message {string} should be displayed")
  public void theMessageShouldBeDisplayed(String expectedMessage) {
    assertThat(analyticsPage.getNoDataMessage()).isVisible();
    assertThat(analyticsPage.getNoDataMessage()).hasText(expectedMessage);
  }

  @Then("all filter controls should be unselected")
  public void allFilterControlsShouldBeUnselected() {
    assertThat(analyticsPage.getSelectByFilterType(FilterType.DATE_RANGE)).hasValue("none");
    assertThat(analyticsPage.getSelectByFilterType(FilterType.REGION)).hasValue("none");
    assertThat(analyticsPage.getSelectByFilterType(FilterType.CATEGORY)).hasValue("none");
  }

  @Then("the sort control should still be {string}")
  public void theSortControlShouldStillBe(String expectedSortOption) {
    String expectedHtmlValue = SortOption.fromString(expectedSortOption).getValue();
    assertThat(analyticsPage.getSelectByFilterType(FilterType.SORT)).hasValue(expectedHtmlValue);
  }
}