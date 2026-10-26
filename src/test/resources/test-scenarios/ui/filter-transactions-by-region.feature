@ui @regression
Feature: Filter and Sort Transactions UI Validation

  @TC-UI-101
  Scenario: [TC-UI-101] Filter transactions by Region and Category in the UI
    Given I open the Sales Analytics app
    When I select "Europe" for "Region" filter
    And I select "Furniture" for "Category" filter
    And I click apply filter
    Then all rows in the table should display "Europe" in the "Region" column
    And all rows in the table should display "Furniture" in the "Category" column

  @TC-UI-102
  Scenario: [TC-UI-102] Display No data available message for empty filter results
    Given I open the Sales Analytics app
    When I select "Last 7 days" for "Date range" filter
    And I select "Asia" for "Region" filter
    And I select "Furniture" for "Category" filter
    And I click apply filter
    Then the message "No data available" should be displayed

  @TC-UI-103
  Scenario: [TC-UI-103] Clear removes all filters but preserves sorting
    Given I open the Sales Analytics app
    When I select "North America" for "Region" filter
    And I select "Revenue: Low → High" for "Sort by" filter
    And I click apply filter
    And I click clear filters
    Then all filter controls should be unselected
    And the sort control should still be "Revenue: Low → High"