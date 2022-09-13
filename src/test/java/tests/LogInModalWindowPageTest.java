package tests;

import customer.Customer;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WindowType;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pages.CustomerAccountPage;

import pages.HomePage;
import pages.LogInModalWindowPage;
import storedataservice.CustomerAccountCompiler;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Locale;
import java.util.ResourceBundle;

import static org.testng.AssertJUnit.*;

// add API test methods verifying response code
// add other methods from LogIn page
public class LogInModalWindowPageTest extends RequiredConditions {
    @Parameters("browser")
    @BeforeTest
    public void openLogInModalWindow() throws MalformedURLException {
        new HomePage()
                .openPage()
                .openLogInModalWindowOnTopMenu();
    }

    @Parameters("browser")
    @Test(testName = "LogInModalWindowPageTitleTest", suiteName = "LogInPageTest", groups = {"positive"},
            description = "Verifies the title of the 'Log in' modal window page")
    public void verifyTheTitleOfLogInModalWindow() throws MalformedURLException {
        ResourceBundle resourceBundle = ResourceBundle.getBundle("VisibleContent", Locale.US);

        String actualLogInModalWindowTitle = new LogInModalWindowPage().getLogInModalWindowTitle();
        String expectedLogInModalWindowTitle = resourceBundle.getString("LogInModalWindowTitle");
        String errorMessageIfTestFails = "You've opened not the 'Log in' modal window page" +
                "or the 'Log in' window page has another title";

        assertEquals(errorMessageIfTestFails, expectedLogInModalWindowTitle, actualLogInModalWindowTitle);
    }

    @Test(testName = "InputUsernameInLogInModalWindowTest", suiteName = "LogInPageTest", groups = {"positive"},
            description = "Verifies that a customer can input username in 'Log in' form in Log in modal window")
    public void verifyThatCustomerCanInputUsernameInLogInFormInLogInWindowPage() throws MalformedURLException {
        Customer account = CustomerAccountCompiler.withCredentialFromProperty();

        String actualInputUsername = new LogInModalWindowPage().inputUsername(account).getInputUsername();
        String expectedInputUsername = account.getFirstName().concat(" ").concat(account.getSurname());
        String errorMessageIfTestFails = "The displayed username is not that was input";

        assertEquals(errorMessageIfTestFails, expectedInputUsername, actualInputUsername);
    }

    @Test(testName = "InputPasswordInLogInModalWindowTest", suiteName = "LogInPageTest", groups = {"positive"},
            description = "Verifies that a customer can input password in 'Log in' form in Log in modal window")
    public void verifyThatCustomerCanInputPasswordInLogInFormInLogInWindowPage() throws MalformedURLException {
        Customer account = CustomerAccountCompiler.withCredentialFromProperty();

        String actualInputPassword = new LogInModalWindowPage().inputPassword(account).getInputPassword();
        String expectedInputPassword = account.getPassword();
        String errorMessageIfTestFails = "The displayed password is not that was input";

        assertEquals(errorMessageIfTestFails, expectedInputPassword, actualInputPassword);
    }

    @Test(testName = "LogInTestViaChangingSignUpLinkNameOnTopMenuToUsernameLink", suiteName = "LogInPageTest", groups = {"positive"},
            description = "Verifies that 'Sign up' link changes to 'Username' link after successful Log in action.")
    public void verifyThatSignUpLinkChangesToUsernameLinkAfterSuccessfulLogInAction() throws MalformedURLException {
        Customer account = CustomerAccountCompiler.withCredentialFromProperty();
        ResourceBundle resourceBundle = ResourceBundle.getBundle("VisibleContent", Locale.US);

        new LogInModalWindowPage().logIn(account);
        String actualLinkName = new CustomerAccountPage().getLinkNameInPlaceOfSignUpLink();
        String expectedLinkName = resourceBundle
                .getString("LinkNameInPlaceOfSignUpAfterSuccessfulRegistration").concat(" ")
                .concat(account.getFirstName().concat(" ").concat(account.getSurname()));
        String errorMessageIfTestFails = "The displayed link name 'Sign up' does not change to the one containing registered username.";

        assertEquals(errorMessageIfTestFails, expectedLinkName, actualLinkName);
    }

    //    Switching to a new tab of the window using: driver.switchTo().newWindow(WindowType.TAB) doesn't work.
    @Test(testName = "ReLogInToTheSameAccountFailureWithoutLogOutTest", suiteName = "LogInPageTest", groups = {"negative", "security"},
            description = "Verifies that the user can't re-'Log in' the same account without the prior 'Log out'.")
    public void verifyThatVerifiesThatUserCantReLogInTheSameAccountWithoutThePriorLogOut() throws MalformedURLException, AWTException {
        Customer account = CustomerAccountCompiler.withCredentialFromProperty();

        new LogInModalWindowPage().logIn(account);
        new HomePage()
                .openPage()
                .openLogInModalWindowOnTopMenu();
        new LogInModalWindowPage().logIn(account);

        boolean usernameAccountIsDisplayed = new CustomerAccountPage().usernameLinkInPlaceOfSignUpLinkIsDisplayed();
        String errorMessageIfTestFails = "Unexpected result: User can re-'Log in' the same account without the prior 'Log out'.";

        assertFalse(errorMessageIfTestFails, usernameAccountIsDisplayed);
    }

