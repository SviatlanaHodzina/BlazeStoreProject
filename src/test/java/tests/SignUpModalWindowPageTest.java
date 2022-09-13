package tests;

import customer.Customer;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pages.CustomerAccountPage;
import pages.HomePage;
import pages.SignUpModalWindowPage;
import storedataservice.CustomerAccountCompiler;

import java.awt.*;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Locale;
import java.util.ResourceBundle;

import static org.testng.AssertJUnit.*;

// add API test methods verifying response code
public class SignUpModalWindowPageTest extends RequiredConditions {
    @Parameters("browser")
    @BeforeTest
    public void openSignUpModalWindow() throws MalformedURLException {
        new HomePage()
                .openPage()
                .openSignUpModalWindowOnTopMenu();
    }

    @Parameters("browser")
    @Test(testName = "SignUpModalWindowPageTitleTest", suiteName = "SignUpPageTest", groups = {"positive"},
            description = "Verifies the title of the 'Sign up' modal window page")
    public void verifyTheTitleOfSignUpModalWindow() throws MalformedURLException {
        ResourceBundle resourceBundle = ResourceBundle.getBundle("VisibleContent", Locale.US);

        String actualSignUpModalWindowTitle = new SignUpModalWindowPage().getSignUpModalWindowTitle();
        String expectedSignUpModalWindowTitle = resourceBundle.getString("SignUpLinkTopNavigatingMenu");
        String errorMessageIfTestFails = "You've opened not the 'Sign up' modal window page" +
                "or the 'Sign up' window page has another title";

        assertEquals(errorMessageIfTestFails, expectedSignUpModalWindowTitle, actualSignUpModalWindowTitle);
    }

    @Test(testName = "InputUsernameInSignUpModalWindowTest", suiteName = "SignUpPageTest", groups = {"positive"},
            description = "Verifies that a customer can input username in 'Sign up' form in Sign up modal window")
    public void verifyThatCustomerCanInputUsernameInSignUpRegisterForm() throws MalformedURLException {
        Customer account = CustomerAccountCompiler.withCredentialFromProperty();

        String actualInputUsernameInSignUpForm = new SignUpModalWindowPage()
                .inputUsernameInSignUpForm(account)
                .getInputUsernameInSignUpForm();
        String expectedInputUsernameInSignUpForm = account.getFirstName()
                .concat(" ").concat(account.getSurname());
        String errorMessageIfTestFails = "The displayed username in 'Sign up' register form is not that was actually input";

        assertEquals(errorMessageIfTestFails, expectedInputUsernameInSignUpForm, actualInputUsernameInSignUpForm);
    }

    @Test(testName = "InputPasswordInSignUpModalWindowTest", suiteName = "InSignUpPageTest", groups = {"positive"},
            description = "Verifies that a customer can input password in 'Sign up' form in Sign up register form")
    public void verifyThatCustomerCanInputPasswordInInSignUpForm() throws MalformedURLException {
        Customer account = CustomerAccountCompiler.withCredentialFromProperty();

        String actualInputPasswordInSignUpForm = new SignUpModalWindowPage()
                .inputPasswordInSignUpForm(account).getInputPasswordInSignUpForm();
        String expectedInputPasswordInSignUpForm = account.getPassword();
        String errorMessageIfTestFails = "The displayed password in 'Sign up' register form is not that was actually input";

        assertEquals(errorMessageIfTestFails, expectedInputPasswordInSignUpForm, actualInputPasswordInSignUpForm);
    }

    @Test(testName = "SignUpTestViaChangingSignUpLinkNameOnTopMenuToUsernameLink", suiteName = "SignUpPageTest", groups = {"positive"},
            description = "Verifies that 'Sign up' link changes to 'Username' link after successful Sign up action.")
    public void verifySignUpSuccessViaDisplayingUsernameLink() throws MalformedURLException {
        Customer account = CustomerAccountCompiler.withCredentialFromProperty();

        new SignUpModalWindowPage().signUp(account);
        boolean usernameRegisteredOnTopMenuIsNotDisplayed =
                new CustomerAccountPage().usernameLinkInPlaceOfSignUpLinkIsNotDisplayed();
        String errorMessageIfTestFails = "'Sign up' failed or the registered username is not displayed on top menu .";

        assertFalse(errorMessageIfTestFails, usernameRegisteredOnTopMenuIsNotDisplayed);
    }

