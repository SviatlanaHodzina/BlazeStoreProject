package pages;

import customer.Customer;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.net.MalformedURLException;
import java.time.Duration;

import static org.openqa.selenium.By.xpath;
import static pages.CustomerAccountPage.CUSTOMER_ACCOUNT_PAGE_USERNAME_LINK_DISPLAYED_IN_NAVIGATING_MENU_ELEMENT_XPATH;
import static pages.HomePage.*;

public class LogInModalWindowPage extends AbstractPage {
    public final Logger logger = LogManager.getRootLogger();

    private final static String LOG_IN_PAGE_MODAL_WINDOW_SHOW_ELEMENT_XPATH = "//div[contains(@id,'logInModal') and contains(@class,'modal fade show')]";
    private final static String LOG_IN_PAGE_USERNAME_PLACEHOLDER_ELEMENT_XPATH = "//input[@id='loginusername']";
    private final static String LOG_IN_PAGE_PASSWORD_PLACEHOLDER_ELEMENT_XPATH = "//input[@id='loginpassword']";
    final static String LOG_IN_PAGE_BUTTON_LOG_IN_MODAL_ELEMENT_XPATH = "//div[@id='logInModal']//button[@onclick='logIn()']";
    final static String LOG_IN_PAGE_BUTTON_LOG_IN_ELEMENT_XPATH = "//a[@id='login2']";
    private final static String LOG_IN_PAGE_BUTTON_CLOSE_FOOTER_ELEMENT_XPATH = "//div[@id='logInModal']//button[@class='btn btn-secondary']";
    private final static String LOG_IN_PAGE_BUTTON_CLOSE_X_ONTOP_ELEMENT_XPATH = "//div[@id='logInModal']//button[@class='close']";

    public LogInModalWindowPage() throws MalformedURLException {
        super();
        PageFactory.initElements(this.driver, this);
    }

    @Override
    public LogInModalWindowPage openPage() {
        new WebDriverWait(driver, Duration.ofSeconds(WAIT_TIMEOUT_SECONDS))
                .until(ExpectedConditions.visibilityOfElementLocated
                        (By.xpath(LOG_IN_MODAL_POPPED_UP_WINDOW_ELEMENT_XPATH)));
        logger.info("You've navigated to 'Log in' modal window");
        return this;
    }

    public String getLogInModalWindowTitle() {
        return driver.findElement(xpath(LOG_IN_PAGE_TITLE_ELEMENT_XPATH))
                .getAttribute("textContent");
    }

    //here there is more expedient might be replacing input 'username' with input 'email or phone number'
    // in implementing 'Log in' functionality
    public LogInModalWindowPage inputUsername(Customer account) {
        WebElement usernameLogInPlaceholder = driver.findElement(By.xpath(LOG_IN_PAGE_USERNAME_PLACEHOLDER_ELEMENT_XPATH));
        new WebDriverWait(driver, Duration.ofSeconds(WAIT_TIMEOUT_SECONDS))
                .until(ExpectedConditions.elementToBeClickable(usernameLogInPlaceholder)).click();
        usernameLogInPlaceholder.sendKeys(account.getFirstName().concat(" ").concat(account.getSurname()));
        logger.info("Entered username is " + usernameLogInPlaceholder.getAttribute("value"));
        return this;
    }

    public String getInputUsername() {
        return driver.findElement(xpath(LOG_IN_PAGE_USERNAME_PLACEHOLDER_ELEMENT_XPATH))
                .getAttribute("value");
    }

    public LogInModalWindowPage inputPassword(Customer account) {
        WebElement passwordLogInPlaceholder = driver.findElement(By.xpath(LOG_IN_PAGE_PASSWORD_PLACEHOLDER_ELEMENT_XPATH));
        new WebDriverWait(driver, Duration.ofSeconds(WAIT_TIMEOUT_SECONDS))
                .until(ExpectedConditions.elementToBeClickable(passwordLogInPlaceholder)).click();
        passwordLogInPlaceholder.sendKeys(account.getPassword());
        logger.info("Entered username is " + passwordLogInPlaceholder.getAttribute("value"));
        return this;
    }

    public String getInputPassword() {
        return driver.findElement(xpath(LOG_IN_PAGE_PASSWORD_PLACEHOLDER_ELEMENT_XPATH))
                .getAttribute("value");
    }

