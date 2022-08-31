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

public class AboutUsModalWindowPage extends AbstractPage {

    public final Logger logger = LogManager.getRootLogger();
    public final static String ABOUT_US_MODAL_WINDOW_SHOW_ELEMENT_XPATH = "//div[contains(@id,'videoModal') and contains(@class,'modal fade show')]";
    public final static String ABOUT_US_MODAL_WINDOW_CLOSED_ELEMENT_XPATH = "//div[contains(@id,'videoModal') and contains(@class,'modal fade')]";
    public final static String VIDEO_EXAMPLE_IN_VIDEO_PLAYER_ELEMENT_XPATH = "//*[@id='example-video_html5_api']";
    public final static String VIDEO_PLAYER_ELEMENT_XPATH = "//div[@id='example-video']";
    public final static String VIDEO_PLAYER_BIG_BUTTON_PLAY_VIDEO_ELEMENT_XPATH = "//button[@title='Play Video']";
    public final static String VIDEO_PLAYER_CONTROL_BAR_BUTTON_PLAY_IN_PAUSED_STATE_ELEMENT_XPATH = "//button[@title='Play']";
    public final static String VIDEO_PLAYER_CONTROL_BAR_BUTTON_PLAY_IN_PLAYING_STATE_ELEMENT_XPATH = "//button[@title='Pause']";
    public final static String VIDEO_PLAYER_BUTTON_MUTE_IN_UNMUTED_STATUS_ELEMENT_XPATH = "//button[@title='Mute']";
    public final static String VIDEO_PLAYER_BUTTON_MUTE_IN_MUTED_STATUS_ELEMENT_XPATH = "//button[@title='Unmute']";
    public final static String VIDEO_PLAYER_PROGRESS_SLIDER_ELEMENT_XPATH = "//div[@aria-label='Progress Bar']";
    public final static String VIDEO_PLAYER_VOLUME_SLIDER_ELEMENT_XPATH = "//div[@aria-label='Volume Level']";// add method for the slider
    public final static String VIDEO_PLAYER_REMAINING_TIME_IN_PLAYING_STATUS_ELEMENT_XPATH = "//div[@class='vjs-remaining-time vjs-time-control vjs-control']";// add method for the timer
    public final static String VIDEO_PLAYER_BUTTON_PICTURE_IN_PICTURE_IN_INACTIVE_MODE_IN_VIDEO_PLAYER_ELEMENT_XPATH = "//button[@title='Picture-in-Picture']";
    public final static String VIDEO_PLAYER_BUTTON_PICTURE_IN_PICTURE_IN_ACTIVE_MODE_ELEMENT_XPATH = "//button[@title='Exit Picture-in-Picture']";
    public final static String VIDEO_PLAYER_BUTTON_FULLSCREEN_IN_INACTIVE_FULLSCREEN_MODE_ELEMENT_XPATH = "//button[@title='Fullscreen']";
    public final static String VIDEO_PLAYER_BUTTON_FULLSCREEN_IN_ACTIVE_FULLSCREEN_MODE_ELEMENT_XPATH = "//button[@title='Non-Fullscreen']";
    public final static String ABOUT_US_BUTTON_CLOSE_FOOTER_ELEMENT_XPATH = "//div[@id='videoModal']//div[@class='modal-footer']//button[@data-dismiss='modal']";
    public final static String ABOUT_US_BUTTON_CLOSE_X_ONTOP_ELEMENT_XPATH = "//div[@id='videoModal']//div[@class='modal-header']//button[@data-dismiss='modal']";


    public AboutUsModalWindowPage() throws MalformedURLException {
        super();
        PageFactory.initElements(this.driver, this);
    }

    @Override
    public AboutUsModalWindowPage openPage() {
        new WebDriverWait(driver, Duration.ofSeconds(WAIT_TIMEOUT_SECONDS))
                .until(ExpectedConditions.visibilityOfElementLocated
                        (By.xpath(ABOUT_US_MODAL_WINDOW_SHOW_ELEMENT_XPATH)));
        logger.info("You've navigated to 'About us' modal window");
        return this;
    }

