package com.qa.automation.utils;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CommonUtils {

    public static String captureScreenshot(WebDriver driver, String testName) {
        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String fileName = testName + "_" + timestamp + ".png";
        String filePath = ConfigReader.getScreenshotPath() + "/" + fileName;

        try {
            File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            File dest = new File(filePath);
            dest.getParentFile().mkdirs();
            FileUtils.copyFile(src, dest);
            return filePath;
        } catch (IOException e) {
            return "Screenshot capture failed: " + e.getMessage();
        }
    }

    public static String generateMaxLengthEmail() {
        String domain = "@example.com";
        int localPartLength = 254 - domain.length();
        return "a".repeat(localPartLength) + domain;
    }
}
