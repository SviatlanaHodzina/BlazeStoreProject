package tests;

import org.testng.AssertJUnit;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pages.HomePage;
import pages.LaptopsPage;
import pages.MonitorsPage;
import pages.PhonesPage;

import java.net.MalformedURLException;
import java.util.*;

import static org.testng.AssertJUnit.*;
import static pages.PhonesPage.getPhoneModelsListInResourceBundle;

//the products' descriptions should be implemented as technical specifications
public class PhonesPageTest extends RequiredConditions {
    @Parameters("browser")
    @BeforeTest
    public void openAboutUsModalWindowLinkOnTopNavigatingMenu() throws MalformedURLException {
        ResourceBundle resourceBundle = ResourceBundle.getBundle("VisibleContent", Locale.US);
        new HomePage()
                .openPage()
                .openPhonesLinkOnSideMenu(resourceBundle.getString("PhonesLinkOnSideMenu"));
    }

    @Parameters("browser")
    @Test(testName = "PhonesRowContainsAllPhoneModelsInResourceBundleTest", suiteName = "PhonesPageTest",
            groups = {"positive"}, description = "Verifies That 'Phones' link side menu navigates to phones row")
    public void verifyThatPhonesLinkOnSideMenuNavigatesToPhonesRowOnly() throws MalformedURLException {
        List<String> actualPhoneHrefModelsList = new PhonesPage()
                .getPhoneHrefModelsList();
        List<String> expectedPhoneModelsList = new PhonesPage()
                .getPhoneModelsListInResourceBundle();

        String errorMessageIfTestFails = "The 'Phones' section does not contain all the phone models in resource bundle.";

        assertTrue(errorMessageIfTestFails, actualPhoneHrefModelsList.containsAll(expectedPhoneModelsList));
    }

    @Parameters("browser")
    @Test(testName = "PhonesRowSizeTest", suiteName = "PhonesPageTest",
            groups = {"Positive"}, description = "Verifies that 'Phones' row size equals size of phones in resource Bundle.")
    public void verifyThatPhonesRowEqualsTheSizeOfPhoneRowInResourceBundle() throws MalformedURLException {
        int actualPhoneRowSize = new PhonesPage().getSizeOfPhonesList();
        int expectedPhoneRowSize = new PhonesPage().getPhoneModelsListInResourceBundle().size();
        String errorMessageIfTestFails = "The 'Phones' row is not equal to the size of phone models in Resource bundle.";

        assertEquals(errorMessageIfTestFails, expectedPhoneRowSize, actualPhoneRowSize);
    }

    @Parameters("browser")
    @Test(testName = "PhonesRowContainerDoesNotContainLaptopItemsTest", suiteName = "PhonesPageTest",
            groups = {"negative"}, description = "Verifies that 'Phones' row does not contain laptop items.")
    public void verifyThatPhonesRowDoesNotContainLaptops() throws MalformedURLException {
        ResourceBundle resourceBundleLaptops = ResourceBundle.getBundle("laptops");

        List<String> actualPhoneHrefModelsList = new PhonesPage()
                .getPhoneHrefModelsList();
        new LaptopsPage();
        List<String> unExpectedLaptopModelsList = LaptopsPage
                .getLaptopModelsListInResourceBundle();

        String errorMessageIfTestFails = "The 'Phones' row contain unexpected laptop item/ items";

        AssertJUnit.assertFalse(errorMessageIfTestFails, actualPhoneHrefModelsList.contains(unExpectedLaptopModelsList));
    }

    @Parameters("browser")
    @Test(testName = "PhonesRowContainerDoesNotContainMonitorItemsTest", suiteName = "PhonesPageTest",
            groups = {"negative"}, description = "Verifies that 'Phones' row does not contain monitor items.")
    public void verifyThatPhonesRowDoesNotContainMonitors() throws MalformedURLException {
        ResourceBundle resourceBundleMonitors = ResourceBundle.getBundle("monitors");

        List<String> actualPhoneHrefModelsList = new PhonesPage()
                .getPhoneHrefModelsList();
        List<String> unExpectedMonitorModelsList = new MonitorsPage()
                .getMonitorModelsListInResourceBundle();

        String errorMessageIfTestFails = "The 'Phones' row contains unexpected monitor item/ items";

        AssertJUnit.assertFalse(errorMessageIfTestFails, actualPhoneHrefModelsList.contains(unExpectedMonitorModelsList));
    }

    @Parameters("browser")
    @Test(testName = "Phone1ModelCheckTest", suiteName = "PhonesPageTest",
            groups = {"positive"}, description = "Verifies that the phone1 model is displayed as declared in the 'phones' resource bundle")
    public void verifyThatThePhone1ModelIsDisplayedAsInResourceBundle() throws MalformedURLException {

        ResourceBundle resourceBundlePhone1 = ResourceBundle.getBundle("phone1");

        String actualPhone1Model = new PhonesPage().getPhoneModel(0);
        String expectedPhone1Model = resourceBundlePhone1.getString("phoneModel");

        String errorMessageIfTestFails = "The phone1's model is not as expected or isn't displayed.";

        assertEquals(errorMessageIfTestFails, actualPhone1Model, expectedPhone1Model);
    }

    @Parameters("browser")
    @Test(testName = "Phone1PriceCheckTest", suiteName = "PhonesPageTest",
            groups = {"positive"}, description = "Verifies phones' prices are equal to the prices declared in the phones' resource bundle")
    public void verifyThatPhonesLinkSideMenuNavigatesToPhonesRow() throws MalformedURLException {
        ResourceBundle resourceBundle = ResourceBundle.getBundle("VisibleContent", Locale.US);
        ResourceBundle resourceBundlePhone1 = ResourceBundle.getBundle("phone1");

        String actualPhone1Price = new PhonesPage().getPhonePrice(0);
        String expectedPhone1Price = resourceBundlePhone1.getString("phonePrice");

        String errorMessageIfTestFails = "The phone1's price is not as expected or isn't displayed.";

        assertEquals(errorMessageIfTestFails, actualPhone1Price, expectedPhone1Price);
    }
}