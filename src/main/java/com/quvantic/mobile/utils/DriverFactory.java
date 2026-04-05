package com.quvantic.mobile.utils;

import com.quvantic.mobile.config.AppConfig;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.options.XCUITestOptions;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

public final class DriverFactory {

    private static final ThreadLocal<AppiumDriver> DRIVER = new ThreadLocal<>();

    private DriverFactory() {}

    public static AppiumDriver getDriver() {
        if (DRIVER.get() == null) {
            DRIVER.set(createDriver());
        }
        return DRIVER.get();
    }

    public static void quitDriver() {
        AppiumDriver driver = DRIVER.get();
        if (driver != null) {
            driver.quit();
            DRIVER.remove();
        }
    }

    private static AppiumDriver createDriver() {
        try {
            URL serverUrl = new URL(AppConfig.getAppiumUrl());
            String platform = AppConfig.getPlatform().toLowerCase();

            if ("ios".equals(platform)) {
                return createIOSDriver(serverUrl);
            }
            return createAndroidDriver(serverUrl);
        } catch (MalformedURLException e) {
            throw new RuntimeException("Invalid Appium server URL", e);
        }
    }

    private static AndroidDriver createAndroidDriver(URL serverUrl) {
        UiAutomator2Options options = new UiAutomator2Options()
                .setDeviceName(AppConfig.getDeviceName())
                .setAutoGrantPermissions(true)
                .setNewCommandTimeout(Duration.ofSeconds(AppConfig.getTimeout()));

        String appPath = AppConfig.getAppPath();
        if (!appPath.isEmpty()) {
            options.setApp(appPath);
        }

        return new AndroidDriver(serverUrl, options);
    }

    private static IOSDriver createIOSDriver(URL serverUrl) {
        XCUITestOptions options = new XCUITestOptions()
                .setDeviceName(AppConfig.getDeviceName())
                .setNewCommandTimeout(Duration.ofSeconds(AppConfig.getTimeout()));

        String appPath = AppConfig.getAppPath();
        if (!appPath.isEmpty()) {
            options.setApp(appPath);
        }

        return new IOSDriver(serverUrl, options);
    }
}
