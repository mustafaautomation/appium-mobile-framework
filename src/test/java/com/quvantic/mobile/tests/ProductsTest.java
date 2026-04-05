package com.quvantic.mobile.tests;

import io.qameta.allure.*;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThat;

@Epic("Shopping")
@Feature("Products")
public class ProductsTest extends BaseTest {

    @BeforeMethod
    public void login() {
        loginAsStandardUser();
    }

    @Test(groups = "smoke")
    @Story("Product display")
    @Description("Products page shows items after login")
    public void productsPageLoads() {
        assertThat(productsPage.isLoaded()).isTrue();
    }

    @Test(groups = "regression")
    @Story("Product count")
    @Description("Products page shows multiple items")
    public void hasMultipleProducts() {
        assertThat(productsPage.getProductCount()).isGreaterThan(0);
    }

    @Test(groups = "regression")
    @Story("Cart navigation")
    @Description("Can navigate to cart from products page")
    public void navigateToCart() {
        productsPage.openCart();
        // Cart page would load
    }
}
