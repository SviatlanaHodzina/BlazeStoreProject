package tests;


import customer.Customer;
import org.testng.AssertJUnit;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pages.CustomerAccountPage;

import pages.HomePage;
import pages.LogInModalWindowPage;
import storedataservice.CustomerAccountCompiler;

import java.net.MalformedURLException;
import java.util.Locale;
import java.util.ResourceBundle;

import static org.testng.AssertJUnit.assertEquals;


public class LogInModalWindowPageTest extends RequiredConditions {
    @Parameters("browser")
    @BeforeTest
    public void LogIn() throws MalformedURLException {
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

        new LogInModalWindowPage().inputUsername(account);
        String actualInputUsername = new LogInModalWindowPage().inputUsername(account).getInputUsername();
        String expectedInputUsername = account.getFirstName().concat(" ").concat(account.getSurname());
        String errorMessageIfTestFails = "The displayed username is not that was input";

        assertEquals(errorMessageIfTestFails, expectedInputUsername, actualInputUsername);
    }

    @Test(testName = "InputPasswordInLogInModalWindowTest", suiteName = "LogInPageTest", groups = {"positive"},
            description = "Verifies that a customer can input password in 'Log in' form in Log in modal window")
    public void verifyThatCustomerCanInputPasswordInLogInFormInLogInWindowPage() throws MalformedURLException {
        Customer account = CustomerAccountCompiler.withCredentialFromProperty();

        new LogInModalWindowPage().inputPassword(account);
        String actualInputPassword = new LogInModalWindowPage().inputPassword(account).getInputPassword();
        String expectedInputPassword = account.getPassword();
        String errorMessageIfTestFails = "The displayed password is not that was input";

        assertEquals(errorMessageIfTestFails, expectedInputPassword, actualInputPassword);
    }

    @Test(testName = "ChangingSignUpLinkNameOnTopMenuToUsernameLinkAfterSuccessfulLogInTest", suiteName = "ContactPageTest", groups = {"positive"},
            description = "Verifies that 'Sign up' link changes to 'Username' link after successful Log in action.")
    public void verifyThatSignUpLinkChangesToUsernameLinkAfterSuccessfulLogInAction() throws MalformedURLException {
        Customer account = CustomerAccountCompiler.withCredentialFromProperty();
        ResourceBundle resourceBundle = ResourceBundle.getBundle("VisibleContent", Locale.US);

        new LogInModalWindowPage().LogIn(account);
        String actualLinkName = new CustomerAccountPage().getLinkNameInPlaceOfSignUpLink();
        String expectedLinkName = resourceBundle
                .getString("LinkTextInPlaceOfSignUpLinkAfterSuccessfulRegistration").concat(" ")
                .concat(account.getFirstName().concat(" ").concat(account.getSurname()));
        String errorMessageIfTestFails = "The displayed link name does not contain registered username.";

        assertEquals(errorMessageIfTestFails, actualLinkName, expectedLinkName);
    }
}