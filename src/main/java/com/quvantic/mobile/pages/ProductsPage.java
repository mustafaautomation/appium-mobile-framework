package com.quvantic.mobile.pages;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import org.openqa.selenium.WebElement;

import java.util.List;

public class ProductsPage extends BasePage {

    @AndroidFindBy(accessibility = "test-PRODUCTS")
    @iOSXCUITFindBy(accessibility = "test-PRODUCTS")
    private WebElement productsTitle;

    @AndroidFindBy(xpath = "//android.view.ViewGroup[@content-desc='test-Item']")
    @iOSXCUITFindBy(accessibility = "test-Item")
    private List<WebElement> productItems;

    @AndroidFindBy(accessibility = "test-Cart")
    @iOSXCUITFindBy(accessibility = "test-Cart")
    private WebElement cartIcon;

    @AndroidFindBy(accessibility = "test-Modal Selector Button")
    @iOSXCUITFindBy(accessibility = "test-Modal Selector Button")
    private WebElement sortButton;

    public ProductsPage(AppiumDriver driver) {
        super(driver);
    }

    public boolean isLoaded() {
        return isDisplayed(productsTitle);
    }

    public int getProductCount() {
        return productItems.size();
    }

    public void openCart() {
        waitAndClick(cartIcon);
    }

    public void openSortMenu() {
        waitAndClick(sortButton);
    }

    public String getFirstProductName() {
        if (productItems.isEmpty()) return "";
        return productItems.get(0).getText();
    }

    public void addFirstProductToCart() {
        addProductByIndex(0);
    }

    public void addProductByIndex(int index) {
        if (index < productItems.size()) {
            List<WebElement> addButtons = driver.findElements(
                org.openqa.selenium.By.xpath("//android.view.ViewGroup[contains(@content-desc, 'test-ADD TO CART')]")
            );
            if (index < addButtons.size()) {
                addButtons.get(index).click();
            }
        }
    }

    public int getCartBadgeCount() {
        try {
            WebElement badge = driver.findElement(
                org.openqa.selenium.By.xpath("//android.view.ViewGroup[@content-desc='test-Cart']/android.view.ViewGroup/android.widget.TextView")
            );
            return Integer.parseInt(badge.getText().trim());
        } catch (Exception e) {
            return 0;
        }
    }

    public void tapProductByIndex(int index) {
        if (index < productItems.size()) {
            productItems.get(index).click();
        }
    }

    public void openMenu() {
        try {
            WebElement menu = driver.findElement(
                org.openqa.selenium.By.xpath("//android.view.ViewGroup[@content-desc='test-Menu']")
            );
            menu.click();
        } catch (Exception e) {
            // Menu might have different locator on iOS
        }
    }
}