    // run this test only after the passed test 'SignUpTestViaChangingSignUpLinkNameOnTopMenuToUsernameLink'
    @Test(testName = "MatchOfDisplayedUsernameLinkToTheRegisteredUsernameTest", suiteName = "SignUpPageTest", groups = {"positive"},
            description = "Verifies that the displayed username link on top menu matches the registered username after successful 'Sign up'.")
    public void verifyTheMatchOfDisplayedUsernameLinkToTheRegisteredUsernameWithSuccessfulSignUp() throws MalformedURLException {
        Customer account = CustomerAccountCompiler.withCredentialFromProperty();
        ResourceBundle resourceBundle = ResourceBundle.getBundle("VisibleContent", Locale.US);

        new SignUpModalWindowPage().signUp(account);
        String actualLinkName = new CustomerAccountPage().getLinkNameInPlaceOfSignUpLink();
        String expectedLinkName = resourceBundle
                .getString("LinkNameInPlaceOfSignUpAfterSuccessfulRegistration").concat(" ")
                .concat(account.getFirstName().concat(" ").concat(account.getSurname()));
        String errorMessageIfTestFails = "The displayed username link on top menu doesn't match the registered username after successful 'Sign up'.";

        assertEquals(errorMessageIfTestFails, expectedLinkName, actualLinkName);
    }

    @Test(testName = "SignUpFailWithEmptyUsernameInputTest", suiteName = "SignUpPageTest", groups = {"negative"},
            description = "Verifies that 'Sign up' fails with empty username input field.")
    public void verifyTheFailOfSignUpWithEmptyUsernameInput() throws MalformedURLException {
        Customer account = CustomerAccountCompiler.withCredentialFromProperty();

        boolean userIsPushedBackToSignUpRegistrationFormWithEmptyUsernameInputField =
                new SignUpModalWindowPage()
                        .signUpWithEmptyUsernameInput(account)
                        .signUpRegistrationWindowIsDisplayed();
        String errorMessageIfTestFails = "Unexpected result in case of Sign up with empty username input field:" +
                " a user might be either signed up, or not pushed back to 'sign up' registration form.";

        assertTrue(errorMessageIfTestFails, userIsPushedBackToSignUpRegistrationFormWithEmptyUsernameInputField);
    }

    @Test(testName = "SignUpFailWithEmptyPasswordInputTest", suiteName = "SignUpPageTest", groups = {"negative"},
            description = "Verifies that 'Sign up' fails with empty password input field.")
    public void verifyTheFailOfSignUpWithEmptyPasswordInput() throws MalformedURLException {
        Customer account = CustomerAccountCompiler.withCredentialFromProperty();

        boolean userIsPushedBackToSignUpRegistrationFormWithEmptyPasswordInput =
                new SignUpModalWindowPage()
                        .signUpWithEmptyPasswordInput(account)
                        .signUpRegistrationWindowIsDisplayed();
        String errorMessageIfTestFails = "Unexpected result in case of Sign up with empty password input field:" +
                " a user might be either signed up, or not pushed back to 'Sign up' registration form.";

        assertTrue(errorMessageIfTestFails, userIsPushedBackToSignUpRegistrationFormWithEmptyPasswordInput);
    }

    @Test(testName = "SignUpFailWithWrongPasswordInputTest", suiteName = "SignUpPageTest", groups = {"negative"},
            description = "Verifies that 'Sign up' fails with wrong password input.")
    public void verifyTheFailOfSignUpWithWrongPasswordInput() throws MalformedURLException {
        Customer account = CustomerAccountCompiler.withCredentialFromProperty();

        boolean userIsPushedBackToSignUpRegistrationFormWithWrongPasswordInput =
                new SignUpModalWindowPage()
                        .signUpWithWrongPasswordInput(account)
                        .signUpRegistrationWindowIsDisplayed();
        String errorMessageIfTestFails = "Unexpected result in case of Sign up with wrong password input:" +
                " a user might be either signed up, or not pushed back to 'Sign up' registration form.";

        assertTrue(errorMessageIfTestFails, userIsPushedBackToSignUpRegistrationFormWithWrongPasswordInput);
    }

