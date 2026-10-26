# Sales Analytics – Test Automation Framework

Automated UI and API testing framework for the Sales Analytics application, built using Java 17,
Microsoft Playwright, and Cucumber BDD.

## Tech Stack & Dependencies

* Language: Java 17
* UI Automation: Microsoft Playwright (Java)
* API Testing: HTTP Client wrappers / RestAssured
* BDD Framework: Cucumber 7 with PicoContainer (Dependency Injection)
* Test Runner: JUnit Platform Engine
* Reporting: Allure Framework
* Build Tool: Maven

---

## Architecture & Core Principles

1. **Type-Safe Enums:** Filter options encapsulate both UI labels and technical HTML/API attributes,
   avoiding hardcoded strings and switches inside pages.
2. **Cucumber Custom Parameter Types:** Enforces strict input validation at the Gherkin feature-file
   level to prevent typos and runtime errors.
3. **User-Facing Locators:** Adheres to Playwright best practices using semantic locators (
   getByLabel,getByRole,getByText) instead of brittle CSS/XPath selectors.
4. **State Management & Dependency Injection:** Utilizes PicoContainer alongside TestContext and
   ApiContextManager for clean state sharing across hooks and step definitions.

---

## Detailed Project Structure

```text
src/test/java/com/analytics/
├── api/                        # API automation layer
│   ├── clients/                # HTTP/REST clients (BaseApiClient, TransactionsApiClient)
│   ├── enums/                  # API constants & endpoints (ApiEndpoints)
│   ├── models/                 # DTOs and response mapping classes
│   └── steps/                  # API Cucumber step definitions (BaseApiSteps, TransactionApiSteps)
├── hooks/                      # Test lifecycle management (ApiHooks, UiHooks)
├── runners/                    # JUnit execution suites (TestRunner)
├── core/                    # Core framework utilities & context
│   ├── enums/                  # Support-level enums (ContextKey)
│   ├── ApiContextManager.java  # API state & response holder
│   ├── ConfigManager.java      # Configuration & properties loader
│   ├── CucumberParameterTypes.java # Custom Gherkin parameter converters
│   ├── DriverFactory.java      # Playwright browser & page lifecycle management
│   └── TestContext.java        # Shared test context state
└── ui/                         # UI automation layer (Page Object Model)
├── enums/                  # Domain options (Category, DateRange, FilterType, Region, Sort)
├── pages/                  # Playwright Page Objects (SalesAnalyticsPage)
└── steps/                  # UI Cucumber step definitions (SalesFilterUiSteps)

src/test/resources/
├── test-scenarios/                 # Gherkin .feature specifications
│   ├── api/                        # API test scenarios (retrieve-filtered-transactions.feature)
│   └── ui/                         # UI test scenarios (filter-transactions-by-region.feature)
├── allure.properties               # Allure reporting configuration
├── config.properties               # Base URLs and execution properties
└── junit-platform.properties       # JUnit platform execution parameters
```

---

## How to Run Tests

You can run tests via Maven in your terminal or directly from IntelliJ IDEA.

### 1. Running via Maven (Terminal)

* Run the full test suite:
  mvn clean test

* Run tests by tag (e.g., UI, API, or regression):
  `mvn test -Dcucumber.filter.tags="@ui"`
  `mvn test -Dcucumber.filter.tags="@api"`
  `mvn test -Dcucumber.filter.tags="@regression"`

* Run a specific .feature file:
  `mvn test -Dcucumber.features="src/test/resources/test-scenarios/ui/filter-transactions-by-region.feature"`

### 2. Managing Browsers & Parallel Threads (Concurrency)

* **Select Browser:** You can specify the target browser via system properties:
    * `mvn test -Dbrowser=chromium` (default)
    * `mvn test -Dbrowser=firefox`
    * `mvn test -Dbrowser=webkit`
    * **Configure Parallel Threads:** Control the number of parallel execution threads:
        * Via command line: `mvn test -Djunit.jupiter.execution.parallel.config.fixed.parallelism=4`
        * Or configure it permanently in `src/test/resources/junit-platform.properties`:

            * `junit.jupiter.execution.parallel.enabled=true`

          * `junit.jupiter.execution.parallel.config.strategy=fixed`

          * `junit.jupiter.execution.parallel.config.fixed.parallelism=4`


Configuration

The framework uses a clean separation of concerns for its configuration files:

config.properties manages environment URLs, browser choices, and execution modes.

junit-platform.properties controls the JUnit 5 test engine, parallel execution threads, and global
plugins including Allure reporting and failure tracking.

Execution and Running Tests

1. Run All Tests

Execute the full test suite via Maven using the command:

```text
mvn clean test
```

2. Parallel Execution and Thread Tuning

Parallel execution is enabled by default via the JUnit 5 engine. To change the number of concurrent
execution threads, modify the fixed parallelism property inside your `junit-platform.properties`
file.

3. Re-running Failed Tests

The framework automatically tracks failed scenarios using Cucumber's built-in rerun plugin. During a
test run, paths to any failed scenarios are automatically logged into target/failed_scenarios.txt.
To re-run only the failed scenarios, execute:

```text 
mvn test -Dcucumber.features="@target/failed_scenarios.txt"
```

Reporting

To generate and view the Allure test report after execution, ensure Allure CLI is installed on your
system and run the command:

```text
allure serve target/allure-results
```