    @Test(testName = "LogInTestViaChangingLogInLinkNameOnTopMenuToLogOut", suiteName = "LogInPageTest", groups = {"positive"},
            description = "Verifies that 'Log in' link changes to 'Log out' link after successful Log in action.")
    public void verifyThatLogInLinkChangesToLogOutLinkAfterSuccessfulLogInAction() throws MalformedURLException {
        Customer account = CustomerAccountCompiler.withCredentialFromProperty();
        ResourceBundle resourceBundle = ResourceBundle.getBundle("VisibleContent", Locale.US);

        new LogInModalWindowPage().logIn(account);
        String actualLinkName = new CustomerAccountPage().getLinkNameInPlaceOfLogInLink();
        String expectedLinkName = resourceBundle
                .getString("LinkNameInPlaceOfLogInAfterSuccessfulRegistration");
        String errorMessageIfTestFails = "The displayed link name Log In does not change to Log out.";

        assertEquals(errorMessageIfTestFails, actualLinkName, expectedLinkName);
    }

    @Test(testName = "LogInFailWithEmptyUsernameInputTest", suiteName = "LogInPageTest", groups = {"negative"},
            description = "Verifies that 'Log in' fails with empty username input field.")
    public void verifyTheFailOfLogInWithEmptyUsernameInput() throws MalformedURLException {
        Customer account = CustomerAccountCompiler.withCredentialFromProperty();

        boolean userIsPushedBackToLogInRegistrationWindowWithLogInErrors = new LogInModalWindowPage()
                .logInWithEmptyUsername(account)
                .pushLogInButton()
                .acceptAlertOnLogIn()
                .logInRegistrationWindowIsDisplayed();
        String errorMessageIfTestFails = "Unexpected result in case of Log in with empty username input field:" +
                " a user might be either logged in, or to be not pushed back to 'Log in' registration window.";

        assertTrue(errorMessageIfTestFails, userIsPushedBackToLogInRegistrationWindowWithLogInErrors);
    }

    @Test(testName = "LogInFailWithEmptyPasswordInputTest", suiteName = "LogInPageTest", groups = {"negative"},
            description = "Verifies that 'Log in' fails with empty password input field.")
    public void verifyTheFailOfLogInWithEmptyPasswordInput() throws MalformedURLException {
        Customer account = CustomerAccountCompiler.withCredentialFromProperty();

        boolean userIsPushedBackToLogInRegistrationWindowWithLogInErrors = new LogInModalWindowPage()
                .logInWithEmptyPassword(account)
                .pushLogInButton()
                .acceptAlertOnLogIn()
                .logInRegistrationWindowIsDisplayed();
        String errorMessageIfTestFails = "Unexpected result in case of Log in with empty password input field:" +
                " a user might be either logged in, or to be not pushed back to 'Log in' registration window.";

        assertTrue(errorMessageIfTestFails, userIsPushedBackToLogInRegistrationWindowWithLogInErrors);
    }

    @Test(testName = "LogInFailWithWrongPasswordInputTest", suiteName = "LogInPageTest", groups = {"negative"},
            description = "Verifies that 'Log in' fails with wrong password input.")
    public void verifyTheFailOfLogInWithWrongPasswordInput() throws MalformedURLException {
        Customer account = CustomerAccountCompiler.withCredentialFromProperty();

        boolean userIsPushedBackToLogInRegistrationWindowWithLogInErrors = new LogInModalWindowPage()
                .logInWithWrongPassword(account)
                .pushLogInButton()
                .acceptAlertOnLogIn()
                .logInRegistrationWindowIsDisplayed();
        String errorMessageIfTestFails = "Unexpected result in case of Log in with wrong password input:" +
                " a user might be either logged in, or to be not pushed back to 'Log in' registration window.";

        assertTrue(errorMessageIfTestFails, userIsPushedBackToLogInRegistrationWindowWithLogInErrors);
    }

    @Test(testName = "LogInFailWithNonRegisteredUsernameInputTest", suiteName = "LogInPageTest", groups = {"negative"},
            description = "Verifies that 'Log in' fails with non-registered username input.")
    public void verifyTheFailOfLogInWithNonRegisteredUsernameInput() throws MalformedURLException {
        Customer account = CustomerAccountCompiler.withCredentialFromProperty();

        boolean userIsPushedBackToLogInRegistrationWindowWithLogInErrors = new LogInModalWindowPage()
                .logInWithNonRegisteredUsername(account)
                .pushLogInButton()
                .acceptAlertOnLogIn()
                .logInRegistrationWindowIsDisplayed();
        String errorMessageIfTestFails = "Unexpected result in case of Log in with non-registered username input:" +
                " a user might be either logged in, or to be not pushed back to 'Log in' registration window.";

        assertTrue(errorMessageIfTestFails, userIsPushedBackToLogInRegistrationWindowWithLogInErrors);
    }

