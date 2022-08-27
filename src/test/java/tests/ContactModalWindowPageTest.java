package tests;

import customer.Customer;
import org.testng.Assert;
import org.testng.AssertJUnit;
import storedataservice.CustomerAccountCompiler;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pages.ContactModalWindowPage;
import pages.HomePage;

import java.net.MalformedURLException;
import java.util.Locale;
import java.util.ResourceBundle;

import static org.testng.AssertJUnit.*;

/**
 * Here are the features that should be developed first and displayed in the Test Requirements list for the ContactModalWindowPage:
 * 1) The message content input into the sending form, displaying in the sending confirmation alert.- Important!
 * This is the test-case verifying the actual sent message, for the future developing feature.
 * 2) The 'Save' and 'Print' features for the sent message content with the corresponding input information about sender and the Date.
 * Actually: There is only an alert confirmation that the message sent without the ensuring the message's context.
 * 3) In the Test Requirements list should be displayed the required color and the design appearance for all visible
 * graphical elements in various states, that should have a contrast with the background colors;
 */

public class ContactModalWindowPageTest extends RequiredConditions {
    @Parameters("browser")
    @BeforeTest
    public void openContactModalWindowLinkOnTopNavigatingMenu() throws MalformedURLException {
        new HomePage()
                .openPage()
                .openContactModalLinkOnTopNavigatingMenu();
    }

    @Parameters("browser")
    @Test(testName = "ContactModalWindowTitleTest", suiteName = "ContactPageTest", groups = {"positive"},
            priority = 1, description = "Verifies the title of the Contact modal window page")
    public void verifyTheTitleOfContactModalWindow() throws MalformedURLException {
        ResourceBundle resourceBundle = ResourceBundle.getBundle("VisibleContent", Locale.US);

        String actualContactModalWindowTitle = new ContactModalWindowPage().getContactModalWindowTitle();
        String expectedContactModalWindowTitle = resourceBundle.getString("ContactModalWindowTitle");
        String errorMessageIfTestFails = "You've opened not the Contact modal window page" +
                "or the Contact window page has another title";

        assertEquals(errorMessageIfTestFails, expectedContactModalWindowTitle, actualContactModalWindowTitle);
    }

    @Test(testName = "InputContactEmailInContactModalWindowTest", suiteName = "ContactPageTest", groups = {"positive"},
            priority = 2, description = "Verifies that a customer can input Email in Contact Form 'New message' in Contact page")
    public void verifyThatCustomerCanInputContactEmailInContactFormInContactPage() throws MalformedURLException {
        Customer account = CustomerAccountCompiler.withCredentialFromProperty();

        String actualInputEmail = new ContactModalWindowPage()
                .inputContactEmail(account)
                .getInputContactEmail();
        String expectedInputEmail = account.getEmail();
        String errorMessageIfTestFails = "The contact email is not displayed or displayed not the one that was input";
        assertEquals(errorMessageIfTestFails, expectedInputEmail, actualInputEmail);
    }

    @Test(testName = "InputContactNameInContactModalWindowTest", suiteName = "ContactPageTest", groups = {"positive"},
            priority = 3, description = "Verifies that a customer can input contact Name in Contact Form 'New message' in Contact page")
    public void verifyThatCustomerCanInputContactNameInContactFormInContactPage() throws MalformedURLException {
        Customer account = CustomerAccountCompiler.withCredentialFromProperty();

        String actualInputName = new ContactModalWindowPage()
                .inputContactName(account)
                .getInputContactName();

        String expectedInputName = account.getFirstName().concat(" ").concat(account.getSurname());
        String errorMessageIfTestFails = "The contact name is not displayed or displayed not the one that was input";

        assertEquals(errorMessageIfTestFails, expectedInputName, actualInputName);
    }

    @Test(testName = "InputMessageInContactModalWindowTest", suiteName = "ContactPageTest", groups = {"positive"},
            priority = 4, description = "Verifies that a customer can input a Message in Contact Form 'New message' in Contact page")
    public void verifyThatCustomerCanInputMessageInContactFormInContactPage() throws MalformedURLException {
        ResourceBundle resourceBundle = ResourceBundle.getBundle("VisibleContent", Locale.US);
        String messageToSend = resourceBundle.getString("SentMessageContext");

        String actualInputMessageContextSent = new ContactModalWindowPage()
                .inputMessage(messageToSend)
                .getInputMessage();

        String expectedInputMessageContextSent = resourceBundle.getString("SentMessageContext");
        String errorMessageIfTestFails = "The input message is not displayed or displayed with the wrong context";

        assertEquals(errorMessageIfTestFails, expectedInputMessageContextSent, actualInputMessageContextSent);
    }

