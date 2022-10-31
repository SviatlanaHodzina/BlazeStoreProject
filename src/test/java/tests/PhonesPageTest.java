package tests;

import org.testng.AssertJUnit;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pages.HomePage;
import pages.PhonesPage;

import java.net.MalformedURLException;
import java.util.*;
import java.util.stream.Collectors;

import static org.testng.AssertJUnit.*;
import static pages.HomePage.*;

//the products' descriptions should be implemented as technical specifications with appropriate content
//currency sign ($,€,£,¥ etc should be placed in DOM separately from their value)
public class PhonesPageTest extends RequiredConditions {
    @Parameters("browser")
    @BeforeTest
    public void openPhonesPage() throws MalformedURLException {
        ResourceBundle resourceBundleVisibleContent = ResourceBundle.getBundle("VisibleContent", Locale.US);
        new HomePage()
                .openPage()
                .openPhonesLinkOnSideMenu(resourceBundleVisibleContent.getString("PhonesLinkOnSideMenu"),
                        getLaptopModelsListInResourceBundle(), getMonitorModelsListInResourceBundle());
    }

    @Parameters("browser")
    @Test(testName = "PhonesSortingTest", suiteName = "PhonesPageTest",
            groups = {"positive"}, description = "Verifies That 'Phones' link side menu navigates to phones row")
    public void verifyThatPhonesLinkSortsTheProductsByTheCategoryPhones() throws MalformedURLException {
        List<String> actualPhoneHrefModelsList = new PhonesPage().getPhoneHrefModelsList()
                .stream().sorted().collect(Collectors.toList());
        List<String> expectedPhoneModelsList = new HomePage().getPhoneModelsListInResourceBundle()
                .stream().sorted().collect(Collectors.toList());

        String errorMessageIfTestFails = "The 'Phones' link does not sort the products by the category 'Phones', but contains other product's category.";

        assertEquals(errorMessageIfTestFails, expectedPhoneModelsList, actualPhoneHrefModelsList);
    }

    @Parameters("browser")
    @Test(testName = "PhonesPriceMapTest", suiteName = "PhonesPageTest",
            groups = {"positive"}, description = "Verifies that the 'Phones price list' is conformed with the one displayed " +
            "in Resource Bundle (analogue of data base)")
    public void verifyThatPhonesPriceListIsConformedWithResourceBundle() throws MalformedURLException {
        Map<String, Integer> actualPhonesPriceList = new PhonesPage().getPhoneModelsPriceMap();
        Map<String, Integer> expectedPhonesPriceList = new HomePage().getPhoneModelsPriceMapInResourceBundle();
        String errorMessageIfTestFails = "The 'Phones price list' is not conformed with the one displayed in Resource Bundle.";

        assertEquals(errorMessageIfTestFails, expectedPhonesPriceList, actualPhonesPriceList);
    }

    @Parameters("browser")
    @Test(testName = "PhonesRowSizeTest", suiteName = "PhonesPageTest",
            groups = {"Positive"}, description = "Verifies that 'Phones' row size equals size of phones in resource Bundle.")
    public void verifyThatPhonesRowEqualsTheSizeOfPhoneRowInResourceBundle() throws MalformedURLException {
        int actualPhoneRowSize = new PhonesPage().getSizeOfPhonesList();
        int expectedPhoneRowSize = new HomePage().getPhoneModelsListInResourceBundle().size();
        String errorMessageIfTestFails = "The 'Phones' row is not equal to the size of phone models in Resource bundle.";

        assertEquals(errorMessageIfTestFails, expectedPhoneRowSize, actualPhoneRowSize);
    }

    @Parameters("browser")
    @Test(testName = "PhonesRowContainerDoesNotContainLaptopItemsTest", suiteName = "PhonesPageTest",
            groups = {"negative"}, description = "Verifies that 'Phones' row does not contain laptop items.")
    public void verifyThatPhonesRowDoesNotContainLaptops() throws MalformedURLException {

        List<String> actualPhoneHrefModelsList = new PhonesPage().getPhoneHrefModelsList();
        List<String> unExpectedLaptopModelsList = new HomePage().getLaptopModelsListInResourceBundle();
        String errorMessageIfTestFails = "The 'Phones' row contain unexpected laptop item/ items";

        AssertJUnit.assertFalse(errorMessageIfTestFails, actualPhoneHrefModelsList.contains(unExpectedLaptopModelsList));
    }

    @Parameters("browser")
    @Test(testName = "PhonesRowContainerDoesNotContainMonitorItemsTest", suiteName = "PhonesPageTest",
            groups = {"negative"}, description = "Verifies that 'Phones' row does not contain monitor items.")
    public void verifyThatPhonesRowDoesNotContainMonitors() throws MalformedURLException {

        List<String> actualPhoneHrefModelsList = new PhonesPage().getPhoneHrefModelsList();
        List<String> unExpectedMonitorModelsList = new HomePage().getMonitorModelsListInResourceBundle();
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
    public void verifyThatThePhone1PriceIsDisplayedAsInResourceBundle() throws MalformedURLException {
        ResourceBundle resourceBundlePhone1 = ResourceBundle.getBundle("phone1");

        String actualPhone1Price = new PhonesPage().getPhonePrice(0);
        String expectedPhone1Price = resourceBundlePhone1.getString("phonePriceDollarUSA");

        String errorMessageIfTestFails = "The phone1's price is not as expected or isn't displayed.";

        assertEquals(errorMessageIfTestFails, actualPhone1Price, expectedPhone1Price);
    }
}
