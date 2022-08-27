package pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.MalformedURLException;
import java.time.Duration;

public class PurchaseInfoConfirmationPopUpWindowPage extends AbstractPage {
    public final Logger logger = LogManager.getRootLogger();

    // web design window page: there is no contrast with the background, that is necessary
    private static final String PURCHASE_CONFIRMATION_POPPED_UP_WINDOW_PAGE_ELEMENT_XPATH
            = "//div[@data-animation='pop']";
    private static final String BUTTON_OK_ON_PURCHASE_CONFIRMATION_WINDOW_PAGE_ELEMENT_XPATH
            = "//button[@class='confirm btn btn-lg btn-primary']";
    private static final String ORDER_INFO_ITEM_ON_PURCHASE_CONFIRMATION_WINDOW_PAGE_ELEMENT_XPATH
            = "//p[@class='lead text-muted ']";

    public PurchaseInfoConfirmationPopUpWindowPage() throws MalformedURLException {
        super();
        PageFactory.initElements(this.driver, this);
    }

    @Override
    public PurchaseInfoConfirmationPopUpWindowPage openPage() {
        new WebDriverWait(driver, Duration.ofSeconds(WAIT_TIMEOUT_SECONDS))
                .until(ExpectedConditions.visibilityOfElementLocated
                        (By.xpath(PURCHASE_CONFIRMATION_POPPED_UP_WINDOW_PAGE_ELEMENT_XPATH)));
        logger.info("You've got a confirmation of your order purchase with customer's and order's info");
        return this;
    }

    public boolean OrderInfoContentOnPurchaseConfirmationWindowPageIsDisplayed() {
        return driver.findElement(By.xpath(ORDER_INFO_ITEM_ON_PURCHASE_CONFIRMATION_WINDOW_PAGE_ELEMENT_XPATH))
                .isDisplayed();
    }

    // here divide the method on getting separate info about Id, Amount, card number, name and date
    public String getOrderInfoContentOnPurchaseConfirmationWindowPage() {
        return driver.findElement(By.xpath
                        (ORDER_INFO_ITEM_ON_PURCHASE_CONFIRMATION_WINDOW_PAGE_ELEMENT_XPATH))
                .getAttribute("textContent");
    }

    public HomePage confirmOrderPurchaseByClickingOKButton() throws MalformedURLException {
        new WebDriverWait(driver, Duration.ofSeconds(WAIT_TIMEOUT_SECONDS))
                .until(ExpectedConditions.elementToBeClickable
                        (By.xpath(BUTTON_OK_ON_PURCHASE_CONFIRMATION_WINDOW_PAGE_ELEMENT_XPATH))).click();
        logger.info("You are confirming the order purchase.");
        return new HomePage();
    }
}