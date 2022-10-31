package pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.MalformedURLException;
import java.time.Duration;
import java.util.ResourceBundle;

import static java.lang.String.format;
import static org.openqa.selenium.By.xpath;
import static pages.HomePage.*;

//write code here
// think how to make the page universal for all products using ordered queryl
public class Phone5Page extends AbstractPage {

    public final Logger logger = LogManager.getRootLogger();
    static ResourceBundle resourceBundlePhone5 = ResourceBundle.getBundle("phone5");
    public static final String SEARCH_QUERY = resourceBundlePhone5.getString("query");
    public final static String HREF_BASE_URI = BLAZE_PRODUCT_STORE_URL + PATHNAME_PAGE + SEARCH_QUERY;

    public final static String PHONE_MODEL_PAGE_BODY_ELEMENT_XPATH = "/html/body";
    public final static String PHONE_MODEL_PAGE_TITLE_ELEMENT_XPATH = "//div[@id='tbodyid']/h2[contains(text(),'%s')]";//for checking the title


    public Phone5Page() throws MalformedURLException {
        super();
        PageFactory.initElements(this.driver, this);
    }

    @Override
    public Phone5Page openPage() {
        new WebDriverWait(driver, Duration.ofSeconds(WAIT_TIMEOUT_SECONDS))
                .until(ExpectedConditions.attributeContains(xpath(PHONE_MODEL_PAGE_BODY_ELEMENT_XPATH),
                        "baseURI", HREF_BASE_URI));
        logger.info("PhonesPage, containing phones items is opened");
        return this;
    }
    // code
}