    @Test(testName = "ContactTheStoreBySendingMessageTest", suiteName = "ContactPageTest", groups = {"positive"},
            priority = 5, description = "Verifies that the confirmation alert has a message of 'successfully " +
            "sent message' content in 'Contact' modal window page")
    public void verifyThatCustomerCanSendMessageInContactModalWindowPage() throws MalformedURLException {
        Customer account = CustomerAccountCompiler.withCredentialFromProperty();
        ResourceBundle resourceBundle = ResourceBundle.getBundle("VisibleContent", Locale.US);
        String messageToSend = resourceBundle.getString("SentMessageContext");

        String actualSentMessageAlertConfirmationText = new ContactModalWindowPage()
                .inputContactEmail(account)
                .inputContactName(account)
                .inputMessage(messageToSend)
                .sendMessage()
                .getResponseMessageInAlertWindowOnSendingMessageInContactPage();

        String expectedSentMessageAlertConfirmationText = resourceBundle.getString("ConfirmationTextOnSendMessageActionInContactPage");
        String errorMessageIfTestFails = "You've got wrong confirmation alert text message or it isn't displayed at all.";

        assertEquals(errorMessageIfTestFails, expectedSentMessageAlertConfirmationText, actualSentMessageAlertConfirmationText);
    }

    // This is the test-case for the future feature, that is supposed to be developed yet.
    @Test(testName = "ActualMessageContentSentToTheStoreTest", suiteName = "ContactPageTest", groups = {"positive"},
            priority = 2, description = "Verifies that a sending confirmation alert contains the message's " +
            "content matching the text input into the sending form.")
    public void verifyThatTheSendingConfirmationAlertContainsTheMessageContentMatchingTheTextInputIntoTheSendingForm()
            throws MalformedURLException {
        Customer account = CustomerAccountCompiler.withCredentialFromProperty();
        ResourceBundle resourceBundle = ResourceBundle.getBundle("VisibleContent", Locale.US);
        String messageToSend = resourceBundle.getString("SentMessageContext");

        String actualSentMessageAlertConfirmationText = new ContactModalWindowPage()
                .inputContactEmail(account)
                .inputContactName(account)
                .inputMessage(messageToSend)
                .sendMessage()
                .getResponseMessageInAlertWindowOnSendingMessageInContactPage();

        String expectedSentMessageContentInConfirmationAlertWindow = resourceBundle.getString("SentMessageContext");
        String errorMessageIfTestFails = "A sending confirmation alert window does not contain the message's content input" +
                " into the sending form or does not match the input message text.";

        AssertJUnit.assertTrue(errorMessageIfTestFails, actualSentMessageAlertConfirmationText
                .contains(expectedSentMessageContentInConfirmationAlertWindow));
    }

    @Test(testName = "ConfirmationOfAlertOfFailureOfSendingMessageWithSkippedEmailInputFieldTest", suiteName = "ContactPageTest",
            groups = {"positive"}, priority = 6, description = "Verifies that the confirmation of an alert of the failure of sending a message " +
            "with skipped Email input field returns a customer to contact form")
    public void verifyThatConfirmationOfAlertOfFailureOfSendingMessageWithSkippedEmailInputFieldReturnsCustomerToTheContactForm()
            throws MalformedURLException {
        Customer account = CustomerAccountCompiler.withCredentialFromProperty();
        ResourceBundle resourceBundle = ResourceBundle.getBundle("VisibleContent", Locale.US);
        String messageToSend = resourceBundle.getString("SentMessageContext");

        boolean acceptanceOfAlertOfTheFailureOfSendingMessageReturnsCustomerToContactModalWindow =
                new ContactModalWindowPage()
                        .completeContactFormWithEmptyEmailInput(account, messageToSend)
                        .sendMessage()
                        .acceptAlertOnSendMessage()
                        .acceptanceOfFailureOfSendingMessageReturnsCustomerToContactModalWindow();

        String errorMessageIfTestFails = "Confirmation of an alert of the failure of sending message with skipped Email " +
                "input field resulted in decline the return a customer to contact form.";

        assertTrue(errorMessageIfTestFails, acceptanceOfAlertOfTheFailureOfSendingMessageReturnsCustomerToContactModalWindow);
    }