    public String getAboutUsModalWindowTitle() {
        return driver.findElement(xpath(ABOUT_US_MODAL_WINDOW_TITLE_ELEMENT_XPATH))
                .getAttribute("textContent");
    }

    public boolean videoPlayerWindowIsPresentInTheAboutUsWindow() {
        return driver.findElement(xpath(VIDEO_PLAYER_ELEMENT_XPATH)).isDisplayed();
    }

    public boolean videoExampleIsPresentInTheAboutUsWindow() {
        return driver.findElement(xpath(VIDEO_PLAYER_ELEMENT_XPATH)).isDisplayed();
    }

    public String getVideoFileResourceOfTheVideoExample() {
        return driver.findElement(xpath(VIDEO_EXAMPLE_IN_VIDEO_PLAYER_ELEMENT_XPATH)).getAttribute("currentSrc");
    }

    public AboutUsModalWindowPage startPlayingVideoExample() {
        new WebDriverWait(driver, Duration.ofSeconds(WAIT_TIMEOUT_SECONDS))
                .until(ExpectedConditions.elementToBeClickable
                        (By.xpath(VIDEO_PLAYER_BIG_BUTTON_PLAY_VIDEO_ELEMENT_XPATH))).click();
        new WebDriverWait(driver, Duration.ofSeconds(WAIT_TIMEOUT_SECONDS))
                .until(ExpectedConditions.visibilityOfElementLocated
                        (By.xpath(VIDEO_PLAYER_CONTROL_BAR_BUTTON_PLAY_IN_PLAYING_STATE_ELEMENT_XPATH)));
        logger.info("The Video example has just started");
        return this;
    }

    public AboutUsModalWindowPage playVideoExampleFromControlBar() {
        new WebDriverWait(driver, Duration.ofSeconds(WAIT_TIMEOUT_SECONDS))
                .until(ExpectedConditions.elementToBeClickable
                        (By.xpath(VIDEO_PLAYER_CONTROL_BAR_BUTTON_PLAY_IN_PAUSED_STATE_ELEMENT_XPATH))).click();
        logger.info("The Video example has just started to play");
        return this;
    }

    public AboutUsModalWindowPage pauseVideoExampleFromControlBar() {
        new WebDriverWait(driver, Duration.ofSeconds(WAIT_TIMEOUT_SECONDS))
                .until(ExpectedConditions.elementToBeClickable
                        (By.xpath(VIDEO_PLAYER_CONTROL_BAR_BUTTON_PLAY_IN_PLAYING_STATE_ELEMENT_XPATH))).click();
        logger.info("The Video example has just paused");
        return this;
    }

    public boolean videoExampleIsPaused() {
        return new WebDriverWait(driver, Duration.ofSeconds(WAIT_TIMEOUT_SECONDS))
                .until(ExpectedConditions.elementToBeClickable
                        (By.xpath(VIDEO_PLAYER_CONTROL_BAR_BUTTON_PLAY_IN_PAUSED_STATE_ELEMENT_XPATH)))
                .getAttribute("Title").matches("Play");
    }

    public boolean videoExampleIsPlaying() {
        return new WebDriverWait(driver, Duration.ofSeconds(WAIT_TIMEOUT_SECONDS))
                .until(ExpectedConditions.elementToBeClickable
                        (By.xpath(VIDEO_PLAYER_CONTROL_BAR_BUTTON_PLAY_IN_PLAYING_STATE_ELEMENT_XPATH)))
                .getAttribute("Title").matches("Pause");
    }

    //    look up timer methods to implement it with timer
    public boolean sliderIsInProgressWhenTheVideoExamplePlays() {
        return !driver.findElement(xpath(VIDEO_PLAYER_PROGRESS_SLIDER_ELEMENT_XPATH))
                .getAttribute("ariaValueNow").equals("0.00");
    }

    public boolean bigButtonPlayInVideoExampleStartsTheVideoExample() {
        startPlayingVideoExample();
        return !driver.findElement(xpath(VIDEO_PLAYER_PROGRESS_SLIDER_ELEMENT_XPATH))
                .getAttribute("ariaValueNow").equals("00.00");
    }

