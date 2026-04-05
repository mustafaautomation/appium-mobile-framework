package com.quvantic.mobile.config;

public final class AppConfig {

    private AppConfig() {}

    public static String getPlatform() {
        return System.getProperty("platform", "android");
    }

    public static String getAppiumUrl() {
        return System.getProperty("appium.url", "http://localhost:4723");
    }

    public static String getAppPath() {
        return System.getProperty("app.path", "");
    }

    public static String getDeviceName() {
        return System.getProperty("device.name", "Pixel_7_API_34");
    }

    public static int getTimeout() {
        return Integer.parseInt(System.getProperty("timeout", "15"));
    }
}
