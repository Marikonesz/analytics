package com.analytics.api.steps;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.microsoft.playwright.APIResponse;
import io.cucumber.java.en.Then;

public abstract class BaseApiSteps {

  protected APIResponse rawResponse;

  @Then("the response status code should be {int}")
  public void theResponseStatusCodeShouldBe(int expectedStatus) {
    assertNotNull(rawResponse,
        "Response is null. Ensure a request was made before checking the status.");
    assertEquals(expectedStatus, rawResponse.status());
  }
}