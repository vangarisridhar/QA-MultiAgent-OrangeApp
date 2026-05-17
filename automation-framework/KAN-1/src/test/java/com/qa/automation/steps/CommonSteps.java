package com.qa.automation.steps;

import com.qa.automation.pages.DashboardPage;
import com.qa.automation.pages.LoginPage;
import com.qa.automation.utils.ConfigReader;
import com.qa.automation.utils.DriverManager;
import io.cucumber.java.en.*;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

public class CommonSteps {

    private final WebDriver driver = DriverManager.getDriver();
    private final LoginPage loginPage = new LoginPage(driver);
    private final DashboardPage dashboardPage = new DashboardPage(driver);

    @Given("the user is already logged in")
    public void theUserIsAlreadyLoggedIn() {
        loginPage.navigateTo();
        loginPage.login(ConfigReader.getValidUsername(), ConfigReader.getValidPassword());
        Assert.assertTrue(dashboardPage.isOnDashboard(),
                "Pre-condition failed: could not log in with valid credentials");
    }

    @Given("the user has logged out")
    public void theUserHasLoggedOut() {
        if (dashboardPage.isOnDashboard()) {
            dashboardPage.clickLogout();
        }
        Assert.assertTrue(loginPage.isOnLoginPage(),
                "Pre-condition failed: user is not on login page after logout");
    }

    @When("the user logs out")
    public void theUserLogsOut() {
        dashboardPage.clickLogout();
    }
}