    @Test(testName = "SignUpFailWithRegisteredUsernameInputTest", suiteName = "SignUpPageTest", groups = {"negative"},
            description = "Verifies that 'Sign up' fails with the registered before username input.")
    public void verifyTheFailOfSignUpWithRegisteredUsernameInput() throws MalformedURLException {
        Customer account = CustomerAccountCompiler.withCredentialFromProperty();

        boolean userIsPushedBackToSignUpRegistrationFormWithRegisteredBeforeUsername =
                new SignUpModalWindowPage()
                        .signUpWithRegisteredBeforeUsername(account)
                        .signUpRegistrationWindowIsDisplayed();
        String errorMessageIfTestFails = "Unexpected result in case of 'Sign up' with the registered before username input:" +
                " a user might be either signed up with the same username, or to be not pushed back to 'Sign up' registration form.";

        assertTrue(errorMessageIfTestFails, userIsPushedBackToSignUpRegistrationFormWithRegisteredBeforeUsername);
    }

    @Test(testName = "AlertMessageResponseToSuccessfulSignUpTest", suiteName = "SignUpPageTest", groups = {"positive"},
            description = "Verifies alert message response to the successful 'Sign up'.")
    public void verifyTheAlertMessageResponseToSuccessfulSignUp() throws MalformedURLException {
        Customer account = CustomerAccountCompiler.withCredentialFromProperty();
        ResourceBundle resourceBundle = ResourceBundle.getBundle("VisibleContent", Locale.US);

        String actualAlertMessageResponseToSuccessfulSignUp =
                new SignUpModalWindowPage()
                        .inputUsernameInSignUpForm(account)
                        .inputPasswordInSignUpForm(account)
                        .pushSignUpButton()
                        .getAlertMessageResponseToSignUpAction();
        String expectedAlertMessageResponseToSuccessfulSignUp = resourceBundle
                .getString("SignUpAlertMessageOnSignUpSuccessful");
        String errorMessageIfTestFails = "The displayed alert message response to successful 'Sign up' is not as expected.";

        assertEquals(errorMessageIfTestFails, actualAlertMessageResponseToSuccessfulSignUp,
                expectedAlertMessageResponseToSuccessfulSignUp);
    }

    //    The same alert message 'Please fill out Username and Password.' when sign up with empty username or password.
//    Consider getting separate messages on empty username and empty password inputs, as well as for weak passwords.
    @Test(testName = "AlertMessageResponseToSignUpFailWithEmptyUsernameInputTest", suiteName = "SignUpPageTest", groups = {"positive"},
            description = "Verifies alert message response to the 'Sign up' failure with empty username input field.")
    public void verifyTheAlertMessageResponseToTheFailOfSignUpWithEmptyUsernameInput() throws MalformedURLException {
        Customer account = CustomerAccountCompiler.withCredentialFromProperty();
        ResourceBundle resourceBundle = ResourceBundle.getBundle("VisibleContent", Locale.US);

        String actualAlertMessageResponseToSignUpWithEmptyUsernameInput =
                new SignUpModalWindowPage()
                        .inputPasswordInSignUpForm(account)
                        .pushSignUpButton()
                        .getAlertMessageResponseToSignUpAction();
        String expectedAlertMessageResponseToSignUpWithEmptyUsernameInput = resourceBundle
                .getString("SignUpAlertMessageOnSignUpWithEmptyUsernameOrPasswordInput");
        String errorMessageIfTestFails = "The displayed alert message response to 'Sign up' with empty username input field is not as expected.";

        assertEquals(errorMessageIfTestFails, actualAlertMessageResponseToSignUpWithEmptyUsernameInput,
                expectedAlertMessageResponseToSignUpWithEmptyUsernameInput);
    }

    //    The same alert message 'Please fill out Username and Password.' when sign up with empty username or password.
//    Consider getting separate messages on empty username and empty password inputs, as well as for weak passwords.
    @Test(testName = "AlertMessageResponseToSignUpFailWithEmptyPasswordInputTest", suiteName = "SignUpPageTest", groups = {"positive"},
            description = "Verifies alert message response to the 'Sign up' failure with empty password input field.")
    public void verifyTheAlertMessageResponseToTheFailOfSignUpWithEmptyPasswordInput() throws MalformedURLException {
        Customer account = CustomerAccountCompiler.withCredentialFromProperty();
        ResourceBundle resourceBundle = ResourceBundle.getBundle("VisibleContent", Locale.US);

        String actualAlertMessageResponseToSignUpWithEmptyPasswordInput =
                new SignUpModalWindowPage()
                        .inputUsernameInSignUpForm(account)
                        .pushSignUpButton()
                        .getAlertMessageResponseToSignUpAction();
        String expectedAlertMessageResponseToSignUpWithEmptyPasswordInput = resourceBundle
                .getString("SignUpAlertMessageOnSignUpWithEmptyUsernameOrPasswordInput");
        String errorMessageIfTestFails = "The displayed alert message response to 'Sign up' with empty password input field is not as expected.";

        assertEquals(errorMessageIfTestFails, actualAlertMessageResponseToSignUpWithEmptyPasswordInput,
                expectedAlertMessageResponseToSignUpWithEmptyPasswordInput);
    }

