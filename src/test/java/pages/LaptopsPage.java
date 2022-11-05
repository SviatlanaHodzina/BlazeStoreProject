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

import static java.lang.String.format;
import static org.openqa.selenium.By.xpath;
import static pages.HomePage.*;
import static utils.ResourceBundleManagerClass.getLaptopModelsListInResourceBundle;

public class LaptopsPage extends AbstractPage {
    public final Logger logger = LogManager.getRootLogger();

    final static String ADD_TO_CART_BUTTON_ELEMENT_XPATH = "//*[@id='tbodyid']//a[@onclick='addToCart(%d)']";
    public final static String LAPTOP_ITEM_MODEL_LIST_ELEMENT_XPATH = "//div[@class='card-block']//a[contains(text(),'%s')]";

    @FindBy(how = How.XPATH, using = PRODUCT_ITEM_IN_A_ROW_ELEMENT_XPATH)
    private List<WebElement> laptopItemsList;

    @FindBy(how = How.XPATH, using = PRODUCT_ITEM_PRICE_ELEMENT_XPATH)
    private static List<WebElement> laptopsPriceList;

    @FindBy(how = How.XPATH, using = PRODUCT_ITEM_MODEL_ELEMENT_XPATH)
    private static List<WebElement> laptopHrefModelsList;

    @FindBy(how = How.XPATH, using = PRODUCT_ITEM_DESCRIPTION_ELEMENT_XPATH)
    private static List<WebElement> laptopDescriptionList;

    public LaptopsPage() throws MalformedURLException {
        super();
        PageFactory.initElements(this.driver, this);
    }

    @Override
    public LaptopsPage openPage() {
        new WebDriverWait(driver, Duration.ofSeconds(WAIT_TIMEOUT_SECONDS))
                .until(ExpectedConditions.attributeContains(xpath(PRODUCT_CONTAINER_ELEMENT_XPATH),
                        "outerText", String.valueOf(getLaptopModelsListInResourceBundle())));
        logger.info("LaptopsPage is opened");
        return this;
    }

    public List<String> getLaptopHrefModelsList() {
        new WebDriverWait(driver, Duration.ofSeconds(WAIT_TIMEOUT_SECONDS_LASTED))
                .until(ExpectedConditions.visibilityOfElementLocated(By.xpath(PRODUCT_ITEM_MODEL_ELEMENT_XPATH)));
        return laptopHrefModelsList.stream()
                .flatMap(laptopModel -> laptopModel.getAttribute("textContent").lines())
                .collect(Collectors.toList());
    }

    public List<String> getLaptopPriceList() {
        new WebDriverWait(driver, Duration.ofSeconds(WAIT_TIMEOUT_SECONDS))
                .until(ExpectedConditions.visibilityOfElementLocated(By.xpath(PRODUCT_ITEM_PRICE_ELEMENT_XPATH)));
        return laptopsPriceList.stream()
                .flatMap(laptopPrice -> laptopPrice.getAttribute("textContent").lines())
                .collect(Collectors.toList());
    }

    public Map<String, Integer> getLaptopModelsPriceMap() {
        new WebDriverWait(driver, Duration.ofSeconds(WAIT_TIMEOUT_SECONDS_LASTED))
                .until(ExpectedConditions.visibilityOfElementLocated(By.xpath(PRODUCT_ITEM_MODEL_ELEMENT_XPATH)));
        new WebDriverWait(driver, Duration.ofSeconds(WAIT_TIMEOUT_SECONDS_LASTED))
                .until(ExpectedConditions.visibilityOfElementLocated(By.xpath(PRODUCT_ITEM_PRICE_ELEMENT_XPATH)));
        Map<String, Integer> laptopPriceMap = new HashMap<>();
        Iterator<String> listIterator = getLaptopHrefModelsList().iterator();
        Iterator<String> priceListIterator = getLaptopPriceList().iterator();
        while (listIterator.hasNext()) {
            while (priceListIterator.hasNext()) {
                String laptopModel = listIterator.next();
                String laptopPrice = priceListIterator.next();
                int laptopPriceValue = Integer.parseInt(laptopPrice.replace("$", ""));
                laptopPriceMap.put(laptopModel, laptopPriceValue);
            }
        }
        return laptopPriceMap;
    }

