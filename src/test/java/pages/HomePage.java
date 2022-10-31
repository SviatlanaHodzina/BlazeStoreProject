package pages;

import org.apache.commons.io.FileUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import webdriver.WebDriverConnector;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.time.Duration;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.lang.String.format;
import static org.openqa.selenium.By.xpath;
import static pages.LaptopsPage.LAPTOP_ITEM_MODEL_LIST_ELEMENT_XPATH;
import static pages.MonitorsPage.MONITOR_ITEM_MODEL_LIST_ELEMENT_XPATH;
import static pages.PhonesPage.*;

public class HomePage extends AbstractPage {
    public static final Logger logger = LogManager.getRootLogger();

    //methods for carousel-slides of items are not implemented yet
    final static String BLAZE_PRODUCT_STORE_URL = "https://www.demoblaze.com/";
    public final static String PATHNAME_PAGE = "/prod.html";
    final static String HOME_PAGE_NAVIGATING_MENU_ELEMENT_XPATH = "//*[@id='narvbarx']";
    public final static String PRODUCT_CATEGORIES_CONTAINER_ON_HOME_PAGE_ELEMENT_XPATH = "//*[@id='contcont']";
    public final static String LOGO_STORE_TITLE_ON_HOME_PAGE_ELEMENT_XPATH = "//*[@id='nava']//text()";
    public final static String HOME_LINK_IN_NAVIGATING_MENU_ELEMENT_XPATH = "//a[contains(@class,'nav-link') and contains(@href,'index.html')]";
    public final static String CONTACT_LINK_IN_NAVIGATING_MENU_ELEMENT_XPATH = "//a[contains(@class,'nav-link') and contains(@data-target,'#exampleModal')]";
    public final static String CONTACT_MODAL_WINDOW_SHOW_ELEMENT_XPATH = "//*[contains(@id,'exampleModal') and contains (@class,'modal fade show')]";
    public final static String ABOUT_US_LINK_IN_NAVIGATING_MENU_ELEMENT_XPATH = "//a[contains(@class,'nav-link') and contains(@data-target,'videoModal')]";
    public final static String ABOUT_US_MODAL_POPPED_UP_WINDOW_ELEMENT_XPATH = "//*[@id='videoModal']//*[@class='modal-content']";
    public final static String ABOUT_US_MODAL_WINDOW_TITLE_ELEMENT_XPATH = "//*[@id='videoModalLabel']";
    public final static String CART_LINK_IN_NAVIGATING_MENU_ELEMENT_XPATH = "//a[contains(@class,'nav-link') and contains(@href,'cart.html')]";
    public final static String CART_PAGE_WRAPPER_ELEMENT_XPATH = "//*[@id='page-wrapper']";
    public final static String LOG_IN_LINK_IN_NAVIGATING_MENU_ELEMENT_XPATH = "//a[contains(@class,'nav-link') and contains(@data-target,'logInModal')]";
    public final static String LOG_IN_MODAL_POPPED_UP_WINDOW_ELEMENT_XPATH = "//*[contains(@id,'logInModal') and contains(@style,'display: block;')]//*[@class='modal-content']";
    public final static String LOG_IN_PAGE_TITLE_ELEMENT_XPATH = "//*[@id='logInModalLabel']";
    final static String SIGN_UP_LINK_IN_NAVIGATING_MENU_ELEMENT_XPATH = "//a[contains(@class,'nav-link') and contains(@data-target,'signInModal')]";
    public final static String SIGN_UP_MODAL_POPPED_UP_WINDOW_ELEMENT_XPATH = "//*[contains(@id,'signInModal') and contains(@class,'modal fade show')]//*[@class='modal-content']";
    public final static String PRODUCT_CONTAINER_ELEMENT_XPATH = "//div[@id='tbodyid']";
    public final static String PRODUCT_ITEM_IN_A_ROW_ELEMENT_XPATH = "//div[@id='tbodyid']/div";
    public final static String PRODUCT_ITEM_MODEL_ELEMENT_XPATH = "//div[@class='card-block']//a";
    public final static String PRODUCT_ITEM_PRICE_ELEMENT_XPATH = "//div[@class='card-block']//h5";
    public final static String PRODUCT_ITEM_DESCRIPTION_ELEMENT_XPATH = "//div[@class='card-block']//p";
    public final static String CATEGORIES_CONTAINER_LINK_0N_SIDE_MENU_ELEMENT_XPATH = "//*[@id='cat']";
    public final static String PHONES_CATEGORY_LINK_0N_SIDE_MENU_ELEMENT_XPATH = "//*[contains(@id,'itemc') and contains(text(),'%s')]";
    public final static String MONITORS_CATEGORY_LINK_0N_SIDE_MENU_ELEMENT_XPATH = "//*[contains(@id,'itemc') and contains(text(),'%s')]";
    public final static String LAPTOPS_CATEGORY_LINK_0N_SIDE_MENU_ELEMENT_XPATH = "//*[contains(@id,'itemc') and contains(text(),'%s')]";

