package com.qa.automation.steps;

import com.qa.automation.pages.DashboardPage;
import com.qa.automation.pages.LoginPage;
import com.qa.automation.utils.DriverManager;
import io.cucumber.java.en.*;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

public class LogoutSteps {

    private final WebDriver driver = DriverManager.getDriver();
    private final DashboardPage dashboardPage = new DashboardPage(driver);
    private final LoginPage loginPage = new LoginPage(driver);

    @When("the user clicks the logout button")
    public void theUserClicksTheLogoutButton() {
        dashboardPage.clickLogout();
    }

    @Then("the session should be terminated")
    public void theSessionShouldBeTerminated() {
        Assert.assertFalse(dashboardPage.isOnDashboard(),
                "Session was not terminated — user is still on dashboard");
    }

    @Then("the user should be redirected to the login page")
    public void theUserShouldBeRedirectedToLoginPage() {
        Assert.assertTrue(loginPage.isOnLoginPage(),
                "Expected login page but URL was: " + driver.getCurrentUrl());
    }

    @Given("the user has navigated to the dashboard")
    public void theUserHasNavigatedToTheDashboard() {
        Assert.assertTrue(dashboardPage.isOnDashboard(),
                "User is not on the dashboard");
    }

    @When("the user presses the browser back button")
    public void theUserPressesTheBrowserBackButton() {
        dashboardPage.pressBackButton();
    }

    @Then("the user should not be able to access the dashboard")
    public void theUserShouldNotBeAbleToAccessDashboard() {
        // SPA may show cached page on back; refresh forces server-side auth check
        driver.navigate().refresh();
        dashboardPage.waitForLoad();
        Assert.assertFalse(dashboardPage.isOnDashboard(),
                "User was able to access dashboard after logout via back button");
    }

    @Given("the user notes the dashboard URL")
    public void theUserNotesTheDashboardUrl() {
        // URL is stored in the page object for later use in direct navigation
        String url = dashboardPage.getDashboardUrl();
        Assert.assertNotNull(url, "Could not read dashboard URL");
        // Store in system property for cross-step use
        System.setProperty("saved.dashboard.url", url);
    }

    @When("the user navigates directly to the dashboard URL")
    public void theUserNavigatesDirectlyToDashboardUrl() {
        String savedUrl = System.getProperty("saved.dashboard.url",
                System.getProperty("app.url", "") + "/dashboard");
        dashboardPage.navigateTo(savedUrl);
    }

    @Then("the dashboard content should not be visible")
    public void theDashboardContentShouldNotBeVisible() {
        Assert.assertFalse(dashboardPage.isLogoutButtonPresent(),
                "Dashboard content is visible after logout — unauthorized access");
    }
}
