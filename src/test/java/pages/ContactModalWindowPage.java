package pages;

import customer.Customer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.Color;
import org.openqa.selenium.support.Colors;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.MalformedURLException;
import java.time.Duration;

import static org.openqa.selenium.By.xpath;
import static pages.HomePage.CONTACT_MODAL_WINDOW_SHOW_ELEMENT_XPATH;
import static pages.HomePage.CONTACT_LINK_IN_NAVIGATING_MENU_ELEMENT_XPATH;

public class ContactModalWindowPage extends AbstractPage {
    public final Logger logger = LogManager.getRootLogger();

    private final static String CONTACT_MODAL_DIALOG_WINDOW_ELEMENT_XPATH = "//*[@id='exampleModal']//*[@class='modal-dialog']";
    private final static String CONTACT_MODAL_WINDOW_ELEMENT_XPATH = "//*[@id='exampleModal']";
    private final static String CONTACT_MODAL_WINDOW_TITLE_ELEMENT_XPATH = "//*[@id='exampleModalLabel']";
    private final static String CONTACT_PAGE_CONTACT_EMAIL_PLACEHOLDER_ELEMENT_XPATH = "//*[@id='recipient-email']";
    private final static String CONTACT_PAGE_CONTACT_NAME_PLACEHOLDER_ELEMENT_XPATH = "//*[@id='recipient-name']";
    private final static String CONTACT_PAGE_MESSAGE_PLACEHOLDER_ELEMENT_XPATH = "//*[@id='message-text']";
    private final static String CONTACT_PAGE_BUTTON_SEND_MESSAGE_ELEMENT_XPATH = "//div[@id='exampleModal']//button[@class='btn btn-primary']";
    private final static String CONTACT_PAGE_BUTTON_CLOSE_FOOTER_ELEMENT_XPATH = "//div[@id='exampleModal']//button[@class='btn btn-secondary']";
    private final static String CONTACT_PAGE_BUTTON_CLOSE_X_ONTOP_ELEMENT_XPATH = "//div[@id='exampleModal']//button[@class='close']";

    public ContactModalWindowPage() throws MalformedURLException {
        super();
        PageFactory.initElements(this.driver, this);
    }

    @Override
    public ContactModalWindowPage openPage() {
        new WebDriverWait(driver, Duration.ofSeconds(WAIT_TIMEOUT_SECONDS))
                .until(ExpectedConditions.visibilityOfElementLocated(By.xpath(CONTACT_MODAL_WINDOW_SHOW_ELEMENT_XPATH)));
        logger.info("You've navigated to 'Contact' modal window");
        return this;
    }

    public String getContactModalWindowTitle() {
        return driver.findElement(xpath(CONTACT_MODAL_WINDOW_TITLE_ELEMENT_XPATH))
                .getAttribute("textContent");
    }

    public ContactModalWindowPage inputContactEmail(Customer account) {
        WebElement contactEmailPlaceholder = driver.findElement(By.xpath(CONTACT_PAGE_CONTACT_EMAIL_PLACEHOLDER_ELEMENT_XPATH));
        contactEmailPlaceholder.click();
        contactEmailPlaceholder.sendKeys(account.getEmail());
        logger.info("Entered contact email is " + contactEmailPlaceholder.getAttribute("value"));
        return this;
    }

    public String getInputContactEmail() {
        return driver.findElement(xpath(CONTACT_PAGE_CONTACT_EMAIL_PLACEHOLDER_ELEMENT_XPATH)).getAttribute("value");
    }

    public ContactModalWindowPage inputContactName(Customer account) {
        WebElement contactNamePlaceholder = driver.findElement(By.xpath(CONTACT_PAGE_CONTACT_NAME_PLACEHOLDER_ELEMENT_XPATH));
        new WebDriverWait(driver, Duration.ofSeconds(WAIT_TIMEOUT_SECONDS))
                .until(ExpectedConditions.elementToBeClickable(contactNamePlaceholder)).click();
        contactNamePlaceholder.sendKeys(account.getFirstName().concat(" ").concat(account.getSurname()));
        logger.info("Entered contact name is " + contactNamePlaceholder.getAttribute("value"));
        return this;
    }

    public String getInputContactName() {
        return driver.findElement(xpath(CONTACT_PAGE_CONTACT_NAME_PLACEHOLDER_ELEMENT_XPATH)).getAttribute("value");
    }

    public ContactModalWindowPage inputMessage(String message) {
        WebElement messagePlaceholder = driver.findElement(By.xpath(CONTACT_PAGE_MESSAGE_PLACEHOLDER_ELEMENT_XPATH));
        new WebDriverWait(driver, Duration.ofSeconds(WAIT_TIMEOUT_SECONDS))
                .until(ExpectedConditions.elementToBeClickable(messagePlaceholder)).click();
        messagePlaceholder.sendKeys(message);
        logger.info("The sent message is:" + messagePlaceholder.getAttribute("value"));
        return this;
    }

