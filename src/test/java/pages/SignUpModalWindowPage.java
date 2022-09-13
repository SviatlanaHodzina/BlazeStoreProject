package pages;

import customer.Customer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import sun.misc.SignalHandler;

import java.net.MalformedURLException;
import java.time.Duration;

import static org.openqa.selenium.By.xpath;
import static pages.CustomerAccountPage.CUSTOMER_ACCOUNT_PAGE_USERNAME_LINK_IN_NAVIGATING_MENU_ELEMENT_XPATH;
import static pages.HomePage.*;

public class SignUpModalWindowPage extends AbstractPage {
    public final Logger logger = LogManager.getRootLogger();

    public final static String SIGN_UP_MODAL_WINDOW_TITLE_ELEMENT_XPATH = "//*[@id='signInModalLabel']";
    public final static String SIGN_UP_USERNAME_PLACEHOLDER_ELEMENT_XPATH = "//input[@id='sign-username']";
    public final static String SIGN_UP_PASSWORD_PLACEHOLDER_ELEMENT_XPATH = "//input[@id='sign-password']";
    public final static String SIGN_UP_BUTTON_ELEMENT_XPATH = "//button[@onclick='register()']";

    public SignUpModalWindowPage() throws MalformedURLException {
        super();
        PageFactory.initElements(this.driver, this);
    }

    @Override
    public SignUpModalWindowPage openPage() {
        new WebDriverWait(driver, Duration.ofSeconds(WAIT_TIMEOUT_SECONDS))
                .until(ExpectedConditions.visibilityOfElementLocated
                        (By.xpath(SIGN_UP_MODAL_POPPED_UP_WINDOW_ELEMENT_XPATH)));
        logger.info("You've navigated to 'Sign up' modal window");
        return this;
    }

    public String getSignUpModalWindowTitle() {
        return driver.findElement(xpath(SIGN_UP_MODAL_WINDOW_TITLE_ELEMENT_XPATH))
                .getAttribute("textContent");
    }

    public SignUpModalWindowPage inputUsernameInSignUpForm(Customer account) {
        WebElement usernameSignUpPlaceholder = driver.findElement(By.xpath(SIGN_UP_USERNAME_PLACEHOLDER_ELEMENT_XPATH));
        new WebDriverWait(driver, Duration.ofSeconds(WAIT_TIMEOUT_SECONDS))
                .until(ExpectedConditions.elementToBeClickable(usernameSignUpPlaceholder)).click();
        usernameSignUpPlaceholder.sendKeys(account.getFirstName().concat(" ").concat(account.getSurname()));
        logger.info("Entered username is " + usernameSignUpPlaceholder.getAttribute("value"));
        return this;
    }

    public SignUpModalWindowPage inputPasswordInSignUpForm(Customer account) {
        WebElement passwordSignUpPlaceholder = driver.findElement(By.xpath(SIGN_UP_PASSWORD_PLACEHOLDER_ELEMENT_XPATH));
        new WebDriverWait(driver, Duration.ofSeconds(WAIT_TIMEOUT_SECONDS))
                .until(ExpectedConditions.elementToBeClickable(passwordSignUpPlaceholder)).click();
        passwordSignUpPlaceholder.sendKeys(account.getFirstName().concat(" ").concat(account.getSurname()));
        logger.info("Entered username is " + passwordSignUpPlaceholder.getAttribute("value"));
        return this;
    }

    public String getInputUsernameInSignUpForm() {
        return driver.findElement(xpath(SIGN_UP_USERNAME_PLACEHOLDER_ELEMENT_XPATH))
                .getAttribute("value");
    }

    public String getInputPasswordInSignUpForm() {
        return driver.findElement(xpath(SIGN_UP_PASSWORD_PLACEHOLDER_ELEMENT_XPATH))
                .getAttribute("value");
    }


