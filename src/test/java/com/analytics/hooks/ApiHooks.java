package com.analytics.hooks;

import com.analytics.core.ApiContextManager;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.qameta.allure.Allure;

public class ApiHooks {

  @Before(value = "@api", order = 1)
  public void setUpApi() {
    ApiContextManager.initApiContext();
  }

  @After(value = "@api", order = 100)
  public void tearDownApi(Scenario scenario) {
    try {
      if (scenario.isFailed()) {
        String failureReport = String.format(
            "Scenario Name: %s%nStatus: FAILED%nTags: %s",
            scenario.getName(),
            String.join(", ", scenario.getSourceTagNames())
        );

        Allure.addAttachment(
            "API Failure Details",
            "text/plain",
            failureReport
        );
      }
    } finally {
      ApiContextManager.closeApiContext();
    }
  }
}