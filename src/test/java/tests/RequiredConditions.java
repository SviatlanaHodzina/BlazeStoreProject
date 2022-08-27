package tests;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.Listeners;
import org.testng.annotations.Parameters;
import utils.TestListener;
import webdriver.WebDriverConnector;

import java.net.MalformedURLException;

@Listeners({TestListener.class})
public class RequiredConditions {
    public static WebDriver driver;

    @Parameters("browser")
    public void setUp(String browser) throws MalformedURLException {
        driver = WebDriverConnector.getDriver("browser");
    }
//
//    public void stopDriver() {
//        WebDriverConnector.closeDriver();
//    }
}