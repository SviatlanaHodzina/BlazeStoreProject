package pages;

import customer.Customer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.MalformedURLException;
import java.time.Duration;

import static org.openqa.selenium.By.xpath;
import static pages.HomePage.*;

public class LogInModalWindowPage extends AbstractPage {
    public final Logger logger = LogManager.getRootLogger();

    private final static String LOG_IN_PAGE_MODAL_WINDOW_SHOW_ELEMENT_XPATH = "//div[contains(@id,'logInModal') and contains(@class,'modal fade show')]";
    private final static String LOG_IN_PAGE_USERNAME_PLACEHOLDER_ELEMENT_XPATH = "//input[@id='loginusername']";
    private final static String LOG_IN_PAGE_PASSWORD_PLACEHOLDER_ELEMENT_XPATH = "//input[@id='loginpassword']";
    private final static String LOG_IN_PAGE_BUTTON_LOG_IN_ELEMENT_XPATH = "//div[@id='logInModal']//button[@onclick='logIn()']";
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
                .getAttribute("textContent");
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
                .getAttribute("textContent");
    }

    public CustomerAccountPage LogIn(Customer account) throws MalformedURLException {
        inputUsername(account);
        inputPassword(account);
        WebElement buttonLogIn = driver.findElement(xpath(LOG_IN_PAGE_BUTTON_LOG_IN_ELEMENT_XPATH));
        buttonLogIn.click();
        new WebDriverWait(driver, Duration.ofSeconds(WAIT_TIMEOUT_SECONDS))
                .until(ExpectedConditions.alertIsPresent()).accept();
        logger.info("You have logged in your account");
        return new CustomerAccountPage();
    }

    public LogInModalWindowPage LogInWithEmptyUsername(Customer account) {
        inputPassword(account);
        WebElement buttonLogIn = driver.findElement(xpath(LOG_IN_PAGE_BUTTON_LOG_IN_ELEMENT_XPATH));
        buttonLogIn.click();
        new WebDriverWait(driver, Duration.ofSeconds(WAIT_TIMEOUT_SECONDS))
                .until(ExpectedConditions.alertIsPresent()).accept();
        new WebDriverWait(driver, Duration.ofSeconds(WAIT_TIMEOUT_SECONDS))
                .until(ExpectedConditions.elementToBeClickable
                        (xpath(LOG_IN_PAGE_USERNAME_PLACEHOLDER_ELEMENT_XPATH))).click();
        logger.info("Username input field is empty. Please, fill out empty field.");
        return this;
    }

    public LogInModalWindowPage LogInWithEmptyPassword(Customer account) {
        inputUsername(account);
        WebElement buttonLogIn = driver.findElement(xpath(LOG_IN_PAGE_BUTTON_LOG_IN_ELEMENT_XPATH));
        buttonLogIn.click();
        new WebDriverWait(driver, Duration.ofSeconds(WAIT_TIMEOUT_SECONDS))
                .until(ExpectedConditions.alertIsPresent()).accept();
        new WebDriverWait(driver, Duration.ofSeconds(WAIT_TIMEOUT_SECONDS))
                .until(ExpectedConditions.elementToBeClickable
                        (xpath(LOG_IN_PAGE_PASSWORD_PLACEHOLDER_ELEMENT_XPATH))).click();
        logger.info("Username input field is empty. Please, fill out empty field.");
        return this;
    }

    public LogInModalWindowPage LogInWithNonExistingUsername(Customer account) {
        inputUsername(account);
        inputPassword(account);
        WebElement buttonLogIn = driver.findElement(xpath(LOG_IN_PAGE_BUTTON_LOG_IN_ELEMENT_XPATH));
        buttonLogIn.click();
        new WebDriverWait(driver, Duration.ofSeconds(WAIT_TIMEOUT_SECONDS))
                .until(ExpectedConditions.alertIsPresent()).accept();
        new WebDriverWait(driver, Duration.ofSeconds(WAIT_TIMEOUT_SECONDS))
                .until(ExpectedConditions.elementToBeClickable
                        (xpath(LOG_IN_PAGE_USERNAME_PLACEHOLDER_ELEMENT_XPATH))).click();
        logger.info("This username does not exist. Please, input a registered username.");
        return this;
    }

    //    here  there should be implemented features on website for resetting password or recovering it via
    //    different recovering options: phone number (call or sms), another email address, etc
    public LogInModalWindowPage LogInWithWrongPassword(Customer account) {
        inputUsername(account);
        inputPassword(account);
        WebElement buttonLogIn = driver.findElement(xpath(LOG_IN_PAGE_BUTTON_LOG_IN_ELEMENT_XPATH));
        buttonLogIn.click();
        new WebDriverWait(driver, Duration.ofSeconds(WAIT_TIMEOUT_SECONDS))
                .until(ExpectedConditions.alertIsPresent()).accept();
        new WebDriverWait(driver, Duration.ofSeconds(WAIT_TIMEOUT_SECONDS))
                .until(ExpectedConditions.elementToBeClickable
                        (xpath(LOG_IN_PAGE_PASSWORD_PLACEHOLDER_ELEMENT_XPATH))).click();
        logger.info("Password is wrong. Try again.");
        return this;
    }

    public String getAlertMessageResponseOnLogInActionWithErrors(Customer account) {
        inputUsername(account);
        inputPassword(account);
        WebElement buttonSendMessage = driver.findElement(xpath(LOG_IN_PAGE_BUTTON_LOG_IN_ELEMENT_XPATH));
        buttonSendMessage.click();
        return new WebDriverWait(driver, Duration.ofSeconds(WAIT_TIMEOUT_SECONDS))
                .until(ExpectedConditions.alertIsPresent()).getText();
    }
}