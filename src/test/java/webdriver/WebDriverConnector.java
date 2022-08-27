package webdriver;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
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
            DesiredCapabilities capabilities = new DesiredCapabilities();
            switch (System.getProperty(browser)) {
                case "firefox": {
                    WebDriverManager.firefoxdriver().setup();
                    driver = new FirefoxDriver();
                    capabilities.setCapability("browserName", "firefox");
                    capabilities.setCapability("maxInstances", 5);
                    capabilities.setCapability("seleniumProtocol", "Webdriver");
                }
                case "msedge": {
                    WebDriverManager.edgedriver().setup();
                    driver = new EdgeDriver();
                    capabilities.setCapability("browserName", "msedge");
                    capabilities.setCapability("maxInstances", 1);
                    capabilities.setCapability("seleniumProtocol", "Webdriver");
                    break;
                }
                case "chrome": {WebDriverManager.chromedriver().driverVersion("99.0.4844.51").setup();
                    driver = new ChromeDriver(chromeOptions);
                    WebDriverManager.chromedriver().setup();
                    driver = new ChromeDriver();
                    capabilities.setCapability("browserName", "chrome");
                    capabilities.setCapability("maxInstances", 5);
                    capabilities.setCapability("seleniumProtocol", "Webdriver");
                }
                case "ie": {
                    WebDriverManager.iedriver().setup();
                    driver = new InternetExplorerDriver();
                    capabilities.setCapability("browserName", "ie");
                    capabilities.setCapability("maxInstances", 1);
                    capabilities.setCapability("seleniumProtocol", "Webdriver");
                }
                case "safari": {
                    WebDriverManager.safaridriver().setup();
                    driver = new SafariDriver();
                    capabilities.setCapability("browserName", "safari");
                    capabilities.setCapability("maxInstances", 1);
                    capabilities.setCapability("seleniumProtocol", "Webdriver");
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
