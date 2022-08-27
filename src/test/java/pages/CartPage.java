package pages;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.IOException;
import java.net.MalformedURLException;
import java.time.Duration;
import java.util.List;

import static pages.HomePage.CART_PAGE_WRAPPER_ELEMENT_XPATH;
import static pages.HomePage.saveScreenShot;

public class CartPage extends AbstractPage {
    public final Logger logger = LogManager.getRootLogger();

    public final static String CART_PAGE_PRODUCT_ORDER_ITEM_IN_PRODUCT_TABLE_ELEMENT_XPATH = "//*[@id='tbodyid']/tr";
    public final static String CART_PAGE_PRODUCT_ORDER_IMAGE_IN_PRODUCT_TABLE_ELEMENT_XPATH = "//*[@id='tbodyid']/tr/td[1]";
    public final static String CART_PAGE_PRODUCT_ORDER_TITLE_IN_PRODUCT_TABLE_ELEMENT_XPATH = "//*[@id='tbodyid']/tr/td[2]";
    public final static String CART_PAGE_PRODUCT_ORDER_PRICE_IN_PRODUCT_TABLE_ELEMENT_XPATH = "//*[@id='tbodyid']/tr/td[3]";
    public final static String CART_PAGE_TOTAL_PRICE_IN_PRODUCT_TABLE_ELEMENT_XPATH = "//*[@id='totalp']";
    public final static String CART_PAGE_PRODUCT_ORDER_IN_PRODUCT_TABLE_DELETE_ELEMENT_XPATH = "//*[@id='tbodyid']/tr/td[4]/a";
    public final static String CART_PAGE_BUTTON_PLACE_ORDER_ELEMENT_XPATH = "//button[@data-target='#orderModal']";
    public final static String PLACE_ORDER_POPPED_UP_WINDOW_ELEMENT_XPATH = "//*[@id='orderModal']//*[@class='modal-content']";

    @FindBy(how = How.TAG_NAME, using = "img")
    private static List<WebElement> imageList;

    public CartPage() throws MalformedURLException {
        super();
        PageFactory.initElements(this.driver, this);
    }

    @Override
    public CartPage openPage() {
        new WebDriverWait(driver, Duration.ofSeconds(WAIT_TIMEOUT_SECONDS))
                .until(ExpectedConditions.visibilityOfElementLocated(By.xpath(CART_PAGE_WRAPPER_ELEMENT_XPATH)));
        logger.info("Cart Page is opened");
        return this;
    }

    @FindBy(how = How.XPATH, using = CART_PAGE_PRODUCT_ORDER_ITEM_IN_PRODUCT_TABLE_ELEMENT_XPATH)
    private List<WebElement> productOrderItemsList;

    @FindBy(how = How.XPATH, using = CART_PAGE_PRODUCT_ORDER_PRICE_IN_PRODUCT_TABLE_ELEMENT_XPATH)
    private List<WebElement> productOrdersPriceList;

    public String getProductOrderItemTitle() {
        return driver.findElement
                        (By.xpath(CART_PAGE_PRODUCT_ORDER_TITLE_IN_PRODUCT_TABLE_ELEMENT_XPATH))
                .getAttribute("textContent");
    }

    public int getProductOrderItemPrice() {
        return Integer.parseInt(driver.findElement
                        (By.xpath(CART_PAGE_PRODUCT_ORDER_PRICE_IN_PRODUCT_TABLE_ELEMENT_XPATH))
                .getAttribute("textContent"));
    }

    public boolean productOrderItemHasImageInTheTable() {
        return driver.findElement
                        (By.xpath(CART_PAGE_PRODUCT_ORDER_IMAGE_IN_PRODUCT_TABLE_ELEMENT_XPATH))
                .isDisplayed();
    }

    public int getSizeOfProductOrderList() {
        return productOrderItemsList.size();
    }

    public int getTotalPriceOfProductOrders() {
        return productOrdersPriceList.size();
    }

    public CartPage deleteAnOrder() {
        new WebDriverWait(driver, Duration.ofSeconds(WAIT_TIMEOUT_SECONDS))
                .until(ExpectedConditions.elementToBeClickable
                        (By.xpath(CART_PAGE_PRODUCT_ORDER_IN_PRODUCT_TABLE_DELETE_ELEMENT_XPATH))).click();
        logger.info("You have deleted the order.");
        return this;
    }
//    this function is not implemented on site
//    public String getDeleteConformationMessage(){
//        return driver.findElement(By.xpath("")).getAttribute("textContent");
//    }

    public int getTotalPriceDisplayedInProductOrdersTable() {
        return Integer.parseInt(driver.findElement
                        (By.xpath(CART_PAGE_TOTAL_PRICE_IN_PRODUCT_TABLE_ELEMENT_XPATH))
                .getAttribute("textContent"));
    }

    public PlaceOrderModalWindow placeOrder() throws MalformedURLException {
        new WebDriverWait(driver, Duration.ofSeconds(WAIT_TIMEOUT_SECONDS))
                .until(ExpectedConditions.elementToBeClickable
                        (By.xpath(CART_PAGE_BUTTON_PLACE_ORDER_ELEMENT_XPATH))).click();
        new WebDriverWait(driver, Duration.ofSeconds(WAIT_TIMEOUT_SECONDS))
                .until(ExpectedConditions.visibilityOfElementLocated(By.xpath(PLACE_ORDER_POPPED_UP_WINDOW_ELEMENT_XPATH)));
        logger.info("This is Place order form. Fill out the input fields, please.");
        return new PlaceOrderModalWindow();
    }

    public static int getCountOfBrokenImages() throws IOException {
        int imagesBrokenCount = 0;
        for (WebElement image : imageList) {
            if (image != null) {
                HttpClient client = HttpClientBuilder.create().build();
                HttpGet request = new HttpGet(image.getAttribute("src"));
                HttpResponse response = client.execute(request);
                if (response.getStatusLine().getStatusCode() != 200) {
                    String brokenImageReference = image.getAttribute("outerHTML");
                    imagesBrokenCount++;
                    saveScreenShot();
                }
            }
        }
        return imagesBrokenCount;
    }
}