# Appium Mobile Framework

[![CI](https://github.com/mustafaautomation/appium-mobile-framework/actions/workflows/ci.yml/badge.svg)](https://github.com/mustafaautomation/appium-mobile-framework/actions)
[![License: MIT](https://img.shields.io/badge/License-MIT-blue.svg)](LICENSE)
[![Java](https://img.shields.io/badge/Java-17-ED8B00.svg?logo=openjdk&logoColor=white)](https://openjdk.org)
[![Appium](https://img.shields.io/badge/Appium-2.0-662D91.svg)](https://appium.io)

Enterprise Appium 2.0 mobile testing framework with Page Object Model. Supports Android (UiAutomator2) and iOS (XCUITest). ThreadLocal driver for parallel execution, gesture helpers, and Allure reporting.

---

## Test Coverage

| Suite | Tests | Platform |
|-------|-------|----------|
| Login | 4 | Android / iOS |
| Products | 3 | Android / iOS |
| **Total** | **7** | |

---

## Quick Start

```bash
# Prerequisites
# 1. Install Appium: npm i -g appium
# 2. Install drivers: appium driver install uiautomator2
# 3. Start Android emulator or connect device

# Start Appium server
appium &

# Run tests
mvn test -Dplatform=android -Ddevice.name=Pixel_7_API_34

# iOS
mvn test -Dplatform=ios -Ddevice.name="iPhone 15"
```

---

## Architecture

```
┌─────────────────────────────────┐
│          Test Classes            │
│  LoginTest │ ProductsTest        │
├─────────────────────────────────┤
│          BaseTest                │
│  setUp/tearDown + loginHelper    │
├─────────────────────────────────┤
│          Page Objects            │
│  LoginPage │ ProductsPage        │
│  BasePage (wait/click/type)      │
├─────────────────────────────────┤
│          Utilities               │
│  DriverFactory (ThreadLocal)     │
│  GestureHelper (swipe/tap/press) │
│  AppConfig (system properties)   │
├─────────────────────────────────┤
│  Appium 2.0 │ TestNG │ Allure   │
└─────────────────────────────────┘
```

---

## Key Patterns

### Cross-Platform Page Objects
```java
@AndroidFindBy(accessibility = "test-Username")
@iOSXCUITFindBy(accessibility = "test-Username")
private WebElement usernameField;
```
One page object works on both Android and iOS.

### Gesture Helpers
```java
GestureHelper.swipeUp(driver);
GestureHelper.swipeLeft(driver);
GestureHelper.tap(driver, x, y);
GestureHelper.longPress(driver, x, y, Duration.ofSeconds(2));
```

### Thread-Safe Driver
```java
// Each test gets its own driver instance
AppiumDriver driver = DriverFactory.getDriver();
```

---

## Project Structure

```
appium-mobile-framework/
├── src/main/java/com/quvantic/mobile/
│   ├── config/AppConfig.java        # Platform, URL, device config
│   ├── utils/
│   │   ├── DriverFactory.java       # ThreadLocal Android/iOS driver
│   │   └── GestureHelper.java       # Swipe, tap, long press
│   └── pages/
│       ├── BasePage.java            # Wait/click/type helpers
│       ├── LoginPage.java           # @AndroidFindBy + @iOSXCUITFindBy
│       └── ProductsPage.java
├── src/test/java/com/quvantic/mobile/tests/
│   ├── BaseTest.java
│   ├── LoginTest.java               # 4 tests
│   └── ProductsTest.java            # 3 tests
├── src/test/resources/testng.xml
├── pom.xml
└── .github/workflows/ci.yml
```

---

## Stack

| Tool | Purpose |
|------|---------|
| Appium 2.0 (java-client 9.3) | Mobile automation |
| UiAutomator2 / XCUITest | Platform drivers |
| TestNG 7.10 | Test runner |
| Allure 2.29 | Reporting |
| AssertJ | Fluent assertions |
| Maven | Build management |

---

## License

MIT

---

Built by [Quvantic](https://quvantic.com)
