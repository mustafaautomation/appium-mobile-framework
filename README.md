# Appium Mobile Framework

[![CI](https://github.com/mustafaautomation/appium-mobile-framework/actions/workflows/ci.yml/badge.svg)](https://github.com/mustafaautomation/appium-mobile-framework/actions)
[![License: MIT](https://img.shields.io/badge/License-MIT-blue.svg)](LICENSE)
[![Java](https://img.shields.io/badge/Java-17-ED8B00.svg?logo=openjdk&logoColor=white)](https://openjdk.org)
[![Appium](https://img.shields.io/badge/Appium-2.0-662D91.svg)](https://appium.io)

Appium 2.0 mobile testing framework with Page Object Model, built as a portfolio project. Supports Android (UiAutomator2) and iOS (XCUITest). ThreadLocal driver for parallel execution, gesture helpers, and Allure reporting.

---

## Test Coverage

**Device tests** (need an Appium server and a running emulator, simulator or real device; they target the Sauce Labs demo app):

| Suite | Tests | Platform |
|-------|-------|----------|
| Login | 4 | Android / iOS |
| Products | 3 | Android / iOS |
| Cart | 4 | Android / iOS |
| Navigation | 4 | Android / iOS |
| **Total** | **15** | |

**Unit tests** (no device or Appium server; this is what CI runs on every push):

| Class | Tests | What it checks |
|-------|-------|----------------|
| AppConfigTest | 3 | Defaults, `-D` overrides, invalid timeout |
| DriverFactoryTest | 4 | Android/iOS capabilities sent to Appium, app path handling, bad server URL |
| GestureHelperTest | 6 | W3C pointer actions for swipe, tap and long press (mocked driver) |
| **Total** | **13** | |

CI does not start an emulator, so the device suite is not run in CI.

---

## Quick Start

```bash
# Unit tests only (no device needed) - the default
mvn test

# Device tests - prerequisites:
# 1. Install Appium: npm i -g appium
# 2. Install drivers: appium driver install uiautomator2
# 3. Start Android emulator or connect device

# Start Appium server
appium &

# Run device tests (pass the demo app with -Dapp.path=... if it isn't installed)
mvn test -Dsuite=testng-device.xml -Dplatform=android -Ddevice.name=Pixel_7_API_34

# iOS
mvn test -Dsuite=testng-device.xml -Dplatform=ios -Ddevice.name="iPhone 15"
```

---

## Architecture

```
┌─────────────────────────────────┐
│          Test Classes            │
│  Login │ Products │ Cart │ Nav   │
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
├── src/test/java/com/quvantic/mobile/
│   ├── config/AppConfigTest.java    # unit
│   ├── utils/                       # unit: DriverFactoryTest, GestureHelperTest
│   └── tests/                       # device: Login, Products, Cart, Navigation
├── src/test/resources/
│   ├── testng-unit.xml              # default suite (CI)
│   └── testng-device.xml            # device suite
├── pom.xml
└── .github/workflows/ci.yml
```

---

## Stack

| Tool | Purpose |
|------|---------|
| Appium 2.0 (java-client 10.1) | Mobile automation |
| UiAutomator2 / XCUITest | Platform drivers |
| TestNG 7.10 | Test runner |
| Allure 2.29 | Reporting |
| AssertJ | Fluent assertions |
| Mockito | Mocked driver in unit tests |
| Maven | Build management |

---

## License

MIT

---

Built by [Quvantic](https://quvantic.com)