    @Test(testName = "ConfirmationOfAlertOfFailureOfSendingMessageWithSkippedNameInputFieldTest", suiteName = "ContactPageTest",
            groups = {"positive"}, priority = 7, description = "Verifies that the confirmation of an alert of the failure of sending " +
            "a message with skipped Name input field returns a customer to contact form")
    public void verifyThatConfirmationOfAlertOfFailureOfSendingMessageWithSkippedNameInputFieldReturnsCustomerToTheContactForm()
            throws MalformedURLException {
        Customer account = CustomerAccountCompiler.withCredentialFromProperty();
        ResourceBundle resourceBundle = ResourceBundle.getBundle("VisibleContent", Locale.US);
        String messageToSend = resourceBundle.getString("SentMessageContext");

        boolean acceptanceOfAlertOfTheFailureOfSendingMessageReturnsCustomerToContactModalWindow =
                new ContactModalWindowPage()
                        .completeContactFormWithEmptyNameInput(account, messageToSend)
                        .sendMessage()
                        .acceptAlertOnSendMessage()
                        .acceptanceOfFailureOfSendingMessageReturnsCustomerToContactModalWindow();
        String errorMessageIfTestFails = "Confirmation of an alert of the failure of sending message with skipped Name input " +
                "field resulted in decline the return a customer to contact form.";

        assertTrue(errorMessageIfTestFails, acceptanceOfAlertOfTheFailureOfSendingMessageReturnsCustomerToContactModalWindow);
    }

    @Test(testName = "ConfirmationOfAlertOfFailureOfSendingMessageWithSkippedMessageInputFieldTest", suiteName = "ContactPageTest",
            groups = {"positive"}, priority = 8, description = "Verifies that the confirmation of an alert of the failure of sending" +
            "a message with skipped Message input field returns a customer to contact form")
    public void verifyThatConfirmationOfAlertOfFailureOfSendingMessageWithSkippedMessageInputFieldReturnsCustomerToTheContactForm()
            throws MalformedURLException {
        Customer account = CustomerAccountCompiler.withCredentialFromProperty();

        boolean acceptanceOfAlertOfTheFailureOfSendingMessageReturnsCustomerToContactModalWindow =
                new ContactModalWindowPage()
                        .completeContactFormWithEmptyMessageInput(account)
                        .sendMessage()
                        .acceptAlertOnSendMessage()
                        .acceptanceOfFailureOfSendingMessageReturnsCustomerToContactModalWindow();
        String errorMessageIfTestFails = "Confirmation of an alert of the failure of sending message with skipped Message input " +
                "field resulted in decline the return a customer to contact form.";

        AssertJUnit.assertTrue(errorMessageIfTestFails, acceptanceOfAlertOfTheFailureOfSendingMessageReturnsCustomerToContactModalWindow);
    }

    @Test(testName = "CloseXOnTopMenuTheContactWidowPageTest", suiteName = "ContactPageTest",
            groups = {"positive"}, priority = 9, description = "Verifies that closing the Contact widow page " +
            "by pushing 'X' on top button closes the popped up window")
    public void verifyThatClosingTheContactWindowPageByCloseXOnTopButtonClosesThePoppedUpWindow() throws MalformedURLException {
        new ContactModalWindowPage().closeTheContactWidowPageByCloseXOnTopButton();
        String errorMessageIfTestFails = "Pushing closing button 'X' on top does not close the Contact window 'New message'";

        assertTrue(errorMessageIfTestFails, new ContactModalWindowPage().contactModalWindowIsClosed());
    }

    @Test(testName = "CloseFooterTheContactWidowPageTest", suiteName = "ContactPageTest",
            groups = {"positive"}, priority = 10, description = "Verifies that closing the Contact widow page " +
            "by pushing 'Close' footer button closes the popped up window")
    public void verifyThatClosingTheContactWindowPageByCloseFooterButtonClosesThePoppedUpWindow() throws MalformedURLException {
        new ContactModalWindowPage().closeTheContactWidowPageByCloseFooterButton();
        String errorMessageIfTestFails = "Pushing 'Close' footer button does not close the Contact window 'New message'";

        assertTrue(errorMessageIfTestFails, new ContactModalWindowPage().contactModalWindowIsClosed());
    }

    /**
     * In the Test Requirements list should be displayed the required color for the active link
     * Actual color of the 'Contact' link on top menu does not contrast with the background color.
     */
    @Test(testName = "ColorChangingOfActiveContactLinkAfterClosingPoppedUpWindowTest", suiteName = "ContactPageTest",
            groups = {"positive", "design", "accessibility"}, priority = 11, description = "Verifies that the active 'Contact' link's color " +
            "get changed after closing popped up window")
    public void verifyThatTheActiveContactLinkColorGetChangedAfterClosingPoppedUpWindow()
            throws MalformedURLException {
        ResourceBundle resourceBundle = ResourceBundle.getBundle("ColorCodesExpected", Locale.US);
        String expectedColorOfActiveContactLink = resourceBundle.getString("ColorActiveLinksOnTopMenuRGBA");
        String actualColorOfActiveContactLink = new ContactModalWindowPage()
                .closeTheContactWidowPageByCloseFooterButton()
                .getColorOfActiveContactLink();
        String errorMessageIfTestFails = "The active 'Contact' link's color doesn't get changed after closing popped up window";

        Assert.assertEquals(actualColorOfActiveContactLink, expectedColorOfActiveContactLink, errorMessageIfTestFails);
    }

