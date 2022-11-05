package pages;

import customer.Customer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.MalformedURLException;
import java.time.Duration;

import static java.time.Duration.ofSeconds;
import static org.openqa.selenium.By.xpath;
import static pages.CartPage.PLACE_ORDER_POPPED_UP_WINDOW_ELEMENT_XPATH;

public class PlaceOrderModalWindow extends AbstractPage {
    public final Logger logger = LogManager.getRootLogger();

    private final static String PLACE_ORDER_WINDOW_HEADER_ELEMENT_XPATH = "//*[@id='orderModalLabel']";
    private final static String PLACE_ORDER_WINDOW_TOTAL_PRICE_ELEMENT_XPATH = "//*[@id='totalm']";
    private final static String PLACE_ORDER_FORM_NAME_INPUT_ELEMENT_XPATH = "//name[@id='name']";
    private final static String PLACE_ORDER_FORM_COUNTRY_INPUT_ELEMENT_XPATH = "//input[@id='country']";
    private final static String PLACE_ORDER_FORM_CITY_INPUT_ELEMENT_XPATH = "//input[@id='city']";
    private final static String PLACE_ORDER_FORM_CARD_INPUT_ELEMENT_XPATH = "//input[@id='card']";
    private final static String PLACE_ORDER_FORM_MONTH_INPUT_ELEMENT_XPATH = "//input[@id='month']";
    private final static String PLACE_ORDER_FORM_YEAR_INPUT_ELEMENT_XPATH = "//input[@id='year']";
    private final static String PLACE_ORDER_BUTTON_PURCHASE_ELEMENT_XPATH = "//button[@onclick='purchaseOrder()']";
    private final static String PLACE_ORDER_BUTTON_CLOSE_FOOTER_ELEMENT_XPATH = "//div[@id='orderModal']//button[@class='btn btn-secondary']";
    private final static String PLACE_ORDER_BUTTON_CLOSE_X_ONTOP_ELEMENT_XPATH = "//div[@id='orderModal']//button[@class='close']";

    @FindBy(xpath = PLACE_ORDER_BUTTON_PURCHASE_ELEMENT_XPATH)
    private WebElement buttonPurchase;

    public PlaceOrderModalWindow() throws MalformedURLException {
        super();
        PageFactory.initElements(this.driver, this);
    }

    @Override
    public PlaceOrderModalWindow openPage() {
        new WebDriverWait(driver, Duration.ofSeconds(WAIT_TIMEOUT_SECONDS))
                .until(ExpectedConditions.visibilityOfElementLocated
                        (By.xpath(PLACE_ORDER_POPPED_UP_WINDOW_ELEMENT_XPATH)));
        logger.info("Place Order ModalWindow is opened");
        return this;
    }

    public String getHeaderOfPlaceOrderWindow() {
        return driver.findElement(xpath(PLACE_ORDER_WINDOW_HEADER_ELEMENT_XPATH))
                .getAttribute("textContent");
    }

    public int getTotalPriceOnTopOfPlaceOrderWindow() {
        return Integer.parseInt(driver.findElement(xpath(PLACE_ORDER_WINDOW_TOTAL_PRICE_ELEMENT_XPATH))
                .getAttribute("textContent").replace("Total: ", ""));
    }

    public PlaceOrderModalWindow inputName(Customer customer) {
        WebElement inputName = driver.findElement(xpath(PLACE_ORDER_FORM_NAME_INPUT_ELEMENT_XPATH));
        new WebDriverWait(driver, ofSeconds(this.WAIT_TIMEOUT_SECONDS))
                .until(ExpectedConditions.elementToBeClickable
                        (xpath(PLACE_ORDER_FORM_NAME_INPUT_ELEMENT_XPATH))).click();
        inputName.click();
        inputName.sendKeys(customer.getFirstName() + " " + customer.getSurname());
        new WebDriverWait(driver, ofSeconds(this.WAIT_TIMEOUT_SECONDS))
                .until(ExpectedConditions.textToBePresentInElementValue(xpath
                                (PLACE_ORDER_FORM_NAME_INPUT_ELEMENT_XPATH),
                        customer.getFirstName() + " " + customer.getSurname()));
        logger.info("You have input a customer's name");
        return this;
    }

    public PlaceOrderModalWindow inputCountry(Customer customer) {
        WebElement inputCountry = driver.findElement(xpath(PLACE_ORDER_FORM_COUNTRY_INPUT_ELEMENT_XPATH));
        new WebDriverWait(driver, ofSeconds(this.WAIT_TIMEOUT_SECONDS))
                .until(ExpectedConditions.elementToBeClickable
                        (xpath(PLACE_ORDER_FORM_COUNTRY_INPUT_ELEMENT_XPATH))).click();
        inputCountry.click();
        inputCountry.sendKeys(customer.getCountry());
        new WebDriverWait(driver, ofSeconds(this.WAIT_TIMEOUT_SECONDS))
                .until(ExpectedConditions.textToBePresentInElementValue(xpath
                        (PLACE_ORDER_FORM_COUNTRY_INPUT_ELEMENT_XPATH), customer.getCountry()));
        logger.info("You have input a customer's country");
        return this;
    }

