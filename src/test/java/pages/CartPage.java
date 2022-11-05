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
import java.util.*;
import java.util.stream.Collectors;

import static pages.HomePage.*;

public class CartPage extends AbstractPage {
    public final Logger logger = LogManager.getRootLogger();

    public final static String CART_PAGE_PRODUCT_ORDER_ITEM_IN_PRODUCT_TABLE_ELEMENT_XPATH = "//*[@id='tbodyid']/tr";
    public final static String CART_PAGE_PRODUCT_ORDER_IMAGE_IN_PRODUCT_TABLE_ELEMENT_XPATH = "//*[@id='tbodyid']/tr/td[1]";
    public final static String CART_PAGE_PRODUCT_ORDER_TITLE_IN_PRODUCT_TABLE_ELEMENT_XPATH = "//*[@id='tbodyid']/tr/td[2]";
    public final static String CART_PAGE_PRODUCT_ORDER_PRICE_IN_PRODUCT_TABLE_ELEMENT_XPATH = "//*[@id='tbodyid']/tr/td[3]";
    public final static String CART_PAGE_TOTAL_PRICE_IN_PRODUCT_TABLE_ELEMENT_XPATH = "//h3[@id='totalp']";
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

    @FindBy(how = How.XPATH, using = CART_PAGE_PRODUCT_ORDER_TITLE_IN_PRODUCT_TABLE_ELEMENT_XPATH)
    public static List<WebElement> productOrderTitlesList;

    @FindBy(how = How.XPATH, using = CART_PAGE_PRODUCT_ORDER_ITEM_IN_PRODUCT_TABLE_ELEMENT_XPATH)
    private List<WebElement> productOrderItemsList;

    @FindBy(how = How.XPATH, using = CART_PAGE_PRODUCT_ORDER_PRICE_IN_PRODUCT_TABLE_ELEMENT_XPATH)
    private List<WebElement> productOrderPriceList;

    @FindBy(how = How.XPATH, using = CART_PAGE_PRODUCT_ORDER_IMAGE_IN_PRODUCT_TABLE_ELEMENT_XPATH)
    private List<WebElement> productOrderImagesList;

    public String getProductOrderItemTitle() {
        new WebDriverWait(driver, Duration.ofSeconds(WAIT_TIMEOUT_SECONDS))
                .until(ExpectedConditions.visibilityOfElementLocated(By.xpath(CART_PAGE_PRODUCT_ORDER_TITLE_IN_PRODUCT_TABLE_ELEMENT_XPATH)));
        return driver.findElement(By.xpath(CART_PAGE_PRODUCT_ORDER_TITLE_IN_PRODUCT_TABLE_ELEMENT_XPATH))
                .getAttribute("textContent");
    }

    public List<String> getProductOrderTitlesListInACart() {
        new WebDriverWait(driver, Duration.ofSeconds(WAIT_TIMEOUT_SECONDS_LASTED))
                .until(ExpectedConditions.visibilityOfElementLocated(By.xpath(CART_PAGE_PRODUCT_ORDER_TITLE_IN_PRODUCT_TABLE_ELEMENT_XPATH)));
        return productOrderTitlesList.stream()
                .flatMap(productModel -> productModel.getAttribute("textContent").lines())
                .collect(Collectors.toList());
    }

    public List<String> getProductPriceListInACart() {
        new WebDriverWait(driver, Duration.ofSeconds(WAIT_TIMEOUT_SECONDS_LASTED))
                .until(ExpectedConditions.visibilityOfElementLocated(By.xpath(CART_PAGE_PRODUCT_ORDER_PRICE_IN_PRODUCT_TABLE_ELEMENT_XPATH)));
        return productOrderPriceList.stream()
                .flatMap(productPrice -> productPrice.getAttribute("textContent").lines())
                .collect(Collectors.toList());
    }

    public int getProductOrderItemPrice() {
        new WebDriverWait(driver, Duration.ofSeconds(WAIT_TIMEOUT_SECONDS))
                .until(ExpectedConditions.visibilityOfElementLocated(By.xpath(CART_PAGE_PRODUCT_ORDER_PRICE_IN_PRODUCT_TABLE_ELEMENT_XPATH)));
        return Integer.parseInt(driver.findElement(By.xpath(CART_PAGE_PRODUCT_ORDER_PRICE_IN_PRODUCT_TABLE_ELEMENT_XPATH))
                .getAttribute("textContent"));
    }

    public SortedMap<String, Integer> getPhoneModelsPriceListAddedInACartMap() {
        new WebDriverWait(driver, Duration.ofSeconds(WAIT_TIMEOUT_SECONDS_LASTED))
                .until(ExpectedConditions.visibilityOfElementLocated(By.xpath(CART_PAGE_PRODUCT_ORDER_TITLE_IN_PRODUCT_TABLE_ELEMENT_XPATH)));
        new WebDriverWait(driver, Duration.ofSeconds(WAIT_TIMEOUT_SECONDS_LASTED))
                .until(ExpectedConditions.visibilityOfElementLocated(By.xpath(CART_PAGE_PRODUCT_ORDER_PRICE_IN_PRODUCT_TABLE_ELEMENT_XPATH)));
        SortedMap<String, Integer> phonePriceMap = new TreeMap<>(new HashMap<>());
        Iterator<String> listIterator = getProductOrderTitlesListInACart().iterator();
        Iterator<String> priceListIterator = getProductPriceListInACart().iterator();
        while (listIterator.hasNext()) {
            while (priceListIterator.hasNext()) {
                String productModel = listIterator.next();
                String productPrice = priceListIterator.next();
                int productPriceValue = Integer.parseInt(productPrice);
                phonePriceMap.put(productModel, productPriceValue);
            }
        }
        return phonePriceMap;
    }

    /**
     * this function is not implemented on site yet
     * public String getDeleteConformationMessage(){
     * return driver.findElement(By.xpath("")).getAttribute("textContent");
     */
    public CartPage deleteAnOrder() {
        new WebDriverWait(driver, Duration.ofSeconds(WAIT_TIMEOUT_SECONDS))
                .until(ExpectedConditions.elementToBeClickable
                        (By.xpath(CART_PAGE_PRODUCT_ORDER_IN_PRODUCT_TABLE_DELETE_ELEMENT_XPATH))).click();
        logger.info("You have deleted the order.");
        return this;
    }

    public int getTotalPriceDisplayedInACart() {
        new WebDriverWait(driver, Duration.ofSeconds(WAIT_TIMEOUT_SECONDS_LASTED))
                .until(ExpectedConditions.visibilityOfElementLocated
                        (By.xpath(CART_PAGE_TOTAL_PRICE_IN_PRODUCT_TABLE_ELEMENT_XPATH)));
        int actualTotalprice = Integer.parseInt(driver.findElement
                        (By.xpath(CART_PAGE_TOTAL_PRICE_IN_PRODUCT_TABLE_ELEMENT_XPATH))
                .getAttribute("outerText"));
        return !(actualTotalprice == 0) ? actualTotalprice : null;
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

    public boolean productOrderItemHasImageInTheTable() {
        new WebDriverWait(driver, Duration.ofSeconds(WAIT_TIMEOUT_SECONDS))
                .until(ExpectedConditions.visibilityOfElementLocated(By.xpath(CART_PAGE_PRODUCT_ORDER_IMAGE_IN_PRODUCT_TABLE_ELEMENT_XPATH)));
        return driver.findElement(By.xpath(CART_PAGE_PRODUCT_ORDER_IMAGE_IN_PRODUCT_TABLE_ELEMENT_XPATH))
                .isDisplayed();
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
