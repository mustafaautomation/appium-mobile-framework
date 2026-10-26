package com.quvantic.mobile.config;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class AppConfigTest {

    private static final String[] KEYS = {"platform", "appium.url", "app.path", "device.name", "timeout"};

    @AfterMethod
    public void clearProperties() {
        for (String key : KEYS) {
            System.clearProperty(key);
        }
    }

    @Test
    public void defaultsTargetLocalAndroidEmulator() {
        assertThat(AppConfig.getPlatform()).isEqualTo("android");
        assertThat(AppConfig.getAppiumUrl()).isEqualTo("http://localhost:4723");
        assertThat(AppConfig.getAppPath()).isEmpty();
        assertThat(AppConfig.getDeviceName()).isEqualTo("Pixel_7_API_34");
        assertThat(AppConfig.getTimeout()).isEqualTo(15);
    }

    @Test
    public void systemPropertiesOverrideDefaults() {
        System.setProperty("platform", "ios");
        System.setProperty("appium.url", "http://grid.local:4444");
        System.setProperty("app.path", "/apps/demo.app");
        System.setProperty("device.name", "iPhone 15");
        System.setProperty("timeout", "30");

        assertThat(AppConfig.getPlatform()).isEqualTo("ios");
        assertThat(AppConfig.getAppiumUrl()).isEqualTo("http://grid.local:4444");
        assertThat(AppConfig.getAppPath()).isEqualTo("/apps/demo.app");
        assertThat(AppConfig.getDeviceName()).isEqualTo("iPhone 15");
        assertThat(AppConfig.getTimeout()).isEqualTo(30);
    }

    @Test
    public void nonNumericTimeoutIsRejected() {
        System.setProperty("timeout", "fast");
        assertThatThrownBy(AppConfig::getTimeout).isInstanceOf(NumberFormatException.class);
    }
}
