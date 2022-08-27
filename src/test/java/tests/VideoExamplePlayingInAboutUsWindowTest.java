package tests;

import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pages.AboutUsModalWindowPage;
import pages.HomePage;

import java.net.MalformedURLException;

import static org.testng.AssertJUnit.assertTrue;

public class VideoExamplePlayingInAboutUsWindowTest extends RequiredConditions {
    @Test(description = "Verifies if a user can play the video example in 'About us' window starting with big button 'Play video'.")
    @Parameters("browser")
    public void verifyThatUserCanPlayTheVideoExampleInAboutUsWindowStartingWithBigButtonPlayVideo()
            throws MalformedURLException {
        new HomePage()
                .openPage()
                .openAboutUsModalWindowOnTopMenu();
        boolean userCanPlayTheVideoExampleInAboutUsWindowStartingWithBigButtonPlayVideo =
                new AboutUsModalWindowPage().startPlayingVideoExample().sliderIsInProgressWhenTheVideoExamplePlays();

        String errorMessageIfTestFails = "The video example in 'About us' window doesn't start play video track.";

        assertTrue(errorMessageIfTestFails, userCanPlayTheVideoExampleInAboutUsWindowStartingWithBigButtonPlayVideo);
    }
}