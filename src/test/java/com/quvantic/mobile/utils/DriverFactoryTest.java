package com.quvantic.mobile.utils;

import io.appium.java_client.android.options.UiAutomator2Options;
import io.appium.java_client.remote.AutomationName;
import io.appium.java_client.ios.options.XCUITestOptions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import java.time.Duration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/** Checks the capabilities DriverFactory sends to Appium, without opening a session. */
public class DriverFactoryTest {

    @AfterMethod
    public void clearProperties() {
        for (String key : new String[] {"platform", "appium.url", "app.path", "device.name", "timeout"}) {
            System.clearProperty(key);
        }
    }

    @Test
    public void androidOptionsUseUiAutomator2AndConfiguredDevice() {
        System.setProperty("device.name", "emulator-5554");
        System.setProperty("timeout", "20");

        UiAutomator2Options options = DriverFactory.androidOptions();

        assertThat(options.getPlatformName().toString()).isEqualToIgnoringCase("android");
        assertThat(options.getAutomationName()).hasValue(AutomationName.ANDROID_UIAUTOMATOR2);
        assertThat(options.getDeviceName()).hasValue("emulator-5554");
        assertThat(options.doesAutoGrantPermissions()).hasValue(true);
        assertThat(options.getNewCommandTimeout()).hasValue(Duration.ofSeconds(20));
    }

    @Test
    public void appCapabilityIsOnlySetWhenAnAppPathIsGiven() {
        assertThat(DriverFactory.androidOptions().getApp()).isEmpty();
        assertThat(DriverFactory.iosOptions().getApp()).isEmpty();

        System.setProperty("app.path", "/apps/demo.apk");
        assertThat(DriverFactory.androidOptions().getApp()).hasValue("/apps/demo.apk");
    }

    @Test
    public void iosOptionsUseXcuiTestAndConfiguredDevice() {
        System.setProperty("device.name", "iPhone 15");
        System.setProperty("app.path", "/apps/demo.app");

        XCUITestOptions options = DriverFactory.iosOptions();

        assertThat(options.getPlatformName().toString()).isEqualToIgnoringCase("ios");
        assertThat(options.getAutomationName()).hasValue(AutomationName.IOS_XCUI_TEST);
        assertThat(options.getDeviceName()).hasValue("iPhone 15");
        assertThat(options.getApp()).hasValue("/apps/demo.app");
        assertThat(options.getNewCommandTimeout()).hasValue(Duration.ofSeconds(15));
    }

    @Test
    public void malformedServerUrlFailsFastWithClearMessage() {
        System.setProperty("appium.url", "not-a-url");
        assertThatThrownBy(DriverFactory::getDriver)
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Invalid Appium server URL");
    }
}
