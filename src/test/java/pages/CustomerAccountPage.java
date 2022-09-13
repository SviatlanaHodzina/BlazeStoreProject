package pages;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.MalformedURLException;
import java.time.Duration;

import static org.openqa.selenium.By.xpath;
import static pages.HomePage.*;
import static pages.LogInModalWindowPage.LOG_IN_PAGE_BUTTON_LOG_IN_ELEMENT_XPATH;

//This page is not implemented by developer yet
//here should be implemented other methods when other features are developed for this page
public class CustomerAccountPage extends AbstractPage {
    public final Logger logger = LogManager.getRootLogger();
    // The following 2 commented Strings for the future implied to be developed features
    private final static String CUSTOMER_ACCOUNT_PAGE_CONTENT_ELEMENT_XPATH = "input here XPATH";
    private final static String CUSTOMER_ACCOUNT_PAGE_TITLE_ELEMENT_XPATH = "input here XPATH";
    final static String CUSTOMER_ACCOUNT_PAGE_USERNAME_LINK_IN_NAVIGATING_MENU_ELEMENT_XPATH = "//a[@id,'nameofuser']";
    final static String CUSTOMER_ACCOUNT_PAGE_USERNAME_LINK_DISPLAYED_IN_NAVIGATING_MENU_ELEMENT_XPATH = "//a[contains(@id,'nameofuser') and contains (@style, 'display: block;')]";
    final static String CUSTOMER_ACCOUNT_PAGE_USERNAME_LINK_NOT_DISPLAYED_IN_NAVIGATING_MENU_ELEMENT_XPATH = "//a[contains(@id,'nameofuser') and contains (@style, 'display:none')]";
    final static String CUSTOMER_ACCOUNT_PAGE_LOG_OUT_LINK_IN_NAVIGATING_MENU_ELEMENT_XPATH = "//a[@onclick='logOut()']";

    public CustomerAccountPage() throws MalformedURLException {
        super();
        PageFactory.initElements(this.driver, this);
    }

    @Override
    public CustomerAccountPage openPage() {
        new WebDriverWait(driver, Duration.ofSeconds(WAIT_TIMEOUT_SECONDS))
                .until(ExpectedConditions.visibilityOfElementLocated
                        (By.xpath(CUSTOMER_ACCOUNT_PAGE_CONTENT_ELEMENT_XPATH)));
        logger.info("You've navigated to 'Log in' modal window");
        return this;
    }

    public HomePage logOut() throws MalformedURLException {
        new WebDriverWait(driver, Duration.ofSeconds(WAIT_TIMEOUT_SECONDS))
                .until(ExpectedConditions.visibilityOfElementLocated
                        (By.xpath(CUSTOMER_ACCOUNT_PAGE_LOG_OUT_LINK_IN_NAVIGATING_MENU_ELEMENT_XPATH))).click();
        new WebDriverWait(driver, Duration.ofSeconds(WAIT_TIMEOUT_SECONDS))
                .until(ExpectedConditions.visibilityOfElementLocated
                        (By.xpath(LOG_IN_PAGE_BUTTON_LOG_IN_ELEMENT_XPATH)));
        return new HomePage();
    }

    public String getCustomerAccountPageTitle() {
        return driver.findElement(xpath(CUSTOMER_ACCOUNT_PAGE_TITLE_ELEMENT_XPATH))
                .getAttribute("textContent");
    }

    public String getLinkNameInPlaceOfSignUpLink() {
        new WebDriverWait(driver, Duration.ofSeconds(WAIT_TIMEOUT_SECONDS))
                .until(ExpectedConditions.visibilityOfElementLocated
                        (By.xpath(CUSTOMER_ACCOUNT_PAGE_USERNAME_LINK_DISPLAYED_IN_NAVIGATING_MENU_ELEMENT_XPATH)));
        return driver.findElement(xpath(CUSTOMER_ACCOUNT_PAGE_USERNAME_LINK_DISPLAYED_IN_NAVIGATING_MENU_ELEMENT_XPATH))
                .getAttribute("textContent");
    }

    public boolean usernameLinkInPlaceOfSignUpLinkIsDisplayed() {
        return driver.findElement(xpath(CUSTOMER_ACCOUNT_PAGE_USERNAME_LINK_DISPLAYED_IN_NAVIGATING_MENU_ELEMENT_XPATH))
                .isDisplayed();
    }

    public boolean usernameLinkInPlaceOfSignUpLinkIsNotDisplayed() {
        return driver.findElement(xpath(SIGN_UP_LINK_IN_NAVIGATING_MENU_ELEMENT_XPATH)).isDisplayed();
    }

    public String getChangedColorOfLinkNameInPlaceOfSignUpLink() {
        new WebDriverWait(driver, Duration.ofSeconds(WAIT_TIMEOUT_SECONDS))
                .until(ExpectedConditions.visibilityOfElementLocated
                        (By.xpath(CUSTOMER_ACCOUNT_PAGE_LOG_OUT_LINK_IN_NAVIGATING_MENU_ELEMENT_XPATH)));
        return driver.findElement(xpath(CUSTOMER_ACCOUNT_PAGE_LOG_OUT_LINK_IN_NAVIGATING_MENU_ELEMENT_XPATH))
                .getCssValue("color");
    }

    public String getLinkNameInPlaceOfLogInLink() {
        return driver.findElement(xpath(CUSTOMER_ACCOUNT_PAGE_LOG_OUT_LINK_IN_NAVIGATING_MENU_ELEMENT_XPATH))
                .getAttribute("textContent");
    }

    public String getChangedColorOfLinkNameInPlaceOfLogInLink() {
        return driver.findElement(xpath(CUSTOMER_ACCOUNT_PAGE_USERNAME_LINK_DISPLAYED_IN_NAVIGATING_MENU_ELEMENT_XPATH))
                .getCssValue("color");
    }

    public boolean SignUpLinkIsDisplayed() {
        return driver.findElement(xpath(SIGN_UP_LINK_IN_NAVIGATING_MENU_ELEMENT_XPATH))
                .isDisplayed();
    }

    public boolean LogInLinkIsDisplayed() {
        return driver.findElement(xpath(LOG_IN_LINK_IN_NAVIGATING_MENU_ELEMENT_XPATH))
                .isDisplayed();
    }
}
