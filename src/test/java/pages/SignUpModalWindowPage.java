package pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.MalformedURLException;
import java.time.Duration;

import static org.openqa.selenium.By.xpath;
import static pages.HomePage.*;

//Methods with variables are not implemented yet
public class SignUpModalWindowPage extends AbstractPage {
    public final Logger logger = LogManager.getRootLogger();

    public SignUpModalWindowPage() throws MalformedURLException {
        super();
        PageFactory.initElements(this.driver, this);
    }

    @Override
    public SignUpModalWindowPage openPage() {
        new WebDriverWait(driver, Duration.ofSeconds(WAIT_TIMEOUT_SECONDS))
                .until(ExpectedConditions.visibilityOfElementLocated
                        (By.xpath(SIGN_UP_MODAL_POPPED_UP_WINDOW_ELEMENT_XPATH)));
        logger.info("You've navigated to 'Sign up' modal window");
        return this;
    }

    public String getSignUpModalWindowHeader() {
        return driver.findElement(xpath(SIGN_UP_MODAL_WINDOW_TITLE_ELEMENT_XPATH))
                .getAttribute("textContent");
    }
}