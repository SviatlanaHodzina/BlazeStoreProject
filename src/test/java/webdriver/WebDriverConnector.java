package webdriver;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.safari.SafariDriver;
import org.testng.annotations.Parameters;

import java.net.MalformedURLException;

public class WebDriverConnector {
    private static WebDriver driver;

    @Parameters("browser")
    public static synchronized WebDriver getDriver(String browser) throws MalformedURLException {
        if (driver == null) {
            switch (System.getProperty(browser)) {
                case "firefox": {
                    WebDriverManager.firefoxdriver().setup();
                    driver = new FirefoxDriver();
                    break;
                }
                case "msedge": {
                    WebDriverManager.edgedriver().driverVersion("104.0.1293.54").setup();
                    driver = new EdgeDriver();
                    break;
                }
                case "chrome": {
                    WebDriverManager.chromedriver().driverVersion("104.0.5112.79").setup();
                    driver = new ChromeDriver();
                    break;
                }
                case "ie": {
                    WebDriverManager.iedriver().setup();
                    driver = new InternetExplorerDriver();
                    break;
                }
            }
            driver.manage().window().maximize();
        }
        return driver;
    }

    public static void closeDriver() {
        driver.quit();
        driver = null;
    }
}