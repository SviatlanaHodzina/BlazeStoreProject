package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import webdriver.WebDriverConnector;

import java.net.MalformedURLException;

public abstract class AbstractPage {
    protected WebDriver driver;
    public final int WAIT_TIMEOUT_SECONDS = 50;

    protected abstract AbstractPage openPage();

    protected AbstractPage() throws MalformedURLException {
        driver = WebDriverConnector.getDriver("browser");
        PageFactory.initElements(driver, this);
    }
}