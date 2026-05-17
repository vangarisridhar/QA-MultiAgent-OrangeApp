package com.qa.automation.pages;

import com.qa.automation.utils.ConfigReader;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage extends BasePage {

    private final By usernameInput = By.name("username");
    private final By passwordInput = By.name("password");
    private final By loginButton   = By.cssSelector("button[type='submit']");
    private final By errorMessage  = By.cssSelector(".oxd-alert-content-text, .oxd-input-group .oxd-text--tag");
    private final By usernameError = By.cssSelector(".oxd-input-group__message, .oxd-alert-content-text");
    private final By passwordError = By.cssSelector(".oxd-input-group__message, .oxd-alert-content-text");

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    public void navigateTo() {
        String base = ConfigReader.getAppUrl().replaceAll("/+$", "");
        String path = ConfigReader.get("app.login.path");
        String url = base.contains(path) ? base : base + path;
        driver.navigate().to(url);
        waitUtils.waitForPageLoad();
    }

    public void enterUsername(String username) {
        waitForElement(usernameInput).clear();
        waitForElement(usernameInput).sendKeys(username);
    }

    public void enterPassword(String password) {
        waitForElement(passwordInput).clear();
        waitForElement(passwordInput).sendKeys(password);
    }

    public void clickLoginButton() {
        waitForClickable(loginButton).click();
    }

    public void login(String username, String password) {
        enterUsername(username);
        enterPassword(password);
        clickLoginButton();
    }

    public boolean isErrorDisplayed() {
        return isElementPresent(errorMessage);
    }

    public String getErrorMessage() {
        return isErrorDisplayed() ? waitForElement(errorMessage).getText() : "";
    }

    public boolean isUsernameValidationErrorDisplayed() {
        try {
            waitForElement(usernameError);
            return true;
        } catch (org.openqa.selenium.TimeoutException e) {
            return false;
        }
    }

    public boolean isPasswordValidationErrorDisplayed() {
        try {
            waitForElement(passwordError);
            return true;
        } catch (org.openqa.selenium.TimeoutException e) {
            return false;
        }
    }

    public boolean isPasswordMasked() {
        String type = waitForElement(passwordInput).getAttribute("type");
        return "password".equalsIgnoreCase(type);
    }

    public boolean isOnLoginPage() {
        return driver.getCurrentUrl().contains(ConfigReader.get("app.login.path"))
                || isElementPresent(loginButton);
    }

}