    @Test(testName = "FailureOfContactTheStoreBySendingMessageWithEmptyEmailInputTest", suiteName = "ContactPageTest",
            groups = {"negative"}, priority = 1, description = "Verifies the failure of sending a message " +
            "with skipped Email input field in Contact modal window")
    public void verifyThatCustomerCanNotSendMessageWithEmptyEmailInputInContactModalWindowPage() throws MalformedURLException {
        Customer account = CustomerAccountCompiler.withCredentialFromProperty();
        ResourceBundle resourceBundle = ResourceBundle.getBundle("VisibleContent", Locale.US);
        String messageToSend = resourceBundle.getString("SentMessageContext");

        String actualAlertTextMessage = new ContactModalWindowPage()
                .completeContactFormWithEmptyEmailInput(account, messageToSend)
                .sendMessage()
                .getResponseMessageInAlertWindowOnSendingMessageInContactPage();

        String unexpectedAlertTextMessage = resourceBundle.getString("ConfirmationTextOnSendMessageActionInContactPage");

        String errorMessageIfTestFails = "You've got unexpected confirmation alert text message of successfully sent input message.";

        Assert.assertNotEquals(unexpectedAlertTextMessage, actualAlertTextMessage, errorMessageIfTestFails);
    }

    @Test(testName = "FailureOfContactTheStoreBySendingMessageWithEmptyNameInputTest", suiteName = "ContactPageTest",
            groups = {"negative"}, priority = 2, description = "Verifies the failure of sending a message " +
            "with skipped Name input field in Contact modal window")
    public void verifyThatCustomerCanNotSendMessageWithEmptyNameInputInContactModalWindowPage() throws MalformedURLException {
        Customer account = CustomerAccountCompiler.withCredentialFromProperty();
        ResourceBundle resourceBundle = ResourceBundle.getBundle("VisibleContent", Locale.US);
        String messageToSend = resourceBundle.getString("SentMessageContext");

        String actualAlertTextMessage = new ContactModalWindowPage()
                .completeContactFormWithEmptyNameInput(account, messageToSend)
                .sendMessage()
                .getResponseMessageInAlertWindowOnSendingMessageInContactPage();

        String unexpectedAlertTextMessage = resourceBundle.getString("ConfirmationTextOnSendMessageActionInContactPage");

        String errorMessageIfTestFails = "You've got unexpected confirmation alert text message of successfully sent input message.";

        Assert.assertNotEquals(unexpectedAlertTextMessage, actualAlertTextMessage, errorMessageIfTestFails);
    }

    @Test(testName = "FailureOfContactTheStoreBySendingMessageWithEmptyMessageInputTest", suiteName = "ContactPageTest",
            groups = {"negative"}, priority = 3, description = "Verifies the failure of sending a message " +
            "with skipped Message input field in Contact modal window")
    public void verifyThatCustomerCanNotSendMessageWithEmptyMessageInputInContactModalWindowPage() throws MalformedURLException {
        Customer account = CustomerAccountCompiler.withCredentialFromProperty();
        ResourceBundle resourceBundle = ResourceBundle.getBundle("VisibleContent", Locale.US);

        String actualAlertTextMessage = new ContactModalWindowPage()
                .completeContactFormWithEmptyMessageInput(account)
                .sendMessage()
                .getResponseMessageInAlertWindowOnSendingMessageInContactPage();

        String unexpectedAlertTextMessage = resourceBundle.getString("ConfirmationTextOnSendMessageActionInContactPage");

        String errorMessageIfTestFails = "You've got unexpected confirmation alert text message of successfully sent input message.";

        Assert.assertNotEquals(unexpectedAlertTextMessage, actualAlertTextMessage, errorMessageIfTestFails);
    }


//    specify the method checking the contrast color with other links and the background
//    @Test(testName = "ContrastColorChangingOfActiveContactLinkAfterClosingPoppedUpWindowTest", suiteName = "ContactPageTest",
//            groups = {"positive", "design"}, description = "Verifies that the changed color of active Contact link after closing " +
//            "popped up window is in contrast with other links")
//    public void verifyThatTheChangedColorOfActiveContactLinkAfterClosingPoppedUpWindowIsInContrastWithOtherLinks()
//            throws MalformedURLException {
//        String actualColorOfActiveContactLink = new ContactModalWindowPage()
//                .closeTheContactWidowPageByCloseFooterButton()
//                .getColorOfActiveContactLink();
//
//        String errorMessageIfTestFails = "The changed color of active Contact link after closing popped up window is not" +
//                " in contrast with other links otherwise request the site's owner for the certain required color HEX or RGBA standard";
//
//        assertTrue(errorMessageIfTestFails, new ContactModalWindowPage().contactModalWindowIsClosed());
//    }
}