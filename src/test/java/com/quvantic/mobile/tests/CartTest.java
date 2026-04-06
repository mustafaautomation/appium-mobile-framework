package com.quvantic.mobile.tests;

import io.qameta.allure.*;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThat;

@Epic("Shopping")
@Feature("Mobile Cart")
public class CartTest extends BaseTest {

    @BeforeMethod
    public void login() {
        loginAsStandardUser();
    }

    @Test(groups = "smoke")
    @Story("Add to cart")
    @Description("Adding a product updates cart badge on mobile")
    public void addProductToCart() {
        productsPage.addFirstProductToCart();
        assertThat(productsPage.getCartBadgeCount()).isGreaterThan(0);
    }

    @Test(groups = "regression")
    @Story("Multiple products")
    @Description("Cart badge reflects multiple added products")
    public void addMultipleProducts() {
        productsPage.addFirstProductToCart();
        productsPage.addProductByIndex(1);
        assertThat(productsPage.getCartBadgeCount()).isEqualTo(2);
    }

    @Test(groups = "regression")
    @Story("Cart navigation")
    @Description("Navigate to cart shows cart page")
    public void openCartPage() {
        productsPage.addFirstProductToCart();
        productsPage.openCart();
        // Cart should be accessible
        assertThat(driver.getPageSource()).containsIgnoringCase("cart");
    }

    @Test(groups = "regression")
    @Story("Empty cart")
    @Description("Cart badge is not visible when cart is empty")
    public void emptyCartHasNoBadge() {
        assertThat(productsPage.getCartBadgeCount()).isEqualTo(0);
    }
}