    @FindBy(how = How.TAG_NAME, using = "img")
    public static List<WebElement> imageList;

    @FindBy(how = How.XPATH, using = "//*[@id='tbodyid']/div")
    private static List<WebElement> homePageProductItemsList;

    public HomePage() throws MalformedURLException {
        super();
        PageFactory.initElements(this.driver, this);
    }

    @Override
    public HomePage openPage() {
        driver.navigate().to(BLAZE_PRODUCT_STORE_URL);
        new WebDriverWait(driver, Duration.ofSeconds(WAIT_TIMEOUT_SECONDS))
                .until(ExpectedConditions.visibilityOfElementLocated(By.xpath(HOME_PAGE_NAVIGATING_MENU_ELEMENT_XPATH)));
        logger.info("This is Blaze PRODUCT STORE Home Page");
        return this;
    }

    public int getSizeOfProductItemsList() {
        return homePageProductItemsList.size();
    }

    public String getLogoStoreTitleOnHomePage() {
        return driver.findElement(xpath(LOGO_STORE_TITLE_ON_HOME_PAGE_ELEMENT_XPATH)).getAttribute("textContent");
    }

    public ContactModalWindowPage openContactModalLinkOnTopNavigatingMenu() throws MalformedURLException {
        driver.switchTo().activeElement();
        new WebDriverWait(driver, Duration.ofSeconds(WAIT_TIMEOUT_SECONDS))
                .until(ExpectedConditions.elementToBeClickable
                        (xpath(CONTACT_LINK_IN_NAVIGATING_MENU_ELEMENT_XPATH))).click();
        new WebDriverWait(driver, Duration.ofSeconds(WAIT_TIMEOUT_SECONDS))
                .until(ExpectedConditions.visibilityOfElementLocated(xpath(CONTACT_MODAL_WINDOW_SHOW_ELEMENT_XPATH)));
        logger.info("You've been navigated to 'Contact' modal window");
        return new ContactModalWindowPage();
    }

    public AboutUsModalWindowPage openAboutUsModalWindowOnTopMenu() throws MalformedURLException {
        driver.switchTo().activeElement();
        new WebDriverWait(driver, Duration.ofSeconds(WAIT_TIMEOUT_SECONDS))
                .until(ExpectedConditions.elementToBeClickable
                        (xpath(ABOUT_US_LINK_IN_NAVIGATING_MENU_ELEMENT_XPATH))).click();
        new WebDriverWait(driver, Duration.ofSeconds(WAIT_TIMEOUT_SECONDS))
                .until(ExpectedConditions.visibilityOfElementLocated(xpath(ABOUT_US_MODAL_POPPED_UP_WINDOW_ELEMENT_XPATH)));
        logger.info("You've been navigated to 'About us' modal window");
        return new AboutUsModalWindowPage();
    }

    public LogInModalWindowPage openLogInModalWindowOnTopMenu() throws MalformedURLException {
        driver.switchTo().activeElement();
        new WebDriverWait(driver, Duration.ofSeconds(WAIT_TIMEOUT_SECONDS))
                .until(ExpectedConditions.elementToBeClickable
                        (xpath(LOG_IN_LINK_IN_NAVIGATING_MENU_ELEMENT_XPATH))).click();
        new WebDriverWait(driver, Duration.ofSeconds(WAIT_TIMEOUT_SECONDS))
                .until(ExpectedConditions.visibilityOfElementLocated(xpath(LOG_IN_MODAL_POPPED_UP_WINDOW_ELEMENT_XPATH)));
        logger.info("You've been navigated to 'Log in' modal window");
        return new LogInModalWindowPage();
    }

