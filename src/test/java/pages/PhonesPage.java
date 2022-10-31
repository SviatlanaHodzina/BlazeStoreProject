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
import static pages.LaptopsPage.LAPTOP_ITEM_MODEL_LIST_ELEMENT_XPATH;
import static pages.MonitorsPage.MONITOR_ITEM_MODEL_LIST_ELEMENT_XPATH;

public class PhonesPage extends AbstractPage {
    public final Logger logger = LogManager.getRootLogger();

    public final static String PHONE_ITEM_MODEL_LIST_ELEMENT_XPATH = "//div[@class='card-block']//a[contains(text(),'%s')]";
    public final static String PRODUCT_ITEM_MODEL_PAGE_TITLE_ELEMENT_XPATH = "//div[@id='tbodyid']/h2[contains(text(),'%s')]";

    @FindBy(how = How.XPATH, using = PRODUCT_ITEM_IN_A_ROW_ELEMENT_XPATH)
    public static List<WebElement> phoneItemsList;

    @FindBy(how = How.XPATH, using = PRODUCT_ITEM_PRICE_ELEMENT_XPATH)
    private static List<WebElement> phonesPriceList;

    @FindBy(how = How.XPATH, using = PRODUCT_ITEM_MODEL_ELEMENT_XPATH)
    public static List<WebElement> phoneHrefModelsList;

    @FindBy(how = How.XPATH, using = PRODUCT_ITEM_DESCRIPTION_ELEMENT_XPATH)
    private static List<WebElement> phoneDescriptionList;


    public PhonesPage() throws MalformedURLException {
        super();
        PageFactory.initElements(this.driver, this);
    }

    @Override
    public PhonesPage openPage() {
        new WebDriverWait(driver, Duration.ofSeconds(WAIT_TIMEOUT_SECONDS))
                .until(ExpectedConditions.attributeContains(xpath(PRODUCT_CONTAINER_ELEMENT_XPATH),
                        "outerText", String.valueOf(getPhoneModelsListInResourceBundle())));
        logger.info("PhonesPage, containing phones items is opened");
        return this;
    }

    public List<String> getPhoneHrefModelsList() {
        new WebDriverWait(driver, Duration.ofSeconds(WAIT_TIMEOUT_SECONDS_LASTED))
                .until(ExpectedConditions.visibilityOfElementLocated(By.xpath(PRODUCT_ITEM_MODEL_ELEMENT_XPATH)));
        return phoneHrefModelsList.stream()
                .flatMap(phoneModel -> phoneModel.getAttribute("textContent").lines())
                .collect(Collectors.toList());
    }

    public List<String> getPhonePriceList() {
        new WebDriverWait(driver, Duration.ofSeconds(WAIT_TIMEOUT_SECONDS))
                .until(ExpectedConditions.visibilityOfElementLocated(By.xpath(PRODUCT_ITEM_PRICE_ELEMENT_XPATH)));
        return phonesPriceList.stream()
                .flatMap(phonePrice -> phonePrice.getAttribute("textContent").lines())
                .collect(Collectors.toList());
    }

    public Map<String, Integer> getPhoneModelsPriceMap() {
        new WebDriverWait(driver, Duration.ofSeconds(WAIT_TIMEOUT_SECONDS_LASTED))
                .until(ExpectedConditions.visibilityOfElementLocated(By.xpath(PRODUCT_ITEM_MODEL_ELEMENT_XPATH)));
        new WebDriverWait(driver, Duration.ofSeconds(WAIT_TIMEOUT_SECONDS_LASTED))
                .until(ExpectedConditions.visibilityOfElementLocated(By.xpath(PRODUCT_ITEM_PRICE_ELEMENT_XPATH)));
        Map<String, Integer> phonePriceMap = new HashMap<>();
        Iterator<String> listIterator = getPhoneHrefModelsList().iterator();
        Iterator<String> priceListIterator = getPhonePriceList().iterator();
        while (listIterator.hasNext()) {
            while (priceListIterator.hasNext()) {
                String phoneModel = listIterator.next();
                String phonePrice = priceListIterator.next();
                int phonePriceValue = Integer.parseInt(phonePrice.replace("$", ""));
                phonePriceMap.put(phoneModel, phonePriceValue);
            }
        }
        return phonePriceMap;
    }

