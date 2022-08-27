package pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.MalformedURLException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import static org.openqa.selenium.By.xpath;
import static pages.HomePage.*;

public class MonitorsPage extends AbstractPage {

    public final Logger logger = LogManager.getRootLogger();

    @FindBy(how = How.XPATH, using = PRODUCT_ITEM_IN_A_ROW_ELEMENT_XPATH)
    private List<WebElement> monitorItemsList;

    @FindBy(how = How.XPATH, using = PRODUCT_ITEM_PRICE_ELEMENT_XPATH)
    private static List<WebElement> monitorPriceList;

    @FindBy(how = How.XPATH, using = PRODUCT_ITEM_MODEL_ELEMENT_XPATH)
    private static List<WebElement> monitorHrefModelsList;

    @FindBy(how = How.XPATH, using = PRODUCT_ITEM_DESCRIPTION_ELEMENT_XPATH)
    private static List<WebElement> monitorDescriptionList;

    public MonitorsPage() throws MalformedURLException {
        super();
        PageFactory.initElements(this.driver, this);
    }

    @Override
    public MonitorsPage openPage() {
        new WebDriverWait(driver, Duration.ofSeconds(WAIT_TIMEOUT_SECONDS))
                .until(ExpectedConditions.attributeContains(xpath(PRODUCT_CONTAINER_ELEMENT_XPATH),
                        "outerText", String.valueOf(getMonitorModelsListInResourceBundle())));
        logger.info("MonitorsPage, containing monitor items, is opened");
        return this;
    }

    @FindBy(how = How.XPATH, using = PRODUCT_ITEM_MODEL_ELEMENT_XPATH)
    private List<WebElement> monitorModelsList;

    public List<String> getMonitorModelsList() {
        new WebDriverWait(driver, Duration.ofSeconds(WAIT_TIMEOUT_SECONDS))
                .until(ExpectedConditions.attributeContains(xpath(PRODUCT_CONTAINER_ELEMENT_XPATH),
                        "outerText", String.valueOf(getMonitorModelsListInResourceBundle())));
        return monitorModelsList.stream().sorted()
                .flatMap(monitorModel -> monitorModel.getAttribute("textContent").lines())
                .collect(Collectors.toList());
    }

    public List<String> getMonitorModelsListInResourceBundle() {
        ResourceBundle resourceBundleMonitors = ResourceBundle.getBundle("monitors");
        Enumeration<String> monitorKeys = resourceBundleMonitors.getKeys();
        List<String> monitorKeysList = new ArrayList<>();
        while (monitorKeys.hasMoreElements()) {
            String monitorKey = monitorKeys.nextElement();
            String monitorValue = resourceBundleMonitors.getString(monitorKey);
            monitorKeysList.add(monitorKey);
        }
        return monitorKeysList;
    }

    public List<String> getMonitorPriceList() {
        new WebDriverWait(driver, Duration.ofSeconds(WAIT_TIMEOUT_SECONDS))
                .until(ExpectedConditions.attributeContains(xpath(PRODUCT_CONTAINER_ELEMENT_XPATH),
                        "outerText", String.valueOf(getMonitorModelsListInResourceBundle())));
        return monitorPriceList.stream().sorted()
                .flatMap(monitorPrice -> monitorPrice.getAttribute("textContent").lines())
                .collect(Collectors.toList());
    }

    public List<String> getMonitorShortDescriptionList() {
        new WebDriverWait(driver, Duration.ofSeconds(WAIT_TIMEOUT_SECONDS))
                .until(ExpectedConditions.attributeContains(xpath(PRODUCT_CONTAINER_ELEMENT_XPATH),
                        "outerText", String.valueOf(getMonitorModelsListInResourceBundle())));
        return monitorDescriptionList.stream().sorted()
                .flatMap(monitorDescription -> monitorDescription.getAttribute("textContent").lines())
                .collect(Collectors.toList());
    }

    public int getSizeOfMonitorsList() {
        return monitorItemsList.size();
    }

    public String getMonitorPrice(int monitorItemIndexInTheList) {
        new WebDriverWait(driver, Duration.ofSeconds(WAIT_TIMEOUT_SECONDS))
                .until(ExpectedConditions.attributeContains(xpath(PRODUCT_CONTAINER_ELEMENT_XPATH),
                        "outerText", String.valueOf(getMonitorModelsListInResourceBundle())));
        logger.info("The price for the monitor index" + monitorItemIndexInTheList + "in the list is:"
                + monitorPriceList.get(monitorItemIndexInTheList).getAttribute("textContent"));
        return monitorPriceList.get(monitorItemIndexInTheList).getAttribute("textContent");
    }

    public String getMonitorModel(int monitorItemIndexInTheList) {
        new WebDriverWait(driver, Duration.ofSeconds(WAIT_TIMEOUT_SECONDS))
                .until(ExpectedConditions.attributeContains(xpath(PRODUCT_CONTAINER_ELEMENT_XPATH),
                        "outerText", String.valueOf(getMonitorModelsListInResourceBundle())));
        logger.info("The model of the monitor index" + monitorItemIndexInTheList + "in the list is:"
                + monitorHrefModelsList.get(monitorItemIndexInTheList).getAttribute("textContent"));
        return monitorHrefModelsList.get(monitorItemIndexInTheList).getAttribute("textContent");
    }

    public String getMonitorDescription(int monitorItemIndexInTheList) {
        new WebDriverWait(driver, Duration.ofSeconds(WAIT_TIMEOUT_SECONDS))
                .until(ExpectedConditions.attributeContains(xpath(PRODUCT_CONTAINER_ELEMENT_XPATH),
                        "outerText", String.valueOf(getMonitorModelsListInResourceBundle())));
        logger.info("The description of the monitor index" + monitorItemIndexInTheList + "in the list: "
                + monitorDescriptionList.get(monitorItemIndexInTheList).getAttribute("textContent"));
        return monitorDescriptionList.get(monitorItemIndexInTheList).getAttribute("textContent");
    }
}