    @Test(testName = "AlertMessageResponseToLogInFailWithEmptyUsernameInputTest", suiteName = "LogInPageTest", groups = {"positive"},
            description = "Verifies alert message response to the 'Log in' failure with empty username input field.")
    public void verifyTheAlertMessageResponseToTheFailOfLogInWithEmptyUsernameInput() throws MalformedURLException {
        Customer account = CustomerAccountCompiler.withCredentialFromProperty();
        ResourceBundle resourceBundle = ResourceBundle.getBundle("VisibleContent", Locale.US);

        String actualAlertMessageResponseToLogInWithErrorInput = new LogInModalWindowPage()
                .logInWithEmptyUsername(account)
                .pushLogInButton()
                .getAlertMessageResponseToLogInAction();
        String expectedAlertMessageResponseToLogInWithErrorInput = resourceBundle
                .getString("LogInAlertMessageOnLogInWithNonExistingUsername");
        String errorMessageIfTestFails = "The displayed alert message response to Log in with empty username input field is not as expected.";

        assertEquals(errorMessageIfTestFails, actualAlertMessageResponseToLogInWithErrorInput,
                expectedAlertMessageResponseToLogInWithErrorInput);
    }

    // As the site is under development, checking log in function is unavailable via HTTPS request.
    // Look up alternative checking the log in via changing the top menu link 'sign up' to the logged in 'username'.
    @Test(testName = "LogInSuccessHTTPResponse",
            suiteName = "LogInModalWindowPageTest", groups = {"positive"},
            description = "Verifies Log in success HTTP response.")
    public void verifyLogInSuccessHTTPResponse() throws IOException {
        Customer account = CustomerAccountCompiler.withCredentialFromProperty();
        String loggedInHTTPS = "https://www.demoblaze.com/#";
        new LogInModalWindowPage().logIn(account);
        HttpClient client = HttpClientBuilder.create().build();
        HttpGet loggedInRequest = new HttpGet(loggedInHTTPS);
        HttpResponse loggedInResponse = client.execute(loggedInRequest);

        int actualLoggedInHTTPResponse = loggedInResponse.getStatusLine().getStatusCode();
        String errorMessageIfTestFails = "The HTTP response is not correct to the successful 'Log in'.";

        assertEquals(errorMessageIfTestFails, 200, actualLoggedInHTTPResponse);
    }

    // As the site is under development, checking 'Log out' function is unavailable via HTTPS request.
    // Look up an alternative checking the 'Log out' - via changing the top menu links 'username' to 'sign up' and 'log out' link to 'log in'.
    @Test(testName = "LogOutSuccessHTTPResponse",
            suiteName = "LogInModalWindowPageTest", groups = {"positive"},
            description = "Verifies Log out success HTTP response.")
    public void verifyLogOutSuccessHTTPResponse() throws IOException {
        Customer account = CustomerAccountCompiler.withCredentialFromProperty();
        String loggedOutHTTPS = "https://www.demoblaze.com/index.html";
        new LogInModalWindowPage().logIn(account);
        new CustomerAccountPage().logOut();
        HttpClient client = HttpClientBuilder.create().build();
        HttpGet loggedOutRequest = new HttpGet(loggedOutHTTPS);
        HttpResponse loggedOutResponse = client.execute(loggedOutRequest);

        int actualLoggedOutHTTPResponse = loggedOutResponse.getStatusLine().getStatusCode();
        String errorMessageIfTestFails = "The HTTP response is not correct to the successful 'Log out'.";

        assertEquals(errorMessageIfTestFails, 200, actualLoggedOutHTTPResponse);
    }

    // As the site is under development, checking the fail of 'Log in' function is unavailable via HTTPS request.
    // Look up alternative checking the fail of 'Log in' - via not changing the top menu links and return to the 'log in' registration popped up window.
    @Test(testName = "LogInFailHTTPResponseWithEmptyUsernameInput",
            suiteName = "LogInModalWindowPageTest", groups = {"negative"},
            description = "Verifies failing Log in with empty username input via HTTP response.")
    public void verifyLogInFailHTTPResponseWithEmptyUsernameInput() throws IOException {
        Customer account = CustomerAccountCompiler.withCredentialFromProperty();
        String loggedInHTTPS = "https://www.demoblaze.com/#";
        new LogInModalWindowPage().logInWithEmptyUsername(account);
        HttpClient client = HttpClientBuilder.create().build();
        HttpGet loggedInRequest = new HttpGet(loggedInHTTPS);
        HttpResponse loggedInResponse = client.execute(loggedInRequest);

        int actualLoggedInHTTPResponse = loggedInResponse.getStatusLine().getStatusCode();
        String errorMessageIfTestFails = "The HTTP response is not correct to the fail of 'Log in'.";

        assertEquals(errorMessageIfTestFails, 400, actualLoggedInHTTPResponse);
    }
}