    public PlaceOrderModalWindow inputCity(Customer customer) {
        WebElement inputCity = driver.findElement(xpath(PLACE_ORDER_FORM_CITY_INPUT_ELEMENT_XPATH));
        new WebDriverWait(driver, ofSeconds(this.WAIT_TIMEOUT_SECONDS))
                .until(ExpectedConditions.elementToBeClickable
                        (xpath(PLACE_ORDER_FORM_CITY_INPUT_ELEMENT_XPATH))).click();
        inputCity.click();
        inputCity.sendKeys(customer.getCity());
        new WebDriverWait(driver, ofSeconds(this.WAIT_TIMEOUT_SECONDS))
                .until(ExpectedConditions.textToBePresentInElementValue(xpath
                        (PLACE_ORDER_FORM_CITY_INPUT_ELEMENT_XPATH), customer.getCity()));
        logger.info("You have input a customer's country");
        return this;
    }

    public PlaceOrderModalWindow inputCreditCard(Customer customer) {
        WebElement inputCreditCard = driver.findElement(xpath(PLACE_ORDER_FORM_CARD_INPUT_ELEMENT_XPATH));
        new WebDriverWait(driver, ofSeconds(this.WAIT_TIMEOUT_SECONDS))
                .until(ExpectedConditions.elementToBeClickable
                        (xpath(PLACE_ORDER_FORM_CARD_INPUT_ELEMENT_XPATH))).click();
        inputCreditCard.click();
        inputCreditCard.sendKeys(customer.getCreditCard());
        new WebDriverWait(driver, ofSeconds(this.WAIT_TIMEOUT_SECONDS))
                .until(ExpectedConditions.textToBePresentInElementValue(xpath
                        (PLACE_ORDER_FORM_CARD_INPUT_ELEMENT_XPATH), customer.getCreditCard()));
        logger.info("You have input a customer's credit card");
        return this;
    }

    // here should be implemented the method of a valid credit card format input, as in fact this functionality does not work

    public PlaceOrderModalWindow inputValidThruMonth(Customer customer) {
        WebElement inputValidThruMonth = driver.findElement(xpath(PLACE_ORDER_FORM_MONTH_INPUT_ELEMENT_XPATH));
        new WebDriverWait(driver, ofSeconds(this.WAIT_TIMEOUT_SECONDS))
                .until(ExpectedConditions.elementToBeClickable
                        (xpath(PLACE_ORDER_FORM_MONTH_INPUT_ELEMENT_XPATH))).click();
        inputValidThruMonth.click();
        inputValidThruMonth.sendKeys(customer.getValidThruMonth());
        new WebDriverWait(driver, ofSeconds(this.WAIT_TIMEOUT_SECONDS))
                .until(ExpectedConditions.textToBePresentInElementValue(xpath
                        (PLACE_ORDER_FORM_MONTH_INPUT_ELEMENT_XPATH), customer.getValidThruMonth()));
        logger.info("You have input a 'valid thru' month of a customer's credit card");
        return this;
    }

    public PlaceOrderModalWindow inputValidThruYear(Customer customer) {
        WebElement inputValidThruYear = driver.findElement(xpath(PLACE_ORDER_FORM_YEAR_INPUT_ELEMENT_XPATH));
        new WebDriverWait(driver, ofSeconds(this.WAIT_TIMEOUT_SECONDS))
                .until(ExpectedConditions.elementToBeClickable
                        (xpath(PLACE_ORDER_FORM_YEAR_INPUT_ELEMENT_XPATH))).click();
        inputValidThruYear.click();
        inputValidThruYear.sendKeys(customer.getValidThruYear());
        new WebDriverWait(driver, ofSeconds(this.WAIT_TIMEOUT_SECONDS))
                .until(ExpectedConditions.textToBePresentInElementValue(xpath
                        (PLACE_ORDER_FORM_YEAR_INPUT_ELEMENT_XPATH), customer.getValidThruYear()));
        logger.info("You have input a 'valid thru' year of a customer's credit card");
        return this;
    }

    public CartPage closePlaceOrderWindowInTheFooter() throws MalformedURLException {
        new WebDriverWait(driver, ofSeconds(this.WAIT_TIMEOUT_SECONDS))
                .until(ExpectedConditions.elementToBeClickable
                        (xpath(PLACE_ORDER_BUTTON_CLOSE_FOOTER_ELEMENT_XPATH))).click();
        new WebDriverWait(driver, ofSeconds(this.WAIT_TIMEOUT_SECONDS))
                .until(ExpectedConditions.invisibilityOfElementLocated
                        (xpath(PLACE_ORDER_POPPED_UP_WINDOW_ELEMENT_XPATH)));
        logger.info("You have closed the Place Order window");
        return new CartPage();
    }

    public CartPage xClosePlaceOrderWindowOnTop() throws MalformedURLException {
        new WebDriverWait(driver, ofSeconds(this.WAIT_TIMEOUT_SECONDS))
                .until(ExpectedConditions.elementToBeClickable
                        (xpath(PLACE_ORDER_BUTTON_CLOSE_X_ONTOP_ELEMENT_XPATH))).click();
        new WebDriverWait(driver, ofSeconds(this.WAIT_TIMEOUT_SECONDS))
                .until(ExpectedConditions.invisibilityOfElementLocated
                        (xpath(PLACE_ORDER_POPPED_UP_WINDOW_ELEMENT_XPATH)));
        logger.info("You have closed the Place Order window");
        return new CartPage();
    }

    public PurchaseInfoConfirmationPopUpWindowPage purchaseTheOrder(Customer customer) throws MalformedURLException {
        inputName(customer);
        inputCountry(customer);
        inputCity(customer);

        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0,600)", "");

        inputCreditCard(customer);
        inputValidThruMonth(customer);
        inputValidThruYear(customer);
        buttonPurchase.click();
        logger.info("You have made the order. Here is your order info");
        return new PurchaseInfoConfirmationPopUpWindowPage();
    }
}