    public AboutUsModalWindowPage activatePictureInPictureMode() {
        new WebDriverWait(driver, Duration.ofSeconds(WAIT_TIMEOUT_SECONDS))
                .until(ExpectedConditions.elementToBeClickable
                        (By.xpath(VIDEO_PLAYER_BUTTON_PICTURE_IN_PICTURE_IN_INACTIVE_MODE_IN_VIDEO_PLAYER_ELEMENT_XPATH)))
                .click();
        new WebDriverWait(driver, Duration.ofSeconds(WAIT_TIMEOUT_SECONDS))
                .until(ExpectedConditions.visibilityOfElementLocated
                        (By.xpath(VIDEO_PLAYER_BUTTON_PICTURE_IN_PICTURE_IN_ACTIVE_MODE_ELEMENT_XPATH)));
        logger.info("You have activated 'Picture-in-Picture' video mode");
        return this;
    }

    public boolean pictureInPictureModeIsActivated() {
        return driver.findElement(xpath(VIDEO_EXAMPLE_IN_VIDEO_PLAYER_ELEMENT_XPATH))
                .getAttribute("innerText").contains("Exit Picture-in-Picture");
    }

    public AboutUsModalWindowPage deactivatePictureInPictureMode() {
        new WebDriverWait(driver, Duration.ofSeconds(WAIT_TIMEOUT_SECONDS))
                .until(ExpectedConditions.elementToBeClickable
                        (By.xpath(VIDEO_PLAYER_BUTTON_PICTURE_IN_PICTURE_IN_ACTIVE_MODE_ELEMENT_XPATH)))
                .click();
        new WebDriverWait(driver, Duration.ofSeconds(WAIT_TIMEOUT_SECONDS))
                .until(ExpectedConditions.visibilityOfElementLocated
                        (By.xpath(VIDEO_PLAYER_BUTTON_PICTURE_IN_PICTURE_IN_INACTIVE_MODE_IN_VIDEO_PLAYER_ELEMENT_XPATH)));
        logger.info("You have deactivated 'Picture-in-Picture' video mode");
        return this;
    }

    public boolean pictureInPictureModeIsDeactivated() {
        return driver.findElement(xpath(VIDEO_EXAMPLE_IN_VIDEO_PLAYER_ELEMENT_XPATH))
                .getAttribute("innerText").contains("Picture-in-Picture");
    }

    public AboutUsModalWindowPage activateFullscreenMode() {
        new WebDriverWait(driver, Duration.ofSeconds(WAIT_TIMEOUT_SECONDS))
                .until(ExpectedConditions.elementToBeClickable
                        (By.xpath(VIDEO_PLAYER_BUTTON_FULLSCREEN_IN_INACTIVE_FULLSCREEN_MODE_ELEMENT_XPATH))).click();
        new WebDriverWait(driver, Duration.ofSeconds(WAIT_TIMEOUT_SECONDS))
                .until(ExpectedConditions.visibilityOfElementLocated
                        (By.xpath(VIDEO_PLAYER_BUTTON_FULLSCREEN_IN_ACTIVE_FULLSCREEN_MODE_ELEMENT_XPATH)));
        logger.info("You have activated 'Fullscreen' video mode");
        return this;
    }

    public boolean fullscreenModeIsActivated() {
        return driver.findElement(xpath(VIDEO_EXAMPLE_IN_VIDEO_PLAYER_ELEMENT_XPATH))
                .getAttribute("innerText").contains("Non-Fullscreen");
    }

    public AboutUsModalWindowPage deactivateFullscreenMode() {
        new WebDriverWait(driver, Duration.ofSeconds(WAIT_TIMEOUT_SECONDS))
                .until(ExpectedConditions.elementToBeClickable
                        (By.xpath(VIDEO_PLAYER_BUTTON_FULLSCREEN_IN_ACTIVE_FULLSCREEN_MODE_ELEMENT_XPATH))).click();
        new WebDriverWait(driver, Duration.ofSeconds(WAIT_TIMEOUT_SECONDS))
                .until(ExpectedConditions.visibilityOfElementLocated
                        (By.xpath(VIDEO_PLAYER_BUTTON_FULLSCREEN_IN_INACTIVE_FULLSCREEN_MODE_ELEMENT_XPATH)));
        logger.info("You have deactivated 'Fullscreen' video mode");
        return this;
    }

