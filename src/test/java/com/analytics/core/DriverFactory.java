package com.analytics.core;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;

public class DriverFactory {

  private static final ThreadLocal<Playwright> playwrightThread = new ThreadLocal<>();
  private static final ThreadLocal<Browser> browserThread = new ThreadLocal<>();
  private static final ThreadLocal<BrowserContext> contextThread = new ThreadLocal<>();
  private static final ThreadLocal<Page> pageThread = new ThreadLocal<>();

  public static void initDriver() {
    Playwright playwright = Playwright.create();
    playwrightThread.set(playwright);

    String browserType = ConfigManager.get("browser").toLowerCase();
    boolean headless = Boolean.parseBoolean(ConfigManager.get("headless"));

    BrowserType.LaunchOptions launchOptions = new BrowserType.LaunchOptions()
        .setHeadless(headless);

    Browser browser;
    switch (browserType) {
      case "firefox":
        browser = playwright.firefox()
            .launch(new BrowserType.LaunchOptions().setHeadless(headless));
        break;
      case "webkit":
        browser = playwright.webkit().launch(new BrowserType.LaunchOptions().setHeadless(headless));
        break;
      default:
        browser = playwright.chromium().launch(launchOptions);
    }
    browserThread.set(browser);

    BrowserContext context = browser.newContext();
    contextThread.set(context);
    pageThread.set(context.newPage());
  }

  public static Page getPage() {
    if (pageThread.get() == null) {
      initDriver();
    }
    return pageThread.get();
  }

  public static void quitDriver() {
    if (contextThread.get() != null) {
      contextThread.get().close();
    }
    if (browserThread.get() != null) {
      browserThread.get().close();
    }
    if (playwrightThread.get() != null) {
      playwrightThread.get().close();
    }

    pageThread.remove();
    contextThread.remove();
    browserThread.remove();
    playwrightThread.remove();
  }
}