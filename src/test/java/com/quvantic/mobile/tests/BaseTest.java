package com.quvantic.mobile.tests;

import com.quvantic.mobile.pages.LoginPage;
import com.quvantic.mobile.pages.ProductsPage;
import com.quvantic.mobile.utils.DriverFactory;
import io.appium.java_client.AppiumDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

public abstract class BaseTest {

    protected AppiumDriver driver;
    protected LoginPage loginPage;
    protected ProductsPage productsPage;

    @BeforeMethod
    public void setUp() {
        driver = DriverFactory.getDriver();
        loginPage = new LoginPage(driver);
        productsPage = new ProductsPage(driver);
    }

    @AfterMethod
    public void tearDown() {
        DriverFactory.quitDriver();
    }

    protected void loginAsStandardUser() {
        loginPage.login("standard_user", "secret_sauce");
    }
}
