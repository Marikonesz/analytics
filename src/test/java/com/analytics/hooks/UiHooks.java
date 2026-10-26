package com.analytics.hooks;

import com.analytics.core.DriverFactory;
import com.microsoft.playwright.Tracing;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.qameta.allure.Allure;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class UiHooks {

  @Before(value = "@ui", order = 1)
  public void setUpUi(Scenario scenario) {
    DriverFactory.initDriver();
    if (DriverFactory.getPage() != null && DriverFactory.getPage().context() != null) {
      DriverFactory.getPage().context().tracing().start(new Tracing.StartOptions()
          .setScreenshots(true)
          .setSnapshots(true)
          .setSources(true));
    }
  }

  @After(value = "@ui", order = 100)
  public void tearDownUi(Scenario scenario) {
    try {
      if (DriverFactory.getPage() != null && DriverFactory.getPage().context() != null) {
        var context = DriverFactory.getPage().context();
        if (scenario.isFailed()) {
          String safeName = scenario.getName().replaceAll("[^a-zA-Z0-9._-]", "_");
          Path tracePath = Paths.get("target/allure-results/trace-" + safeName + ".zip");

          context.tracing().stop(new Tracing.StopOptions().setPath(tracePath));

          if (Files.exists(tracePath)) {
            Allure.addAttachment(
                "Playwright Trace (Failed)",
                "application/zip",
                Files.newInputStream(tracePath),
                ".zip"
            );
          }
        } else {
          context.tracing().stop(new Tracing.StopOptions());
        }
      }
    } catch (Exception e) {
      System.err.println("Failed to stop tracing or attach trace to Allure: " + e.getMessage());
    } finally {
      DriverFactory.quitDriver();
    }
  }
}