package com.qa.automation.pages;

import com.qa.automation.utils.ConfigReader;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class DashboardPage extends BasePage {

    private final By logoutButton  = By.cssSelector("a[href$='/auth/logout']");
    private final By welcomeMessage = By.cssSelector(".oxd-userdropdown-name");
    private final By userMenu      = By.cssSelector(".oxd-userdropdown-tab");

    public DashboardPage(WebDriver driver) {
        super(driver);
    }

    public boolean isOnDashboard() {
        try {
            waitUtils.waitForUrlToContain("/dashboard");
            return true;
        } catch (org.openqa.selenium.TimeoutException e) {
            return isElementPresent(logoutButton);
        }
    }

    public void waitForLoad() {
        waitUtils.waitForPageLoad();
    }

    public boolean isLogoutButtonPresent() {
        return isElementPresent(logoutButton);
    }

    public void clickLogout() {
        // Try user menu first, then direct logout button
        if (isElementPresent(userMenu)) {
            waitForClickable(userMenu).click();
        }
        waitForClickable(logoutButton).click();
    }

    public String getDashboardUrl() {
        return driver.getCurrentUrl();
    }

    public void navigateTo(String url) {
        driver.navigate().to(url);
        waitUtils.waitForPageLoad();
    }

    public void pressBackButton() {
        driver.navigate().back();
        waitUtils.waitForPageLoad();
    }
}