    public CustomerAccountPage signUp(Customer account) throws MalformedURLException {
        inputUsernameInSignUpForm(account);
        inputPasswordInSignUpForm(account);
        pushSignUpButton();
        acceptAlertOnSignUp();
        new WebDriverWait(driver, Duration.ofSeconds(WAIT_TIMEOUT_SECONDS))
                .until(ExpectedConditions.invisibilityOfElementLocated
                        (By.xpath(SIGN_UP_MODAL_POPPED_UP_WINDOW_ELEMENT_XPATH)));
        logger.info("You have signed up your account.");
        return new CustomerAccountPage();
    }

    public SignUpModalWindowPage pushSignUpButton() {
        WebElement buttonSignUp = driver.findElement(xpath(SIGN_UP_BUTTON_ELEMENT_XPATH));
        buttonSignUp.click();
        return this;
    }

    public SignUpModalWindowPage acceptAlertOnSignUp() {
        new WebDriverWait(driver, Duration.ofSeconds(WAIT_TIMEOUT_SECONDS))
                .until(ExpectedConditions.alertIsPresent()).accept();
        logger.info("Alert window with the response message to 'Sign up' is accepted");
        return this;
    }

    public SignUpModalWindowPage signUpWithEmptyUsernameInput(Customer account) {
        inputPasswordInSignUpForm(account);
        pushSignUpButton();
        acceptAlertOnSignUp();
        new WebDriverWait(driver, Duration.ofSeconds(WAIT_TIMEOUT_SECONDS))
                .until(ExpectedConditions.visibilityOfAllElementsLocatedBy
                        (xpath(SIGN_UP_MODAL_POPPED_UP_WINDOW_ELEMENT_XPATH)));
        logger.info("You've pushed back to 'Sign up' registration window.");
        return this;
    }

    public SignUpModalWindowPage signUpWithEmptyPasswordInput(Customer account) {
        inputUsernameInSignUpForm(account);
        pushSignUpButton();
        acceptAlertOnSignUp();
        new WebDriverWait(driver, Duration.ofSeconds(WAIT_TIMEOUT_SECONDS))
                .until(ExpectedConditions.visibilityOfAllElementsLocatedBy
                        (xpath(SIGN_UP_MODAL_POPPED_UP_WINDOW_ELEMENT_XPATH)));
        logger.info("You've pushed back to 'Sign up' registration window.");
        return this;
    }

    public SignUpModalWindowPage signUpWithWrongPasswordInput(Customer account) {
        inputUsernameInSignUpForm(account);
        inputPasswordInSignUpForm(account);
        pushSignUpButton();
        acceptAlertOnSignUp();
        new WebDriverWait(driver, Duration.ofSeconds(WAIT_TIMEOUT_SECONDS))
                .until(ExpectedConditions.visibilityOfAllElementsLocatedBy
                        (xpath(SIGN_UP_MODAL_POPPED_UP_WINDOW_ELEMENT_XPATH)));
        logger.info("You've pushed back to 'Sign up' registration window.");
        return this;
    }

    public SignUpModalWindowPage signUpWithRegisteredBeforeUsername(Customer account) {
        inputUsernameInSignUpForm(account);
        inputPasswordInSignUpForm(account);
        pushSignUpButton();
        acceptAlertOnSignUp();
        new WebDriverWait(driver, Duration.ofSeconds(WAIT_TIMEOUT_SECONDS))
                .until(ExpectedConditions.visibilityOfAllElementsLocatedBy
                        (xpath(SIGN_UP_MODAL_POPPED_UP_WINDOW_ELEMENT_XPATH)));
        logger.info("You've pushed back to 'Sign up' registration window.");
        return this;
    }

    public boolean signUpRegistrationWindowIsDisplayed() {
        return driver.findElement(By.xpath(SIGN_UP_MODAL_POPPED_UP_WINDOW_ELEMENT_XPATH)).isDisplayed();
    }

    public String getAlertMessageResponseToSignUpAction() {
        return new WebDriverWait(driver, Duration.ofSeconds(WAIT_TIMEOUT_SECONDS))
                .until(ExpectedConditions.alertIsPresent()).getText();
    }
}
