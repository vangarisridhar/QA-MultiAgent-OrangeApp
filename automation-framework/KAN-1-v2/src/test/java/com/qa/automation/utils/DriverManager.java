package com.qa.automation.utils;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import java.time.Duration;

public class DriverManager {

    private static final ThreadLocal<WebDriver> driverHolder = new ThreadLocal<>();

    public static WebDriver getDriver() {
        if (driverHolder.get() == null) {
            initDriver();
        }
        return driverHolder.get();
    }

    private static void initDriver() {
        String browser = ConfigReader.getBrowser().toLowerCase();
        WebDriver driver;

        switch (browser) {
            case "firefox":
                WebDriverManager.firefoxdriver().setup();
                FirefoxOptions ffOptions = new FirefoxOptions();
                if (ConfigReader.isHeadless()) ffOptions.addArguments("--headless");
                driver = new FirefoxDriver(ffOptions);
                break;
            case "chrome":
            default:
                WebDriverManager.chromedriver().setup();
                ChromeOptions options = new ChromeOptions();
                if (ConfigReader.isHeadless()) options.addArguments("--headless=new");
                options.addArguments("--no-sandbox", "--disable-dev-shm-usage");
                driver = new ChromeDriver(options);
                break;
        }

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(ConfigReader.getImplicitTimeout()));
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));
        if (Boolean.parseBoolean(ConfigReader.get("browser.maximize"))) {
            driver.manage().window().maximize();
        }
        driverHolder.set(driver);
    }

    public static void quitDriver() {
        if (driverHolder.get() != null) {
            driverHolder.get().quit();
            driverHolder.remove();
        }
    }
}
