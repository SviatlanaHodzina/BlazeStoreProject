package pages;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.IOException;
import java.net.MalformedURLException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import static org.openqa.selenium.By.xpath;
import static pages.HomePage.*;

public class LaptopsPage extends AbstractPage {
    public final Logger logger = LogManager.getRootLogger();

    public LaptopsPage() throws MalformedURLException {
        super();
        PageFactory.initElements(this.driver, this);
    }

    @Override
    public LaptopsPage openPage() {
        new WebDriverWait(driver, Duration.ofSeconds(WAIT_TIMEOUT_SECONDS))
                .until(ExpectedConditions.attributeContains(xpath(PRODUCT_CONTAINER_ELEMENT_XPATH),
                        "outerText", String.valueOf(getLaptopModelsListInResourceBundle())));
        logger.info("Cart Page is opened");
        return this;
    }

    @FindBy(how = How.XPATH, using = PRODUCT_ITEM_IN_A_ROW_ELEMENT_XPATH)
    private List<WebElement> laptopItemsList;

    @FindBy(how = How.XPATH, using = PRODUCT_ITEM_PRICE_ELEMENT_XPATH)
    private static List<WebElement> laptopsPriceList;

    @FindBy(how = How.XPATH, using = PRODUCT_ITEM_MODEL_ELEMENT_XPATH)
    private static List<WebElement> laptopHrefModelsList;

    @FindBy(how = How.XPATH, using = PRODUCT_ITEM_DESCRIPTION_ELEMENT_XPATH)
    private static List<WebElement> laptopDescriptionList;

    public List<String> getLaptopModelsList() {
        new WebDriverWait(driver, Duration.ofSeconds(WAIT_TIMEOUT_SECONDS))
                .until(ExpectedConditions.attributeContains(xpath(PRODUCT_CONTAINER_ELEMENT_XPATH),
                        "outerText", String.valueOf(getLaptopModelsListInResourceBundle())));
        return laptopHrefModelsList.stream().sorted()
                .flatMap(phoneModel -> phoneModel.getAttribute("textContent").lines())
                .collect(Collectors.toList());
    }

    public static List<String> getLaptopModelsListInResourceBundle() {
        ResourceBundle resourceBundleLaptops = ResourceBundle.getBundle("laptops");
        Enumeration<String> laptopKeys = resourceBundleLaptops.getKeys();
        List<String> laptopKeysList = new ArrayList<>();
        while (laptopKeys.hasMoreElements()) {
            String laptopKey = laptopKeys.nextElement();
            String laptopValue = resourceBundleLaptops.getString(laptopKey);
            laptopKeysList.add(laptopValue);
        }
        return laptopKeysList;
    }

    public List<String> getLaptopPriceList() {
        new WebDriverWait(driver, Duration.ofSeconds(WAIT_TIMEOUT_SECONDS))
                .until(ExpectedConditions.attributeContains(xpath(PRODUCT_CONTAINER_ELEMENT_XPATH),
                        "outerText", String.valueOf(getLaptopModelsListInResourceBundle())));
        return laptopsPriceList.stream().sorted()
                .flatMap(phonePrice -> phonePrice.getAttribute("textContent").lines())
                .collect(Collectors.toList());
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