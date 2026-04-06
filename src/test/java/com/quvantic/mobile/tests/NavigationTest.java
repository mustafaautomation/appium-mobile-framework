package com.quvantic.mobile.tests;

import io.qameta.allure.*;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThat;

@Epic("Navigation")
@Feature("Mobile Navigation")
public class NavigationTest extends BaseTest {

    @BeforeMethod
    public void login() {
        loginAsStandardUser();
    }

    @Test(groups = "smoke")
    @Story("Products page")
    @Description("Products page is the default after login")
    public void productsIsDefaultPage() {
        assertThat(productsPage.isLoaded()).isTrue();
        assertThat(productsPage.getProductCount()).isGreaterThan(0);
    }

    @Test(groups = "regression")
    @Story("Product detail")
    @Description("Tapping a product opens detail view")
    public void tapProductOpensDetail() {
        productsPage.tapProductByIndex(0);
        assertThat(driver.getPageSource()).containsIgnoringCase("back");
    }

    @Test(groups = "regression")
    @Story("Menu access")
    @Description("Menu is accessible from products page")
    public void menuIsAccessible() {
        productsPage.openMenu();
        assertThat(driver.getPageSource()).containsIgnoringCase("menu");
    }

    @Test(groups = "regression")
    @Story("App state")
    @Description("App maintains state after navigation")
    public void appMaintainsState() {
        productsPage.addFirstProductToCart();
        int cartCount = productsPage.getCartBadgeCount();
        productsPage.openCart();
        // Navigate back — cart should still have items
        driver.navigate().back();
        assertThat(productsPage.getCartBadgeCount()).isEqualTo(cartCount);
    }
}
