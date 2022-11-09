package pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.w3c.dom.ranges.Range;

import java.net.MalformedURLException;
import java.time.Duration;
import java.time.temporal.ValueRange;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static java.lang.String.format;
import static org.openqa.selenium.By.xpath;
import static pages.HomePage.PRODUCT_ITEM_DESCRIPTION_ELEMENT_XPATH;
import static pages.HomePage.PRODUCT_ITEM_MODEL_ELEMENT_XPATH;

public class PurchaseInfoConfirmationPopUpWindowPage extends AbstractPage {
    public final Logger logger = LogManager.getRootLogger();

    // web design window page: there is no contrast with the background, that is necessary
    private static final String PURCHASE_CONFIRMATION_POPPED_UP_WINDOW_PAGE_ELEMENT_XPATH
            = "//div[@data-animation='pop']";
    private static final String BUTTON_OK_ON_PURCHASE_CONFIRMATION_WINDOW_PAGE_ELEMENT_XPATH
            = "//button[@class='confirm btn btn-lg btn-primary']";
    private static final String ORDER_INFO_ITEM_ON_PURCHASE_CONFIRMATION_WINDOW_PAGE_ELEMENT_XPATH
            = "//p[@class='lead text-muted ']";
    private static final String ORDER_INFO_CONTENT_ON_PURCHASE_CONFIRMATION_WINDOW_PAGE_ELEMENT_XPATH
            = "//p[@class='lead text-muted ']/text()";
    private static final String ORDER_INFO_PURCHASE_CONFIRMATION_MESSAGE_ELEMENT_XPATH
            = "//div[@class='sweet-alert  showSweetAlert visible']//h2";
    private static final String ORDER_INFO_ID_ON_PURCHASE_CONFIRMATION_ELEMENT_XPATH
            = " //p[@class='lead text-muted ']/text()[1]";
    private static final String ORDER_INFO_AMOUNT_ON_PURCHASE_CONFIRMATION_ELEMENT_XPATH
            = " //p[@class='lead text-muted ']/text()[2]";
    private static final String ORDER_INFO_CARD_NUMBER_ON_PURCHASE_CONFIRMATION_ELEMENT_XPATH
            = " //p[@class='lead text-muted ']/text()[3]";
    private static final String ORDER_INFO_NAME_ON_PURCHASE_CONFIRMATION_ELEMENT_XPATH
            = " //p[@class='lead text-muted ']/text()[4]";
    private static final String ORDER_INFO_DATE_ON_PURCHASE_CONFIRMATION_ELEMENT_XPATH
            = " //p[@class='lead text-muted ']/text()[5]";

    //use it for the future 'print' feature
    @FindBy(how = How.XPATH, using = ORDER_INFO_CONTENT_ON_PURCHASE_CONFIRMATION_WINDOW_PAGE_ELEMENT_XPATH)
    private static List<WebElement> orderContentInfoList;

    public PurchaseInfoConfirmationPopUpWindowPage() throws MalformedURLException {
        super();
        PageFactory.initElements(this.driver, this);
    }

    @Override
    public PurchaseInfoConfirmationPopUpWindowPage openPage() {
        new WebDriverWait(driver, Duration.ofSeconds(WAIT_TIMEOUT_SECONDS))
                .until(ExpectedConditions.visibilityOfElementLocated
                        (xpath(PURCHASE_CONFIRMATION_POPPED_UP_WINDOW_PAGE_ELEMENT_XPATH)));
        logger.info("You've got a confirmation of your order purchase with customer's and order's info");
        return this;
    }

    public boolean orderInfoContentOnPurchaseConfirmationWindowPageIsDisplayed() {
        return driver.findElement(xpath(ORDER_INFO_CONTENT_ON_PURCHASE_CONFIRMATION_WINDOW_PAGE_ELEMENT_XPATH))
                .isDisplayed();
    }

    public String getOrderInfoPurchaseConfirmationMessage() {
        new WebDriverWait(driver, Duration.ofSeconds(WAIT_TIMEOUT_SECONDS))
                .until(ExpectedConditions.visibilityOfElementLocated
                        (xpath(ORDER_INFO_PURCHASE_CONFIRMATION_MESSAGE_ELEMENT_XPATH)));
        return driver.findElement(xpath
                        (ORDER_INFO_PURCHASE_CONFIRMATION_MESSAGE_ELEMENT_XPATH))
                .getAttribute("textContent");
    }

    public String getDisplayedOrderInfoIDGeneratedOnPurchaseConfirmationWindowPage() {
        new WebDriverWait(driver, Duration.ofSeconds(WAIT_TIMEOUT_SECONDS))
                .until(ExpectedConditions.visibilityOfElementLocated
                        (xpath(ORDER_INFO_ID_ON_PURCHASE_CONFIRMATION_ELEMENT_XPATH)));
        return driver.findElement(xpath
                        (ORDER_INFO_ID_ON_PURCHASE_CONFIRMATION_ELEMENT_XPATH))
                .getAttribute("textContent");
    }

