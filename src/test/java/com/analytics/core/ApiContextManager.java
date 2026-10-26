package com.analytics.core;

import com.microsoft.playwright.APIRequest;
import com.microsoft.playwright.APIRequestContext;
import com.microsoft.playwright.Playwright;

public class ApiContextManager {

  private static final ThreadLocal<Playwright> playwrightThread = new ThreadLocal<>();
  private static final ThreadLocal<APIRequestContext> apiContextThread = new ThreadLocal<>();

  public static void initApiContext() {
    Playwright playwright = Playwright.create();
    playwrightThread.set(playwright);

    APIRequestContext apiContext = playwright.request().newContext(
        new APIRequest.NewContextOptions().setBaseURL(ConfigManager.get("base.url"))
    );
    apiContextThread.set(apiContext);
  }

  public static APIRequestContext getApiContext() {
    return apiContextThread.get();
  }

  public static void closeApiContext() {
    if (apiContextThread.get() != null) {
      apiContextThread.get().dispose();
    }
    if (playwrightThread.get() != null) {
      playwrightThread.get().close();
    }
    apiContextThread.remove();
    playwrightThread.remove();
  }
}