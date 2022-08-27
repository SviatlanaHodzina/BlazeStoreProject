package utils;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import webdriver.WebDriverConnector;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class TestListener implements ITestListener {
    private final Logger log = LogManager.getRootLogger();

    public void onTestStart(ITestResult iTestResult) {
    }

    public void onTestSuccess(ITestResult iTestResult) {
    }

    public void onTestFailure(ITestResult iTestResult) {
    }

    public void onTestSkipped(ITestResult iTestResult) {
    }

    public void onTestFailedButWithinSuccessPercentage(ITestResult iTestResult) {
    }

    public void onStart(ITestContext iTestContext) {
    }

    public void onFinish(ITestContext iTestContext) {
    }

    private void saveScreenShot() throws MalformedURLException {
        File screenCapture = ((TakesScreenshot) WebDriverConnector
                .getDriver("browser"))
                .getScreenshotAs(OutputType.FILE);
        try {
            FileUtils.copyFile(screenCapture,
                    new File(".//target/screenshots/" + getCurrentTimeAsString() + ".png"));
        } catch (IOException e) {
            log.error("Failed to save screenshot: " + e.getLocalizedMessage());
        }
    }

    private String getCurrentTimeAsString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("uuuu-MM-dd_HH-mm-ss");
        return ZonedDateTime.now().format(formatter);
    }
}