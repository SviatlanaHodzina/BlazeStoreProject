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
import java.util.*;
import java.util.stream.Collectors;

import static org.openqa.selenium.By.xpath;
import static pages.HomePage.*;

public class PhonesPage extends AbstractPage {
    public final Logger logger = LogManager.getRootLogger();

    @FindBy(how = How.XPATH, using = PRODUCT_ITEM_IN_A_ROW_ELEMENT_XPATH)
    private List<WebElement> phoneItemsList;

    @FindBy(how = How.XPATH, using = PRODUCT_ITEM_PRICE_ELEMENT_XPATH)
    private static List<WebElement> phonesPriceList;

    @FindBy(how = How.XPATH, using = PRODUCT_ITEM_MODEL_ELEMENT_XPATH)
    private static List<WebElement> phoneHrefModelsList;

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
        return phoneHrefModelsList.stream().sorted()
                .flatMap(phoneModel -> phoneModel.getAttribute("textContent").lines())
                .collect(Collectors.toList());
    }

    public static List<String> getPhoneModelsListInResourceBundle() {
        ResourceBundle resourceBundlePhones = ResourceBundle.getBundle("phones");
        Enumeration<String> phoneKeys = resourceBundlePhones.getKeys();
        List<String> phoneKeysList = new ArrayList<>();
        while (phoneKeys.hasMoreElements()) {
            String phoneKey = phoneKeys.nextElement();
            String phoneValue = resourceBundlePhones.getString(phoneKey);
            phoneKeysList.add(phoneValue);
        }
        return phoneKeysList;
    }

    public List<String> getPhonePriceList() {
        new WebDriverWait(driver, Duration.ofSeconds(WAIT_TIMEOUT_SECONDS))
                .until(ExpectedConditions.attributeContains(xpath(PRODUCT_CONTAINER_ELEMENT_XPATH),
                        "outerText", String.valueOf(getPhoneModelsListInResourceBundle())));
        return phonesPriceList.stream().sorted()
                .flatMap(phonePrice -> phonePrice.getAttribute("textContent").lines())
                .collect(Collectors.toList());
    }

    public List<String> getPhoneShortDescriptionList() {
        new WebDriverWait(driver, Duration.ofSeconds(WAIT_TIMEOUT_SECONDS))
                .until(ExpectedConditions.attributeContains(xpath(PRODUCT_CONTAINER_ELEMENT_XPATH),
                        "outerText", String.valueOf(getPhoneModelsListInResourceBundle())));
        return phoneDescriptionList.stream().sorted()
                .flatMap(phoneDescription -> phoneDescription.getAttribute("textContent").lines())
                .collect(Collectors.toList());
    }

    public int getSizeOfPhonesList() {
        return phoneItemsList.size();
    }

    public String getPhonePrice(int phoneItemIndexInTheList) {
        new WebDriverWait(driver, Duration.ofSeconds(WAIT_TIMEOUT_SECONDS))
                .until(ExpectedConditions.attributeContains(xpath(PRODUCT_CONTAINER_ELEMENT_XPATH),
                        "outerText", String.valueOf(getPhoneModelsListInResourceBundle())));
        logger.info("The price for the phone index" + phoneItemIndexInTheList + "in the list is:"
                + phonesPriceList.get(phoneItemIndexInTheList).getAttribute("textContent"));
        return phonesPriceList.get(phoneItemIndexInTheList).getAttribute("textContent");
    }

    public String getPhoneModel(int phoneItemIndexInTheList) {
        new WebDriverWait(driver, Duration.ofSeconds(WAIT_TIMEOUT_SECONDS))
                .until(ExpectedConditions.attributeContains(xpath(PRODUCT_CONTAINER_ELEMENT_XPATH),
                        "outerText", String.valueOf(getPhoneModelsListInResourceBundle())));
        logger.info("The model of a phone index" + phoneItemIndexInTheList + "in the list is:"
                + phoneHrefModelsList.get(phoneItemIndexInTheList).getAttribute("textContent"));
        return phoneHrefModelsList.get(phoneItemIndexInTheList).getAttribute("textContent");
    }

    public String getPhoneDescription(int phoneItemIndexInTheList) {
        new WebDriverWait(driver, Duration.ofSeconds(WAIT_TIMEOUT_SECONDS))
                .until(ExpectedConditions.attributeContains(xpath(PRODUCT_CONTAINER_ELEMENT_XPATH),
                        "outerText", String.valueOf(getPhoneModelsListInResourceBundle())));
        logger.info("The description of a phone index" + phoneItemIndexInTheList + "in the list: "
                + phoneDescriptionList.get(phoneItemIndexInTheList).getAttribute("textContent"));
        return phoneDescriptionList.get(phoneItemIndexInTheList).getAttribute("textContent");
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