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
    @Test(testName = "AboutUsModalWindowOpeningTestByTitle", suiteName = "AboutUsModalWindowPageTest",
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
    @Test(testName = "VideoPlayerWindowPresenceTest",
            suiteName = "AboutUsModalWindowPageTest", groups = {"positive"},
            description = "Verifies that the video player window is present in 'AboutUsModalWindow'")
    public void verifyThatTheVideoPlayerIsPresentInAboutUsModalWindow()
            throws MalformedURLException {

        boolean videoPlayerIsPresentInTheAboutUsWindow = new AboutUsModalWindowPage()
                .videoPlayerWindowIsPresentInTheAboutUsWindow();
        String errorMessageIfTestFails = "Video player window is not displayed in the 'AboutUsWindowPage'";

        assertTrue(errorMessageIfTestFails, videoPlayerIsPresentInTheAboutUsWindow);
    }

    @Parameters("browser")
    @Test(testName = "VideoExamplePresenceTest",
            suiteName = "AboutUsModalWindowPageTest", groups = {"positive"},
            description = "Verifies that the video example is present in 'AboutUsModalWindow'")
    public void verifyThatTheVideoExampleIsPresentInAboutUsModalWindow()
            throws MalformedURLException {

        boolean videoExampleIsPresentInTheAboutUsWindow = new AboutUsModalWindowPage()
                .videoExampleIsPresentInTheAboutUsWindow();
        String errorMessageIfTestFails = "Video example is not displayed in the 'AboutUsWindowPage'";

        assertTrue(errorMessageIfTestFails, videoExampleIsPresentInTheAboutUsWindow);
    }

    @Parameters("browser")
    @Test(testName = "VideoFileBlobURLTest",
            suiteName = "AboutUsModalWindowPageTest", groups = {"positive"},
            description = "Verifies that the actual video file complies with the resource file in generated Blob URL")
    public void verifyThatActualVideoFileCompliesWithTheResourceFileInGeneratedBlobURL()
            throws MalformedURLException {
        ResourceBundle resourceBundle = ResourceBundle.getBundle("VisibleContent", Locale.US);

        String actualVideoFile =
                new AboutUsModalWindowPage()
                        .getVideoFileResourceOfTheVideoExample();

        String expectedVideoFile = resourceBundle.getString("VideoExampleVideoFileBlobURL");
        String errorMessageIfTestFails = "Actual video file doesn't comply with the resource file in generated blob URL";

        assertTrue(errorMessageIfTestFails, actualVideoFile.contains(expectedVideoFile));
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

        boolean fullscreenModeOfTheVideoExampleInAboutUsWindowIsActivated =
                new AboutUsModalWindowPage()
                        .startPlayingVideoExample()
                        .activateFullscreenMode()
                        .fullscreenModeIsActivated();

        String errorMessageIfTestFails = "The 'Fullscreen' mode of video example activation " +
                "in 'About us' window isn't available.";

        assertTrue(errorMessageIfTestFails, fullscreenModeOfTheVideoExampleInAboutUsWindowIsActivated);
    }

    @Parameters("browser")
    @Test(testName = "FullscreenModeDeactivationTest",
            suiteName = "AboutUsModalWindowPageTest", groups = {"positive"},
            description = "Verifies if the 'Fullscreen' mode of the video example deactivation " +
                    "is available in 'About us' window")
    public void verifyThatUserCanDeactivateFullscreenModeOfTheVideoExampleInAboutUsWindow()
            throws MalformedURLException {

        boolean fullscreenModeOfTheVideoExampleInAboutUsWindowIsDeactivated =
                new AboutUsModalWindowPage()
                        .startPlayingVideoExample()
                        .activateFullscreenMode()
                        .deactivateFullscreenMode()
                        .fullscreenModeIsDeactivated();

        String errorMessageIfTestFails = "The 'Fullscreen' mode of video example deactivation " +
                "in 'About us' window isn't available.";

        assertTrue(errorMessageIfTestFails, fullscreenModeOfTheVideoExampleInAboutUsWindowIsDeactivated);
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
                        .startPlayingVideoExample()
                        .activatePictureInPictureMode()
                        .pictureInPictureModeIsActivated();

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
                        .startPlayingVideoExample()
                        .activatePictureInPictureMode()
                        .deactivatePictureInPictureMode()
                        .pictureInPictureModeIsDeactivated();

        String errorMessageIfTestFails = "The 'Picture-in-Picture' mode of video example deactivation" +
                "in 'About us' window isn't available.";

        assertTrue(errorMessageIfTestFails, userCanDeactivatePictureInPictureMode);
    }
}
