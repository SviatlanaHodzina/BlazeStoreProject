package tests;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pages.AboutUsModalWindowPage;
import pages.HomePage;

import java.net.MalformedURLException;
import java.util.Locale;
import java.util.ResourceBundle;

import static org.testng.AssertJUnit.assertEquals;
import static org.testng.AssertJUnit.assertTrue;

public class AboutUsModalWindowPageTest extends RequiredConditions {
    @Parameters("browser")
    @BeforeTest
    public void openAboutUsModalWindowLinkOnTopNavigatingMenu() throws MalformedURLException {
        new HomePage()
                .openPage()
                .openAboutUsModalWindowOnTopMenu();
    }

    @Parameters("browser")
    @Test(testName = "AboutUsModalWindowTitleTest", suiteName = "AboutUsModalWindowPageTest",
            groups = {"positive"}, description = "Verifies if a user can open 'About us' modal " +
            "window link on top navigating menu with Video example")
    public void verifyThatUserCanOpenAboutUsModalWindowLinkOnTopNavigatingMenu() throws MalformedURLException {
        ResourceBundle resourceBundle = ResourceBundle.getBundle("VisibleContent", Locale.US);

        String actualAboutUsModalWindowTitle = new AboutUsModalWindowPage().getAboutUsModalWindowTitle();
        String expectedAboutUsModalWindowTitle = resourceBundle.getString("AboutUsModalWindowTitle");

        String errorMessageIfTestFails = "The 'About us' link on top menu is not accessible " +
                "or you've opened another link or the mentioned opened window has another title";

        assertEquals(errorMessageIfTestFails, expectedAboutUsModalWindowTitle, actualAboutUsModalWindowTitle);
    }

    @Parameters("browser")
    @Test(testName = "StartOfVideoExamplePlayingTest", suiteName = "AboutUsModalWindowPageTest",
            groups = {"positive"}, description = "Verifies if the video example in 'About us' " +
            "window can be started to play")
    public void verifyThatUserCanStartPlayingVideoExampleInAboutUsWindow() throws MalformedURLException {

        boolean userCanStartPlayingVideoExampleInAboutUsWindow = new AboutUsModalWindowPage()
                .bigButtonPlayInVideoExampleStartsTheVideoExample();

        String errorMessageIfTestFails = "The Video example does not start to play";

        assertTrue(errorMessageIfTestFails, userCanStartPlayingVideoExampleInAboutUsWindow);
    }

    @Parameters("browser")
    @Test(testName = "FullscreenModeActivationTest", suiteName = "AboutUsModalWindowPageTest",
            groups = {"positive"}, description = "Verifies if the 'Fullscreen' mode of the video example" +
            " activation is available in 'About us' window")
    public void verifyThatUserCanActivateFullscreenModeOfTheVideoExampleInAboutUsWindow()
            throws MalformedURLException {

        boolean userCanActivateFullscreenModeOfTheVideoExampleInAboutUsWindow =
                new AboutUsModalWindowPage()
                        .activateFullscreenMode().fullscreenModeIsActivated();

        String errorMessageIfTestFails = "The 'Fullscreen' mode of video example activation " +
                "in 'About us' window isn't available.";

        assertTrue(errorMessageIfTestFails, userCanActivateFullscreenModeOfTheVideoExampleInAboutUsWindow);
    }

    @Parameters("browser")
    @Test(testName = "FullscreenModeDeactivationTest",
            suiteName = "AboutUsModalWindowPageTest", groups = {"positive"},
            description = "Verifies if the 'Fullscreen' mode of the video example deactivation " +
                    "is available in 'About us' window")
    public void verifyThatUserCanDeactivateFullscreenModeOfTheVideoExampleInAboutUsWindow()
            throws MalformedURLException {

        boolean userCanDeactivateFullscreenModeOfTheVideoExampleInAboutUsWindow =
                new AboutUsModalWindowPage()
                        .deactivateFullscreenMode().fullscreenModeIsDeactivated();

        String errorMessageIfTestFails = "The 'Fullscreen' mode of video example deactivation " +
                "in 'About us' window isn't available.";

        assertTrue(errorMessageIfTestFails, userCanDeactivateFullscreenModeOfTheVideoExampleInAboutUsWindow);
    }

    @Parameters("browser")
    @Test(testName = "PicInPicModeActivationTest",
            suiteName = "AboutUsModalWindowPageTest", groups = {"positive"},
            description = "Verifies if the 'Picture-in-Picture' mode of the video example activation " +
                    "is available in 'About us' window")
    public void verifyThatUserCanActivatePictureInPictureModeOfTheVideoExampleInAboutUsWindow()
            throws MalformedURLException {

        boolean userCanActivatePictureInPictureModeOfTheVideoExampleInAboutUsWindow =
                new AboutUsModalWindowPage()
                        .activatePictureInPictureMode().pictureInPictureModeIsActivated();

        String errorMessageIfTestFails = "The 'Picture-in-Picture' mode of video example activation " +
                "in 'About us' window isn't available.";

        assertTrue(errorMessageIfTestFails, userCanActivatePictureInPictureModeOfTheVideoExampleInAboutUsWindow);
    }

    @Parameters("browser")
    @Test(testName = "PicInPicModeDeactivationTest",
            suiteName = "AboutUsModalWindowPageTest", groups = {"positive"},
            description = "Verifies if the 'Picture-in-Picture' mode of the video example deactivation" +
                    " is available in 'About us' window")
    public void verifyThatUserCanDeactivatePictureInPictureModeOfTheVideoExampleInAboutUsWindow()
            throws MalformedURLException {

        boolean userCanDeactivatePictureInPictureMode =
                new AboutUsModalWindowPage()
                        .deactivatePictureInPictureMode().pictureInPictureModeIsDeactivated();

        String errorMessageIfTestFails = "The 'Picture-in-Picture' mode of video example deactivation" +
                "in 'About us' window isn't available.";

        assertTrue(errorMessageIfTestFails, userCanDeactivatePictureInPictureMode);
    }
}