    public SignUpModalWindowPage openSignUpModalWindowOnTopMenu() throws MalformedURLException {
        driver.switchTo().activeElement();
        new WebDriverWait(driver, Duration.ofSeconds(WAIT_TIMEOUT_SECONDS))
                .until(ExpectedConditions.elementToBeClickable
                        (xpath(SIGN_UP_LINK_IN_NAVIGATING_MENU_ELEMENT_XPATH))).click();
        new WebDriverWait(driver, Duration.ofSeconds(WAIT_TIMEOUT_SECONDS))
                .until(ExpectedConditions.visibilityOfElementLocated(xpath(SIGN_UP_MODAL_POPPED_UP_WINDOW_ELEMENT_XPATH)));
        logger.info("You've been navigated to 'Sign up' modal window");
        return new SignUpModalWindowPage();
    }

    public CartPage openCartPage() throws MalformedURLException {
        new WebDriverWait(driver, Duration.ofSeconds(WAIT_TIMEOUT_SECONDS))
                .until(ExpectedConditions.elementToBeClickable
                        (xpath(CART_LINK_IN_NAVIGATING_MENU_ELEMENT_XPATH))).click();
        new WebDriverWait(driver, Duration.ofSeconds(WAIT_TIMEOUT_SECONDS))
                .until(ExpectedConditions.visibilityOfElementLocated(xpath(CART_PAGE_WRAPPER_ELEMENT_XPATH)));
        logger.info("You've been navigated to Cart Page");
        return new CartPage();
    }

    public PhonesPage openPhonesLinkOnSideMenu(String phone, List<String> laptops, List<String> monitors) throws MalformedURLException {

        new WebDriverWait(driver, Duration.ofSeconds(WAIT_TIMEOUT_SECONDS))
                .until(ExpectedConditions.elementToBeClickable
                        (xpath(format(PHONES_CATEGORY_LINK_0N_SIDE_MENU_ELEMENT_XPATH, phone)))).click();
        new WebDriverWait(driver, Duration.ofSeconds(WAIT_TIMEOUT_SECONDS))
                .until((ExpectedConditions.invisibilityOfElementLocated(xpath(format(LAPTOP_ITEM_MODEL_LIST_ELEMENT_XPATH, laptops)))));
        new WebDriverWait(driver, Duration.ofSeconds(WAIT_TIMEOUT_SECONDS))
                .until((ExpectedConditions.invisibilityOfElementLocated(xpath(format(MONITOR_ITEM_MODEL_LIST_ELEMENT_XPATH, monitors)))));
        logger.info("You've been navigated to 'Phones' page");
        return new PhonesPage();
    }

    public LaptopsPage openLaptopsLinkOnSideMenu(String laptops) throws MalformedURLException {
        new WebDriverWait(driver, Duration.ofSeconds(WAIT_TIMEOUT_SECONDS))
                .until(ExpectedConditions.elementToBeClickable
                        (xpath(format(LAPTOPS_CATEGORY_LINK_0N_SIDE_MENU_ELEMENT_XPATH, laptops)))).click();
        new WebDriverWait(driver, Duration.ofSeconds(WAIT_TIMEOUT_SECONDS))
                .until(ExpectedConditions.attributeContains(xpath(PRODUCT_CONTAINER_ELEMENT_XPATH),
                        "outerText", String.valueOf(getLaptopModelsListInResourceBundle())));
        logger.info("You've been navigated to 'Laptops' row");
        return new LaptopsPage();
    }

