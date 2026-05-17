# KAN-1 — Login-Logout Automation Framework

Selenium 4 / Cucumber 7 / TestNG 7 automation for the **Login-Logout ecommerce Application**.

## Prerequisites

- Java 11+
- Maven 3.8+
- Chrome or Firefox browser

## Quick Start

```bash
# Smoke tests (positive + @smoke tagged)
mvn clean test -Dapp.url=https://your-app.com

# Full regression
mvn clean test -Dapp.url=https://your-app.com -Dcucumber.filter.tags="not @wip"

# Security scenarios only
mvn clean test -Dapp.url=https://your-app.com -Dcucumber.filter.tags="@security"

# Headless
mvn clean test -Dapp.url=https://your-app.com -Dbrowser.headless=true
```

## Test Coverage

| Tag          | Scenarios | Description                        |
|--------------|-----------|------------------------------------|
| `@smoke`     | 2         | Happy-path login + logout          |
| `@positive`  | 5         | All positive login/logout flows    |
| `@negative`  | 7         | Invalid credentials, empty fields  |
| `@edge`      | 4         | Max-length, special chars, SQLi, XSS |
| `@validation`| 5         | Masking, format, cookie/token checks |
| `@security`  | 6         | Security-focused scenarios         |

## Reports

After execution, reports are in `target/reports/`:
- `cucumber-report.html` — smoke run HTML report
- `regression-report.html` — regression HTML report
- `cucumber-report.json` — JSON for CI/CD integration

## Project Structure

```
src/test/
├── resources/
│   ├── features/
│   │   ├── login.feature   — 18 login scenarios
│   │   └── logout.feature  — 3 logout scenarios
│   └── config/
│       └── config.properties
└── java/com/qa/automation/
    ├── pages/      BasePage, LoginPage, DashboardPage
    ├── steps/      LoginSteps, LogoutSteps, CommonSteps
    ├── utils/      DriverManager, WaitUtils, ConfigReader, CommonUtils
    ├── hooks/      Hooks (setup/teardown + screenshot on failure)
    └── runners/    TestRunner (smoke), RegressionRunner (full)
```
