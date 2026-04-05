package com.quvantic.mobile.tests;

import io.qameta.allure.*;
import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThat;

@Epic("Authentication")
@Feature("Mobile Login")
public class LoginTest extends BaseTest {

    @Test(groups = "smoke")
    @Story("Valid login")
    @Description("Standard user can log in on mobile")
    public void validLogin() {
        loginAsStandardUser();
        assertThat(productsPage.isLoaded()).isTrue();
    }

    @Test(groups = "smoke")
    @Story("Invalid login")
    @Description("Locked user sees error on mobile")
    public void lockedOutUser() {
        loginPage.login("locked_out_user", "secret_sauce");
        assertThat(loginPage.getErrorMessage()).contains("locked out");
    }

    @Test(groups = "regression")
    @Story("Empty credentials")
    @Description("Empty username shows error")
    public void emptyUsername() {
        loginPage.login("", "secret_sauce");
        assertThat(loginPage.getErrorMessage()).contains("Username is required");
    }

    @Test(groups = "regression")
    @Story("Empty password")
    @Description("Empty password shows error")
    public void emptyPassword() {
        loginPage.login("standard_user", "");
        assertThat(loginPage.getErrorMessage()).contains("Password is required");
    }
}