    public MonitorsPage openMonitorsLinkOnSideMenu(String monitors) throws MalformedURLException {
        ResourceBundle resourceBundlePhones = ResourceBundle.getBundle("monitors");
        new WebDriverWait(driver, Duration.ofSeconds(WAIT_TIMEOUT_SECONDS))
                .until(ExpectedConditions.elementToBeClickable
                        (xpath(format(MONITORS_CATEGORY_LINK_0N_SIDE_MENU_ELEMENT_XPATH, monitors)))).click();
        new WebDriverWait(driver, Duration.ofSeconds(WAIT_TIMEOUT_SECONDS))
                .until(ExpectedConditions.attributeContains(xpath(PRODUCT_CONTAINER_ELEMENT_XPATH),
                        "outerText", String.valueOf(getLaptopModelsListInResourceBundle())));
        logger.info("You've been navigated to 'Laptops' row");
        return new MonitorsPage();
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
                }
            }
        }
        return imagesBrokenCount;
    }

    private static String getCurrentTimeAsString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("uuuu-MM-dd_HH-mm-ss");
        return ZonedDateTime.now().format(formatter);
    }

    static void saveScreenShot() throws MalformedURLException {
        File screenCapture = ((TakesScreenshot) WebDriverConnector
                .getDriver("browser"))
                .getScreenshotAs(OutputType.FILE);
        try {
            FileUtils.copyFile(screenCapture,
                    new File(".//brokenImages_screenshots/" + getCurrentTimeAsString() + ".png"));
        } catch (IOException e) {
            logger.error("Failed to save screenshot: " + e.getLocalizedMessage());
        }
    }

    public static void takeTheBrokenImageScreenshotOnHomePage() throws IOException {
        for (WebElement image : imageList) {
            if (image != null) {
                HttpClient client = HttpClientBuilder.create().build();
                HttpGet request = new HttpGet(image.getAttribute("src"));
                HttpResponse response = client.execute(request);
                if (response.getStatusLine().getStatusCode() != 200) {
                    saveScreenShot();
                }
            }
        }
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

    public static List<String> getLaptopModelsListInResourceBundle() {
        ResourceBundle resourceBundleLaptops = ResourceBundle.getBundle("laptops");
        Enumeration<String> laptopKeys = resourceBundleLaptops.getKeys();
        List<String> laptopKeysList = new ArrayList<>();
        while (laptopKeys.hasMoreElements()) {
            String laptopKey = laptopKeys.nextElement();
            String laptopValue = resourceBundleLaptops.getString(laptopKey);
            laptopKeysList.add(laptopValue);
        }
        return laptopKeysList.stream().sorted().collect(Collectors.toList());
    }

    public static List<String> getMonitorModelsListInResourceBundle() {
        ResourceBundle resourceBundleMonitors = ResourceBundle.getBundle("monitors");
        Enumeration<String> monitorKeys = resourceBundleMonitors.getKeys();
        List<String> monitorKeysList = new ArrayList<>();
        while (monitorKeys.hasMoreElements()) {
            String monitorKey = monitorKeys.nextElement();
            String monitorValue = resourceBundleMonitors.getString(monitorKey);
            monitorKeysList.add(monitorValue);
        }
        return monitorKeysList.stream().sorted().collect(Collectors.toList());
    }

    public static Map<String, Integer> getPhoneModelsPriceMapInResourceBundle() {
        ResourceBundle resourceBundlePhonePrices = ResourceBundle.getBundle("PhonesPriceListDollarUSA");
        ResourceBundle resourceBundlePhones = ResourceBundle.getBundle("phones");
        Enumeration<String> phonePriceKeys = resourceBundlePhonePrices.getKeys();
        Enumeration<String> phoneKeys = resourceBundlePhonePrices.getKeys();
        Map<String, Integer> phoneKeysMap = new HashMap<>();
        while (phonePriceKeys.hasMoreElements()) {
            while (phoneKeys.hasMoreElements()) {
                String pricePhoneKey = phonePriceKeys.nextElement();
                String modelPhoneKey = phoneKeys.nextElement();
                int phonePriceValue = Integer.parseInt((resourceBundlePhonePrices.getString(pricePhoneKey)).replace("$", ""));
                String phoneModelKey = resourceBundlePhones.getString(modelPhoneKey);
                phoneKeysMap.put(phoneModelKey, phonePriceValue);
            }
        }
        return phoneKeysMap;
    }
}
