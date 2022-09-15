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
import utils.StringUtils;

import java.net.MalformedURLException;
import java.time.Duration;
import java.util.Locale;
import java.util.ResourceBundle;

import static org.openqa.selenium.By.xpath;
import static pages.CustomerAccountPage.CUSTOMER_ACCOUNT_PAGE_USERNAME_LINK_IN_NAVIGATING_MENU_ELEMENT_XPATH;
import static pages.HomePage.*;
import static utils.StringUtils.generateRandomWeakPasswordWithRandomLength;

// Strong password feature is not implemented yet by a developer.
// The method for getting a weak password input warning message is reserved for the future feature.
public class SignUpModalWindowPage extends AbstractPage {
    public final Logger logger = LogManager.getRootLogger();
    public final static String SIGN_UP_MODAL_WINDOW_TITLE_ELEMENT_XPATH = "//*[@id='signInModalLabel']";
    public final static String SIGN_UP_USERNAME_PLACEHOLDER_ELEMENT_XPATH = "//input[@id='sign-username']";
    public final static String SIGN_UP_PASSWORD_PLACEHOLDER_ELEMENT_XPATH = "//input[@id='sign-password']";
    public final static String SIGN_UP_BUTTON_ELEMENT_XPATH = "//button[@onclick='register()']";
    public final static String SIGN_UP_WEAK_PASSWORD_WARNING_MESSAGE_ELEMENT_XPATH = "//here input XPATH for the future implemented element";


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

    public SignUpModalWindowPage inputGeneratedUsernameForInSignUpForm() {
        WebElement usernameSignUpPlaceholder = driver.findElement(By.xpath(SIGN_UP_USERNAME_PLACEHOLDER_ELEMENT_XPATH));
        new WebDriverWait(driver, Duration.ofSeconds(WAIT_TIMEOUT_SECONDS))
                .until(ExpectedConditions.elementToBeClickable(usernameSignUpPlaceholder)).click();
        usernameSignUpPlaceholder.sendKeys(StringUtils.generateRandomStringUsernameLength().concat(" ")
                .concat(StringUtils.generateRandomStringUsernameLength()));
        logger.info("Entered username is " + usernameSignUpPlaceholder.getAttribute("value"));
        return this;
    }

    public SignUpModalWindowPage inputGeneratedStrongPasswordInSignUpForm() {
        WebElement passwordSignUpPlaceholder = driver.findElement(By.xpath(SIGN_UP_PASSWORD_PLACEHOLDER_ELEMENT_XPATH));
        new WebDriverWait(driver, Duration.ofSeconds(WAIT_TIMEOUT_SECONDS))
                .until(ExpectedConditions.elementToBeClickable(passwordSignUpPlaceholder)).click();
        passwordSignUpPlaceholder.sendKeys(StringUtils.generateRandomStrongPasswordWithRandomLength());
        logger.info("Entered generated password is " + passwordSignUpPlaceholder.getAttribute("value"));
        return this;
    }

    public SignUpModalWindowPage inputGeneratedWeakPasswordInSignUpForm() {
        WebElement passwordSignUpPlaceholder = driver.findElement(By.xpath(SIGN_UP_PASSWORD_PLACEHOLDER_ELEMENT_XPATH));
        new WebDriverWait(driver, Duration.ofSeconds(WAIT_TIMEOUT_SECONDS))
                .until(ExpectedConditions.elementToBeClickable(passwordSignUpPlaceholder)).click();
        passwordSignUpPlaceholder.sendKeys(generateRandomWeakPasswordWithRandomLength());
        logger.info("Entered generated password is " + passwordSignUpPlaceholder.getAttribute("value"));
        return this;
    }

    public String getInputUsernameInSignUpForm() {
        return driver.findElement(By.xpath(SIGN_UP_USERNAME_PLACEHOLDER_ELEMENT_XPATH))
                .getAttribute("value");
    }

    public String getInputPasswordInSignUpForm() {
        return driver.findElement(By.xpath(SIGN_UP_PASSWORD_PLACEHOLDER_ELEMENT_XPATH))
                .getAttribute("value");
    }

    public boolean theInputPasswordInSignUpFormIsStrong() {
        ResourceBundle resourceBundle = ResourceBundle.getBundle("PasswordAndCreditCardSettings", Locale.US);
        int minLengthOfStrongPassword = Integer.parseInt(resourceBundle.getString("upperBoundLengthOfWeakPassword"));
        return ((driver.findElement(By.xpath(SIGN_UP_PASSWORD_PLACEHOLDER_ELEMENT_XPATH))
                .getAttribute("value").length()) >= minLengthOfStrongPassword);
    }

    public boolean theInputPasswordInSignUpFormIsWeak(int maxStrongPasswordLength) {
        return (driver.findElement(By.xpath(SIGN_UP_PASSWORD_PLACEHOLDER_ELEMENT_XPATH))
                .getAttribute("value").length() < maxStrongPasswordLength);
    }


    public CustomerAccountPage signUp() throws MalformedURLException {
        inputGeneratedUsernameForInSignUpForm();
        inputGeneratedStrongPasswordInSignUpForm();
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

    public SignUpModalWindowPage signUpWithEmptyUsernameInput() {
        inputGeneratedStrongPasswordInSignUpForm();
        pushSignUpButton();
        acceptAlertOnSignUp();
        new WebDriverWait(driver, Duration.ofSeconds(WAIT_TIMEOUT_SECONDS))
                .until(ExpectedConditions.visibilityOfAllElementsLocatedBy
                        (xpath(SIGN_UP_MODAL_POPPED_UP_WINDOW_ELEMENT_XPATH)));
        logger.info("You've pushed back to 'Sign up' registration window.");
        return this;
    }

    public SignUpModalWindowPage signUpWithEmptyPasswordInput() {
        inputGeneratedUsernameForInSignUpForm();
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
        inputGeneratedStrongPasswordInSignUpForm();
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

    // these methods for weak password message are reserved for the future strong password feature to be developed
    public String getTheWeakPasswordInputWarningMessageInSignUpRegistrationForm() {
        return driver.findElement(By.xpath(SIGN_UP_WEAK_PASSWORD_WARNING_MESSAGE_ELEMENT_XPATH)).getAttribute("textContent");
    }

    public boolean theWeakPasswordWarningMessageIsDisplayed() {
        return driver.findElement(By.xpath(SIGN_UP_WEAK_PASSWORD_WARNING_MESSAGE_ELEMENT_XPATH)).isDisplayed();
    }
}