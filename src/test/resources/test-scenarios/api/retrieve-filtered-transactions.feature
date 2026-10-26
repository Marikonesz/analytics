@api @regression
Feature: Filter and Sort Transactions API Validation

  @TC-API-201
  Scenario: [TC-API-201] Retrieve filtered transactions via API
    When I request transactions via API with query "category=Furniture&sort=rev_desc"
    Then the response status code should be 200
    And all items in API response must have category "Furniture"

  @TC-API-202
  Scenario: [TC-API-202] Retrieve all transactions when no filters are selected
    When I request transactions via API with query ""
    Then the response status code should be 200
    And the response should contain a non-empty list of transactions

  @TC-API-203
  Scenario: [TC-API-203] Retrieve transactions matching multiple simultaneous filters via API
    When I request transactions via API with query "region=Europe&category=Furniture"
    Then the response status code should be 200
    And all items in API response must have region "Europe"
    And all items in API response must have category "Furniture"