    public List<String> getLaptopShortDescriptionList() {
        new WebDriverWait(driver, Duration.ofSeconds(WAIT_TIMEOUT_SECONDS))
                .until(ExpectedConditions.attributeContains(xpath(PRODUCT_CONTAINER_ELEMENT_XPATH),
                        "outerText", String.valueOf(getLaptopModelsListInResourceBundle())));
        return laptopDescriptionList.stream().sorted()
                .flatMap(laptopDescription -> laptopDescription.getAttribute("textContent").lines())
                .collect(Collectors.toList());
    }

    public int getSizeOfLaptopsList() {
        return laptopItemsList.size();
    }

    public String getLaptopPrice(int laptopItemIndexInTheList) {
        new WebDriverWait(driver, Duration.ofSeconds(WAIT_TIMEOUT_SECONDS))
                .until(ExpectedConditions.attributeContains(xpath(PRODUCT_CONTAINER_ELEMENT_XPATH),
                        "outerText", String.valueOf(getLaptopModelsListInResourceBundle())));
        logger.info("The price for the laptop index" + laptopItemIndexInTheList + "in the list is:"
                + laptopsPriceList.get(laptopItemIndexInTheList).getAttribute("textContent"));
        return laptopsPriceList.get(laptopItemIndexInTheList).getAttribute("textContent");
    }

    public String getLaptopModel(int laptopItemIndexInTheList) {
        new WebDriverWait(driver, Duration.ofSeconds(WAIT_TIMEOUT_SECONDS))
                .until(ExpectedConditions.attributeContains(xpath(PRODUCT_CONTAINER_ELEMENT_XPATH),
                        "outerText", String.valueOf(getLaptopModelsListInResourceBundle())));
        logger.info("The model of the laptop index" + laptopItemIndexInTheList + "in the list is:"
                + laptopHrefModelsList.get(laptopItemIndexInTheList).getAttribute("textContent"));
        return laptopHrefModelsList.get(laptopItemIndexInTheList).getAttribute("textContent");
    }

    public String getLaptopDescription(int laptopItemIndexInTheList) {
        new WebDriverWait(driver, Duration.ofSeconds(WAIT_TIMEOUT_SECONDS))
                .until(ExpectedConditions.attributeContains(xpath(PRODUCT_CONTAINER_ELEMENT_XPATH),
                        "outerText", String.valueOf(getLaptopModelsListInResourceBundle())));
        logger.info("The description of the laptop index" + laptopItemIndexInTheList + "in the list: "
                + laptopDescriptionList.get(laptopItemIndexInTheList).getAttribute("textContent"));
        return laptopDescriptionList.get(laptopItemIndexInTheList).getAttribute("textContent");
    }

    public LaptopsPage openALaptopModelPage(String laptopModelName) throws MalformedURLException {
        new WebDriverWait(driver, Duration.ofSeconds(WAIT_TIMEOUT_SECONDS))
                .until(ExpectedConditions.visibilityOfElementLocated
                        (xpath(format(PRODUCT_ITEM_MODEL_LIST_ELEMENT_XPATH, laptopModelName)))).click();
        new WebDriverWait(driver, Duration.ofSeconds(WAIT_TIMEOUT_SECONDS))
                .until((ExpectedConditions.visibilityOfElementLocated
                        (xpath(format(PRODUCT_ITEM_PAGE_MODEL_TITLE_ELEMENT_XPATH, laptopModelName)))));
        return new LaptopsPage();
    }

    public LaptopsPage pushAddToCartButton(int orderIndex) throws MalformedURLException {
        WebElement addToCart = driver.findElement(xpath(format(ADD_TO_CART_BUTTON_ELEMENT_XPATH, orderIndex)));
        addToCart.click();
        return new LaptopsPage();
    }

    public LaptopsPage acceptAlertOnAddToCart() throws MalformedURLException {
        new WebDriverWait(driver, Duration.ofSeconds(WAIT_TIMEOUT_SECONDS))
                .until(ExpectedConditions.alertIsPresent()).accept();
        logger.info("Alert window with the confirmation message of adding a product to a cart is accepted");
        return new LaptopsPage();
    }

    public static int getCountOfBrokenImages() throws IOException {
        int imagesBrokenCount = 0;
        for (WebElement image : imageList) {
            if (image != null) {
                HttpClient client = HttpClientBuilder.create().build();
                HttpGet request = new HttpGet(image.getAttribute("src"));
                HttpResponse response = client.execute(request);
                if (response.getStatusLine().getStatusCode() != 200) {
                    String brokenImageReference = image.getAttribute("src");
                    imagesBrokenCount++;
                    saveScreenShot();
                }
            }
        }
        return imagesBrokenCount;
    }
}
