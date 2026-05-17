package com.qa.automation.pages;

import com.qa.automation.utils.WaitUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public abstract class BasePage {

    protected final WebDriver driver;
    protected final WaitUtils waitUtils;

    protected BasePage(WebDriver driver) {
        this.driver = driver;
        this.waitUtils = new WaitUtils(driver);
    }

    protected WebElement waitForElement(By locator) {
        return waitUtils.waitForVisible(locator);
    }

    protected WebElement waitForClickable(By locator) {
        return waitUtils.waitForClickable(locator);
    }

    protected boolean isElementPresent(By locator) {
        return waitUtils.isElementPresent(locator);
    }

    protected void jsClick(By locator) {
        WebElement el = waitForElement(locator);
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", el);
    }

    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }
}