    //modified due to a developer's change of skipping alert message
    public CustomerAccountPage logIn(Customer account) throws MalformedURLException {
        inputUsername(account);
        inputPassword(account);
        pushLogInButton();
        new WebDriverWait(driver, Duration.ofSeconds(WAIT_TIMEOUT_SECONDS))
                .until(ExpectedConditions.visibilityOfElementLocated
                        (By.xpath(CUSTOMER_ACCOUNT_PAGE_USERNAME_LINK_DISPLAYED_IN_NAVIGATING_MENU_ELEMENT_XPATH)));
        logger.info("You have logged in your account.");
        return new CustomerAccountPage();
    }

    public LogInModalWindowPage logInWithEmptyUsername(Customer account) {
        inputPassword(account);
        pushLogInButton();
        acceptAlertOnLogIn();
        new WebDriverWait(driver, Duration.ofSeconds(WAIT_TIMEOUT_SECONDS))
                .until(ExpectedConditions.visibilityOfAllElementsLocatedBy
                        (xpath(LOG_IN_MODAL_POPPED_UP_WINDOW_ELEMENT_XPATH)));
        logger.info("You've pushed back to Log in registration window.");
        return this;
    }

    public LogInModalWindowPage logInWithEmptyPassword(Customer account) {
        inputUsername(account);
        pushLogInButton();
        acceptAlertOnLogIn();
        new WebDriverWait(driver, Duration.ofSeconds(WAIT_TIMEOUT_SECONDS))
                .until(ExpectedConditions.visibilityOfAllElementsLocatedBy
                        (xpath(LOG_IN_MODAL_POPPED_UP_WINDOW_ELEMENT_XPATH)));
        logger.info("You've pushed back to Log in registration window.");
        return this;
    }

    public LogInModalWindowPage logInWithNonRegisteredUsername(Customer account) {
        inputUsername(account);
        inputPassword(account);
        pushLogInButton();
        acceptAlertOnLogIn();
        new WebDriverWait(driver, Duration.ofSeconds(WAIT_TIMEOUT_SECONDS))
                .until(ExpectedConditions.visibilityOfElementLocated
                        (xpath(LOG_IN_MODAL_POPPED_UP_WINDOW_ELEMENT_XPATH)));
        logger.info("You've pushed back to Log in registration window.");
        return this;
    }

    //    here  there should be implemented features on website for resetting password or recovering it via
    //    different recovering options: phone number (call or sms), another email address, etc
    public LogInModalWindowPage logInWithWrongPassword(Customer account) {
        inputUsername(account);
        inputPassword(account);
        pushLogInButton();
        acceptAlertOnLogIn();
        new WebDriverWait(driver, Duration.ofSeconds(WAIT_TIMEOUT_SECONDS))
                .until(ExpectedConditions.visibilityOfAllElementsLocatedBy
                        (xpath(LOG_IN_MODAL_POPPED_UP_WINDOW_ELEMENT_XPATH)));
        logger.info("You've pushed back to Log in registration window.");
        return this;
    }

    public LogInModalWindowPage pushLogInButton() {
        WebElement buttonLogIn = driver.findElement(xpath(LOG_IN_PAGE_BUTTON_LOG_IN_MODAL_ELEMENT_XPATH));
        buttonLogIn.click();
        return this;
    }

    public String getAlertMessageResponseToLogInAction() {
        return new WebDriverWait(driver, Duration.ofSeconds(WAIT_TIMEOUT_SECONDS))
                .until(ExpectedConditions.alertIsPresent()).getText();
    }

    public LogInModalWindowPage acceptAlertOnLogIn() {
        new WebDriverWait(driver, Duration.ofSeconds(WAIT_TIMEOUT_SECONDS))
                .until(ExpectedConditions.alertIsPresent()).accept();
        logger.info("Alert window with the response message to 'Log in' is accepted");
        return this;
    }

    public boolean logInRegistrationWindowIsDisplayed() {
        return driver.findElement(By.xpath(LOG_IN_MODAL_POPPED_UP_WINDOW_ELEMENT_XPATH)).isDisplayed();
    }

    public void openNewTabByRobot() throws AWTException {
        Robot robot = new Robot();
        robot.keyPress(KeyEvent.VK_CONTROL);
        robot.keyPress(KeyEvent.VK_T);
        robot.keyRelease(KeyEvent.VK_T);
        robot.keyRelease(KeyEvent.VK_CONTROL);
        logger.info("New tab is opened");
    }

    // open new tab via Action class doesn't work
    public void openNewTabByAction() {
        Actions act = new Actions(driver);
        act.keyDown(Keys.CONTROL).sendKeys("t").keyUp(Keys.CONTROL).build().perform();
        logger.info("New tab is opened");
    }
}