    public List<String> getFilteredPhoneModelsList() {
        new WebDriverWait(driver, Duration.ofSeconds(WAIT_TIMEOUT_SECONDS))
                .until(ExpectedConditions.visibilityOfAllElements(phoneHrefModelsList));
        return phoneHrefModelsList.stream()
                .flatMap(phoneModel -> phoneModel.getAttribute("textContent").lines())
                .filter(m -> m.equals(getPhoneModelsListInResourceBundle()))
                .collect(Collectors.toList());
    }

    public List<String> getPhoneShortDescriptionList() {
        new WebDriverWait(driver, Duration.ofSeconds(WAIT_TIMEOUT_SECONDS))
                .until(ExpectedConditions.attributeContains(xpath(PRODUCT_CONTAINER_ELEMENT_XPATH),
                        "outerText", String.valueOf(getPhoneModelsListInResourceBundle())));
        return phoneDescriptionList.stream()
                .flatMap(phoneDescription -> phoneDescription.getAttribute("textContent").lines())
                .collect(Collectors.toList());
    }

    public int getSizeOfPhonesList() {
        return new WebDriverWait(driver, Duration.ofSeconds(WAIT_TIMEOUT_SECONDS_LASTED))
                .until(ExpectedConditions.visibilityOfAllElements(phoneItemsList)).size();
    }

    public String getPhoneModel(int phoneItemIndexInTheList) {
        new WebDriverWait(driver, Duration.ofSeconds(WAIT_TIMEOUT_SECONDS))
                .until(ExpectedConditions.visibilityOfElementLocated(xpath(PRODUCT_CONTAINER_ELEMENT_XPATH)));
        logger.info("The model of a phone index" + phoneItemIndexInTheList + "in the list is:"
                + phoneHrefModelsList.get(phoneItemIndexInTheList).getAttribute("textContent"));
        return phoneHrefModelsList.get(phoneItemIndexInTheList).getAttribute("textContent");
    }

    public String getPhonePrice(int phoneItemIndexInTheList) {
        new WebDriverWait(driver, Duration.ofSeconds(WAIT_TIMEOUT_SECONDS))
                .until(ExpectedConditions.visibilityOfElementLocated(xpath(PRODUCT_CONTAINER_ELEMENT_XPATH)));
        logger.info("The price for the phone index" + phoneItemIndexInTheList + "in the list is:"
                + phonesPriceList.get(phoneItemIndexInTheList).getAttribute("textContent"));
        return phonesPriceList.get(phoneItemIndexInTheList).getAttribute("textContent");
    }

    public String getPhoneDescription(int phoneItemIndexInTheList) {
        new WebDriverWait(driver, Duration.ofSeconds(WAIT_TIMEOUT_SECONDS))
                .until(ExpectedConditions.attributeContains(xpath(PRODUCT_CONTAINER_ELEMENT_XPATH),
                        "outerText", String.valueOf(getPhoneModelsListInResourceBundle())));
        logger.info("The description of a phone index" + phoneItemIndexInTheList + "in the list: "
                + phoneDescriptionList.get(phoneItemIndexInTheList).getAttribute("textContent"));
        return phoneDescriptionList.get(phoneItemIndexInTheList).getAttribute("textContent");
    }

    public Phone5Page openAPhoneModelPage(String phoneModelName) throws MalformedURLException {
        new WebDriverWait(driver, Duration.ofSeconds(WAIT_TIMEOUT_SECONDS))
                .until(ExpectedConditions.visibilityOfElementLocated
                        (xpath(format(PHONE_ITEM_MODEL_LIST_ELEMENT_XPATH, phoneModelName)))).click();
        new WebDriverWait(driver, Duration.ofSeconds(WAIT_TIMEOUT_SECONDS))
                .until((ExpectedConditions.visibilityOfElementLocated
                        (xpath(format(PRODUCT_ITEM_MODEL_PAGE_TITLE_ELEMENT_XPATH, phoneModelName)))));
        logger.info("Phone1 page is opened");
        return new Phone5Page();
    }

    public static int getCountOfBrokenImages() throws IOException {
        int imagesBrokenCount = 0;
        for (WebElement image : imageList) {
            if (image != null) {
                HttpClient client = HttpClientBuilder.create().build();
                HttpGet request = new HttpGet(image.getAttribute("src"));
                HttpResponse response = client.execute(request);
                if (response.getStatusLine().getStatusCode() != 200) {
                    imagesBrokenCount++;
                    saveScreenShot();
                }
            }
        }
        return imagesBrokenCount;
    }
}
