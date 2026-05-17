package com.qa.automation.hooks;

import com.qa.automation.utils.CommonUtils;
import com.qa.automation.utils.DriverManager;
import io.cucumber.java.After;
import io.cucumber.java.AfterStep;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.openqa.selenium.WebDriver;

public class Hooks {

    @Before(order = 0)
    public void setUp(Scenario scenario) {
        DriverManager.getDriver();
    }

    @After(order = 0)
    public void tearDown(Scenario scenario) {
        WebDriver driver = DriverManager.getDriver();
        if (scenario.isFailed() && Boolean.parseBoolean(
                System.getProperty("screenshot.on.failure", "true"))) {
            String screenshotPath = CommonUtils.captureScreenshot(driver, scenario.getName());
            scenario.log("Screenshot saved: " + screenshotPath);
        }
        DriverManager.quitDriver();
    }

    @AfterStep
    public void afterStep(Scenario scenario) {
        if (scenario.isFailed()) {
            WebDriver driver = DriverManager.getDriver();
            CommonUtils.captureScreenshot(driver, "step_failure_" + scenario.getName());
        }
    }
}