    public String getDisplayedOrderInfoAmountOnPurchaseConfirmationWindowPage() {
        new WebDriverWait(driver, Duration.ofSeconds(WAIT_TIMEOUT_SECONDS))
                .until(ExpectedConditions.visibilityOfElementLocated
                        (xpath(ORDER_INFO_AMOUNT_ON_PURCHASE_CONFIRMATION_ELEMENT_XPATH)));
        return driver.findElement(xpath(ORDER_INFO_AMOUNT_ON_PURCHASE_CONFIRMATION_ELEMENT_XPATH))
                .getAttribute("textContent").replace("Amount:USD", "");
    }

    public String getDisplayedOrderInfoCardNumberOnPurchaseConfirmationWindowPage() {
        new WebDriverWait(driver, Duration.ofSeconds(WAIT_TIMEOUT_SECONDS))
                .until(ExpectedConditions.visibilityOfElementLocated
                        (xpath(ORDER_INFO_CARD_NUMBER_ON_PURCHASE_CONFIRMATION_ELEMENT_XPATH)));
        return driver.findElement(xpath(ORDER_INFO_CARD_NUMBER_ON_PURCHASE_CONFIRMATION_ELEMENT_XPATH))
                .getAttribute("textContent");
    }

    public String getDisplayedOrderInfoNameOnPurchaseConfirmationWindowPage() {
        new WebDriverWait(driver, Duration.ofSeconds(WAIT_TIMEOUT_SECONDS))
                .until(ExpectedConditions.visibilityOfElementLocated
                        (xpath(ORDER_INFO_NAME_ON_PURCHASE_CONFIRMATION_ELEMENT_XPATH)));
        return driver.findElement(xpath(ORDER_INFO_NAME_ON_PURCHASE_CONFIRMATION_ELEMENT_XPATH))
                .getAttribute("textContent");
    }

    public String getDisplayedOrderInfoDateOnPurchaseConfirmationWindowPage() {
        new WebDriverWait(driver, Duration.ofSeconds(WAIT_TIMEOUT_SECONDS))
                .until(ExpectedConditions.visibilityOfElementLocated
                        (xpath(ORDER_INFO_DATE_ON_PURCHASE_CONFIRMATION_ELEMENT_XPATH)));
        return driver.findElement(xpath(ORDER_INFO_DATE_ON_PURCHASE_CONFIRMATION_ELEMENT_XPATH))
                .getAttribute("textContent");
    }

    public List<String> getDisplayedOrderInfoListWithoutIDOrder() {
        new WebDriverWait(driver, Duration.ofSeconds(WAIT_TIMEOUT_SECONDS))
                .until(ExpectedConditions.visibilityOfElementLocated(xpath(ORDER_INFO_ITEM_ON_PURCHASE_CONFIRMATION_WINDOW_PAGE_ELEMENT_XPATH)));
        List<String> orderInfoStringList = new ArrayList<>();
        orderInfoStringList.add(getDisplayedOrderInfoAmountOnPurchaseConfirmationWindowPage());
        orderInfoStringList.add(getDisplayedOrderInfoCardNumberOnPurchaseConfirmationWindowPage());
        orderInfoStringList.add(getDisplayedOrderInfoNameOnPurchaseConfirmationWindowPage());
        orderInfoStringList.add(getDisplayedOrderInfoDateOnPurchaseConfirmationWindowPage());
        return orderInfoStringList.stream()
                .flatMap(String::lines)
                .collect(Collectors.toList());
    }

    public String getPurchaseMessageConfirmationContextDisplayed() {
        new WebDriverWait(driver, Duration.ofSeconds(WAIT_TIMEOUT_SECONDS))
                .until(ExpectedConditions.visibilityOfElementLocated(xpath(ORDER_INFO_PURCHASE_CONFIRMATION_MESSAGE_ELEMENT_XPATH)));
        return driver.findElement(xpath(ORDER_INFO_PURCHASE_CONFIRMATION_MESSAGE_ELEMENT_XPATH))
                .getAttribute("textContent");
    }

    public HomePage confirmOrderPurchaseByClickingOKButton() throws MalformedURLException {
        new WebDriverWait(driver, Duration.ofSeconds(WAIT_TIMEOUT_SECONDS))
                .until(ExpectedConditions.elementToBeClickable
                        (xpath(BUTTON_OK_ON_PURCHASE_CONFIRMATION_WINDOW_PAGE_ELEMENT_XPATH))).click();
        logger.info("You are confirming the order purchase.");
        return new HomePage();
    }
}
