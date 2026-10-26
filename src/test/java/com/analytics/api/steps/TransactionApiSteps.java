package com.analytics.api.steps;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.analytics.api.clients.TransactionsApiClient;
import com.analytics.api.models.TransactionResponse;
import com.analytics.core.TestContext;
import com.analytics.core.enums.ContextKey;
import com.microsoft.playwright.APIResponse;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class TransactionApiSteps {

  private final TransactionsApiClient apiClient;
  private final TestContext testContext;

  public TransactionApiSteps(TransactionsApiClient apiClient, TestContext testContext) {
    this.apiClient = apiClient;
    this.testContext = testContext;
  }

  @When("I request transactions via API with query {string}")
  public void iRequestTransactionsViaApiWithQuery(String query) {
    APIResponse rawResponse = apiClient.getTransactionsRaw(query);
    TransactionResponse transactionResponse = apiClient.getTransactions(query);

    testContext.set(ContextKey.RAW_RESPONSE, rawResponse);
    testContext.set(ContextKey.API_RESPONSE_OBJECT, transactionResponse);
  }

  @Then("the response status code should be {int}")
  public void theResponseStatusCodeShouldBe(int expectedStatus) {
    APIResponse rawResponse = testContext.get(ContextKey.RAW_RESPONSE, APIResponse.class);
    assertNotNull(rawResponse, "Raw response is null");
    assertEquals(expectedStatus, rawResponse.status());
  }

  @Then("all items in API response must have category {string}")
  public void allItemsInApiResponseMustHaveCategory(String expectedCategory) {
    TransactionResponse transactionResponse = testContext.get(ContextKey.API_RESPONSE_OBJECT,
        TransactionResponse.class);
    assertNotNull(transactionResponse.getData());
    transactionResponse.getData().stream()
        .forEach(tx -> assertEquals(expectedCategory, tx.getCategory()));
  }

  @Then("all items in API response must have region {string}")
  public void allItemsInApiResponseMustHaveRegion(String expectedRegion) {
    TransactionResponse transactionResponse = testContext.get(ContextKey.API_RESPONSE_OBJECT,
        TransactionResponse.class);
    assertNotNull(transactionResponse.getData());
    transactionResponse.getData().stream()
        .forEach(tx -> assertEquals(expectedRegion, tx.getRegion()));
  }

  @Then("the response should contain a non-empty list of transactions")
  public void theResponseShouldContainANonEmptyListOfTransactions() {
    TransactionResponse transactionResponse = testContext.get(ContextKey.API_RESPONSE_OBJECT,
        TransactionResponse.class);
    assertNotNull(transactionResponse.getData());
    assertFalse(transactionResponse.getData().isEmpty(), "Transaction list should not be empty");
  }
}