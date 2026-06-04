package com.qa.automation.steps;

import com.qa.automation.pages.DashboardPage;
import com.qa.automation.pages.LoginPage;
import com.qa.automation.utils.CommonUtils;
import com.qa.automation.utils.DriverManager;
import io.cucumber.java.en.*;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

public class LoginSteps {

    private final WebDriver driver = DriverManager.getDriver();
    private final LoginPage loginPage = new LoginPage(driver);
    private final DashboardPage dashboardPage = new DashboardPage(driver);

    @Given("the user is on the login page")
    public void theUserIsOnTheLoginPage() {
        loginPage.navigateTo();
        Assert.assertTrue(loginPage.isOnLoginPage(), "Expected to be on the login page");
    }

    @When("the user enters username {string}")
    public void theUserEntersUsername(String username) {
        loginPage.enterUsername(username);
    }

    @When("the user enters password {string}")
    public void theUserEntersPassword(String password) {
        loginPage.enterPassword(password);
    }

    @When("the user clicks the login button")
    public void theUserClicksTheLoginButton() {
        loginPage.clickLoginButton();
    }

    @When("the user clicks the login button without entering credentials")
    public void theUserClicksLoginWithoutCredentials() {
        loginPage.clickLoginButton();
    }

    @When("the user enters a username of maximum allowed length")
    public void theUserEntersMaxLengthUsername() {
        loginPage.enterUsername(CommonUtils.generateMaxLengthEmail());
    }

    @Then("the user should be redirected to the dashboard")
    public void theUserShouldBeRedirectedToTheDashboard() {
        Assert.assertTrue(dashboardPage.isOnDashboard(),
                "Expected to be on dashboard but URL was: " + driver.getCurrentUrl());
    }

    @Then("no error message should be displayed")
    public void noErrorMessageShouldBeDisplayed() {
        Assert.assertFalse(loginPage.isErrorDisplayed(), "Unexpected error message shown");
    }

    @Then("an error message should be displayed")
    public void anErrorMessageShouldBeDisplayed() {
        Assert.assertTrue(loginPage.isErrorDisplayed(),
                "Expected error message was not displayed");
    }

    @Then("the user should remain on the login page")
    public void theUserShouldRemainOnTheLoginPage() {
        Assert.assertTrue(loginPage.isOnLoginPage(),
                "Expected to remain on login page but URL was: " + driver.getCurrentUrl());
    }

    @Then("no session should be created")
    public void noSessionShouldBeCreated() {
        Assert.assertFalse(dashboardPage.isOnDashboard(), "Session was unexpectedly created");
    }

    @Then("a validation error should be shown for the username field")
    public void aValidationErrorShouldBeShownForUsernameField() {
        Assert.assertTrue(loginPage.isUsernameValidationErrorDisplayed(),
                "Expected username validation error was not shown");
    }

    @Then("a validation error should be shown for the password field")
    public void aValidationErrorShouldBeShownForPasswordField() {
        Assert.assertTrue(loginPage.isPasswordValidationErrorDisplayed(),
                "Expected password validation error was not shown");
    }

    @Then("the application should handle the max-length input without crashing")
    public void theApplicationShouldHandleMaxLengthInput() {
        String url = driver.getCurrentUrl();
        Assert.assertNotNull(url, "Application crashed — no URL found");
        Assert.assertFalse(url.isEmpty(), "Application returned empty URL after max-length input");
    }

    @Then("no SQL error should be exposed")
    public void noSqlErrorShouldBeExposed() {
        String pageSource = driver.getPageSource().toLowerCase();
        Assert.assertFalse(pageSource.contains("sql") && pageSource.contains("error"),
                "SQL error details were exposed in the page");
        Assert.assertFalse(pageSource.contains("syntax error"),
                "SQL syntax error was exposed");
    }

    @Then("the XSS payload should not be executed")
    public void xssPayloadShouldNotBeExecuted() {
        // If XSS ran, an alert would be present — no alert means sanitized
        try {
            driver.switchTo().alert().dismiss();
            Assert.fail("XSS payload was executed — alert was present");
        } catch (org.openqa.selenium.NoAlertPresentException e) {
            // Expected — no alert means XSS was blocked
        }
    }

    @Then("the password field should mask the input")
    public void thePasswordFieldShouldMaskInput() {
        Assert.assertTrue(loginPage.isPasswordMasked(),
                "Password field does not mask input (type != 'password')");
    }

    @Then("the error message should be generic and not field-specific")
    public void errorMessageShouldBeGeneric() {
        String msg = loginPage.getErrorMessage().toLowerCase();
        Assert.assertTrue(loginPage.isErrorDisplayed(), "No error message displayed");
        Assert.assertFalse(msg.contains("username not found") || msg.contains("user does not exist"),
                "Error message reveals username existence: " + msg);
        Assert.assertFalse(msg.contains("incorrect password") || msg.contains("wrong password"),
                "Error message reveals which field is wrong: " + msg);
    }

    @Then("the session cookie should be cleared")
    public void theSessionCookieShouldBeCleared() {
        boolean sessionCookiePresent = driver.manage().getCookies().stream()
                .anyMatch(c -> c.getName().toLowerCase().contains("session")
                        || c.getName().toLowerCase().contains("auth")
                        || c.getName().toLowerCase().contains("token"));
        Assert.assertFalse(sessionCookiePresent, "Session/auth cookie still present after logout");
    }

    @Then("the auth token should be invalidated")
    public void theAuthTokenShouldBeInvalidated() {
        // Verify no auth-related cookies remain
        boolean authCookiePresent = driver.manage().getCookies().stream()
                .anyMatch(c -> c.getName().toLowerCase().contains("token")
                        || c.getName().toLowerCase().contains("jwt")
                        || c.getName().toLowerCase().contains("auth"));
        Assert.assertFalse(authCookiePresent, "Auth token cookie still present after logout");
    }
}