    public boolean fullscreenModeIsDeactivated() {
        return driver.findElement(xpath(VIDEO_EXAMPLE_IN_VIDEO_PLAYER_ELEMENT_XPATH))
                .getAttribute("innerText").contains("Fullscreen");
    }

    public AboutUsModalWindowPage muteTheAudioStreamInTheVideoExample() {
        new WebDriverWait(driver, Duration.ofSeconds(WAIT_TIMEOUT_SECONDS))
                .until(ExpectedConditions.elementToBeClickable
                        (By.xpath(VIDEO_PLAYER_BUTTON_MUTE_IN_UNMUTED_STATUS_ELEMENT_XPATH))).click();
        new WebDriverWait(driver, Duration.ofSeconds(WAIT_TIMEOUT_SECONDS))
                .until(ExpectedConditions.visibilityOfElementLocated
                        (By.xpath(VIDEO_PLAYER_BUTTON_MUTE_IN_MUTED_STATUS_ELEMENT_XPATH)));
        logger.info("You have muted audio stream in video example");
        return this;
    }

    public boolean videoExampleIsMuted() {
        return driver.findElement(xpath(VIDEO_PLAYER_BUTTON_MUTE_IN_MUTED_STATUS_ELEMENT_XPATH))
                .getAttribute("textContent").contains("Unmute");
    }

    public AboutUsModalWindowPage unmuteTheAudioStreamInTheVideoExample() {
        new WebDriverWait(driver, Duration.ofSeconds(WAIT_TIMEOUT_SECONDS))
                .until(ExpectedConditions.elementToBeClickable
                        (By.xpath(VIDEO_PLAYER_BUTTON_MUTE_IN_MUTED_STATUS_ELEMENT_XPATH))).click();
        new WebDriverWait(driver, Duration.ofSeconds(WAIT_TIMEOUT_SECONDS))
                .until(ExpectedConditions.visibilityOfElementLocated
                        (By.xpath(VIDEO_PLAYER_BUTTON_MUTE_IN_UNMUTED_STATUS_ELEMENT_XPATH)));
        logger.info("You have muted audio stream in video example");
        return this;
    }

    public boolean videoExampleIsUnmuted() {
        return driver.findElement(xpath(VIDEO_PLAYER_BUTTON_MUTE_IN_UNMUTED_STATUS_ELEMENT_XPATH))
                .getAttribute("title").contains("Mute");
    }

    public AboutUsModalWindowPage closeAboutUsWindowWithVideoExampleInTheFooter() {
        new WebDriverWait(driver, Duration.ofSeconds(WAIT_TIMEOUT_SECONDS))
                .until(ExpectedConditions.elementToBeClickable
                        (By.xpath(ABOUT_US_BUTTON_CLOSE_FOOTER_ELEMENT_XPATH))).click();
        new WebDriverWait(driver, Duration.ofSeconds(WAIT_TIMEOUT_SECONDS))
                .until(ExpectedConditions.visibilityOfElementLocated
                        (By.xpath(ABOUT_US_MODAL_WINDOW_CLOSED_ELEMENT_XPATH)));
        return this;
    }

    public AboutUsModalWindowPage closeAboutUsWindowWithVideoExampleOnTop() {
        new WebDriverWait(driver, Duration.ofSeconds(WAIT_TIMEOUT_SECONDS))
                .until(ExpectedConditions.elementToBeClickable
                        (By.xpath(ABOUT_US_BUTTON_CLOSE_X_ONTOP_ELEMENT_XPATH))).click();
        new WebDriverWait(driver, Duration.ofSeconds(WAIT_TIMEOUT_SECONDS))
                .until(ExpectedConditions.visibilityOfElementLocated
                        (By.xpath(ABOUT_US_MODAL_WINDOW_CLOSED_ELEMENT_XPATH)));
        return this;
    }

    public boolean aboutUsModalWindowPageIsClosed() {
        return driver.findElement(xpath(ABOUT_US_MODAL_WINDOW_CLOSED_ELEMENT_XPATH)).isDisplayed();
    }
}
