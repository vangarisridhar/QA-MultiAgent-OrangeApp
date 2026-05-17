package com.qa.automation.runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.DataProvider;

@CucumberOptions(
        features = "src/test/resources/features",
        glue = {
                "com.qa.automation.steps",
                "com.qa.automation.hooks"
        },
        tags = "not @wip",
        plugin = {
                "pretty",
                "html:target/reports/regression-report.html",
                "json:target/reports/regression-report.json",
                "junit:target/reports/regression-report.xml"
        },
        monochrome = true
)
public class RegressionRunner extends AbstractTestNGCucumberTests {

    @Override
    @DataProvider(parallel = false)
    public Object[][] scenarios() {
        return super.scenarios();
    }
}