    @Test(testName = "AlertMessageResponseToSignUpFailWithRegisteredBeforeUsernameInputTest", suiteName = "SignUpPageTest", groups = {"positive"},
            description = "Verifies alert message response to the 'Sign up' failure with registered before username input field.")
    public void verifyTheAlertMessageResponseToTheFailOfSignUpWithRegisteredBeforeUsernameInput() throws MalformedURLException {
        Customer account = CustomerAccountCompiler.withCredentialFromProperty();
        ResourceBundle resourceBundle = ResourceBundle.getBundle("VisibleContent", Locale.US);

        String actualAlertMessageResponseToSignUpWithRegisteredUsernameInput =
                new SignUpModalWindowPage()
                        .inputUsernameInSignUpForm(account)
                        .inputPasswordInSignUpForm(account)
                        .pushSignUpButton()
                        .getAlertMessageResponseToSignUpAction();
        String expectedAlertMessageResponseToSignUpWithRegisteredBeforeUsernameInput = resourceBundle
                .getString("SignUpAlertMessageOnSignUpWithRegisteredBeforeUsername");
        String errorMessageIfTestFails = "The displayed alert message response to 'Sign up' with registered before username input field is not as expected.";

        assertEquals(errorMessageIfTestFails, actualAlertMessageResponseToSignUpWithRegisteredUsernameInput,
                expectedAlertMessageResponseToSignUpWithRegisteredBeforeUsernameInput);
    }

    // As the site is under development, checking 'Sign up' function is unavailable via HTTPS request.
    // Look up alternative checking the 'Sign up' via the changing the top menu link 'sign up' to the signed up 'username'.
    @Test(testName = "SignUpSuccessHTTPResponse",
            suiteName = "SignUpModalWindowPageTest", groups = {"positive"},
            description = "Verifies 'Sign up' in success HTTP response.")
    public void verifySignUpSuccessHTTPResponse() throws IOException {
        Customer account = CustomerAccountCompiler.withCredentialFromProperty();
        String signedUpHTTPS = "https://www.demoblaze.com/#";
        new SignUpModalWindowPage().signUp(account);
        HttpClient client = HttpClientBuilder.create().build();
        HttpGet signedUpRequest = new HttpGet(signedUpHTTPS);
        HttpResponse signedUpResponse = client.execute(signedUpRequest);

        int actualSignedUpHTTPResponse = signedUpResponse.getStatusLine().getStatusCode();
        String errorMessageIfTestFails = "The HTTP response is not correct to the successful 'sign up'.";

        assertEquals(errorMessageIfTestFails, 200, actualSignedUpHTTPResponse);
    }

    // As the site is under development, checking the fail of 'Sign up' function is unavailable via HTTPS request.
    // Look up alternative checking the fail of 'sign up' - via not changing the top menu links and return to the 'Sign up' registration form.
    @Test(testName = "SignUpFailHTTPResponseWithEmptyUsernameInput",
            suiteName = "SignUpModalWindowPageTest", groups = {"negative"},
            description = "Verifies failing 'Sign up' in with empty username input via HTTP response.")
    public void verifySignUpFailHTTPResponseWithEmptyUsernameInput() throws IOException {
        Customer account = CustomerAccountCompiler.withCredentialFromProperty();
        String signedUpHTTPS = "https://www.demoblaze.com/#";
        new SignUpModalWindowPage().signUpWithEmptyUsernameInput(account);
        HttpClient client = HttpClientBuilder.create().build();
        HttpGet signedUpRequest = new HttpGet(signedUpHTTPS);
        HttpResponse signedUpResponse = client.execute(signedUpRequest);

        int actualSignedUpHTTPResponse = signedUpResponse.getStatusLine().getStatusCode();
        String errorMessageIfTestFails = "The HTTP response is not correct to the fail of 'Sign up'.";

        assertEquals(errorMessageIfTestFails, 400, actualSignedUpHTTPResponse);
    }
}