    public String getInputMessage() {
        return driver.findElement(xpath(CONTACT_PAGE_MESSAGE_PLACEHOLDER_ELEMENT_XPATH)).getAttribute("value");
    }

    public ContactModalWindowPage sendMessage() {
        WebElement buttonSendMessage = driver.findElement(xpath(CONTACT_PAGE_BUTTON_SEND_MESSAGE_ELEMENT_XPATH));
        buttonSendMessage.click();
        logger.info("Message is sent");
        return this;
    }

    public String getResponseMessageInAlertWindowOnSendingMessageInContactPage() {
        return new WebDriverWait(driver, Duration.ofSeconds(WAIT_TIMEOUT_SECONDS))
                .until(ExpectedConditions.alertIsPresent()).getText();
    }

    public ContactModalWindowPage acceptAlertOnSendMessage() {
        new WebDriverWait(driver, Duration.ofSeconds(WAIT_TIMEOUT_SECONDS))
                .until(ExpectedConditions.alertIsPresent()).accept();
        logger.info("Alert window with the response message is accepted");
        return this;
    }

    public boolean acceptanceOfFailureOfSendingMessageReturnsCustomerToContactModalWindow() {
        return driver.findElement(By.xpath(CONTACT_MODAL_WINDOW_ELEMENT_XPATH)).isDisplayed();
    }

    public boolean acceptanceOfTheAlertOfSentMessageClosesTheContactModalWindow() {
        return !driver.findElement(By.xpath(CONTACT_MODAL_WINDOW_ELEMENT_XPATH)).isDisplayed();
    }

    public ContactModalWindowPage completeContactFormWithEmptyEmailInput(Customer account, String message) {
        inputContactName(account);
        inputMessage(message);
        logger.info("The Contact form is completed with skipped Email input field.");
        return this;
    }

    public ContactModalWindowPage completeContactFormWithEmptyNameInput(Customer account, String message) {
        inputContactEmail(account);
        inputMessage(message);
        logger.info("The Contact form is completed with skipped Name input field.");
        return this;
    }

    public ContactModalWindowPage completeContactFormWithEmptyMessageInput(Customer account) {
        inputContactEmail(account);
        inputContactName(account);
        logger.info("The Contact form is completed with skipped Message input field.");
        return this;
    }

    public ContactModalWindowPage closeTheContactWidowPageByCloseFooterButton() {
        new WebDriverWait(driver, Duration.ofSeconds(WAIT_TIMEOUT_SECONDS))
                .until(ExpectedConditions.elementToBeClickable
                        (xpath(CONTACT_PAGE_BUTTON_CLOSE_FOOTER_ELEMENT_XPATH))).click();
        new WebDriverWait(driver, Duration.ofSeconds(WAIT_TIMEOUT_SECONDS))
                .until(ExpectedConditions.invisibilityOfElementLocated
                        (xpath(CONTACT_MODAL_DIALOG_WINDOW_ELEMENT_XPATH)));
        logger.info("You have closed the Contact window page");
        return this;
    }

    public ContactModalWindowPage closeTheContactWidowPageByCloseXOnTopButton() {
        new WebDriverWait(driver, Duration.ofSeconds(WAIT_TIMEOUT_SECONDS))
                .until(ExpectedConditions.elementToBeClickable
                        (xpath(CONTACT_PAGE_BUTTON_CLOSE_X_ONTOP_ELEMENT_XPATH))).click();
        new WebDriverWait(driver, Duration.ofSeconds(WAIT_TIMEOUT_SECONDS))
                .until(ExpectedConditions.invisibilityOfElementLocated
                        (xpath(CONTACT_MODAL_DIALOG_WINDOW_ELEMENT_XPATH)));
        logger.info("You have closed the Contact window page");
        return this;
    }

    public boolean contactModalWindowIsClosed() {
        return !driver.findElement(xpath(CONTACT_MODAL_DIALOG_WINDOW_ELEMENT_XPATH)).isDisplayed();
    }

    public String getColorOfActiveContactLink() {
        return driver.findElement(xpath(CONTACT_LINK_IN_NAVIGATING_MENU_ELEMENT_XPATH)).getCssValue("color");
    }

    boolean activeContactLinkOnTopMenuDiffersWithContrastColorAfterClosingPoppedUpWindow() {
        new WebDriverWait(driver, Duration.ofSeconds(WAIT_TIMEOUT_SECONDS))
                .until(ExpectedConditions.elementToBeClickable
                        (xpath(CONTACT_PAGE_BUTTON_CLOSE_X_ONTOP_ELEMENT_XPATH))).click();
        return driver.findElement(xpath(CONTACT_LINK_IN_NAVIGATING_MENU_ELEMENT_XPATH)).getCssValue("color").equals("#014c8c");
    }
}