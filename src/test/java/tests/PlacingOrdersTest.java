package tests;

import customer.Customer;
import org.openqa.selenium.JavascriptExecutor;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pages.CartPage;
import pages.HomePage;
import pages.PlaceOrderModalWindow;
import storedataservice.CustomerAccountCompiler;
import utils.ResourceBundleManagerClass;

import java.net.MalformedURLException;
import java.util.*;
import java.util.stream.Collectors;

import static org.testng.AssertJUnit.assertEquals;
import static utils.ResourceBundleManagerClass.*;

//for the correct purchasing products feature Customer Account Page should be implemented
//here are preliminary tests without implemented Customer Account Page
public class PlacingOrdersTest extends RequiredConditions {
    @Parameters("browser")
    @BeforeTest
    public void logInExistingAccount() throws MalformedURLException {
        Customer account = CustomerAccountCompiler.withCredentialFromProperty();
        new HomePage()
                .openPage()
                .openLogInModalWindowOnTopMenu()
                .logIn(account);
    }

    @Parameters("browser")
    @Test(testName = "TotalPriceOfOrderedProductListInPlaceOrderFormTest", suiteName = "PlacingOrdersTest",
            groups = {"positive"}, description = "Verifies that a total price displayed in Place Order form is equal the price displayed in a cart with ordered products")
    public void verifyThatTotalPriceDisplayedInPlaceOrderFormIsEqualToThePriceDisplayedInACart() throws MalformedURLException {
        ResourceBundle resourceBundleVisibleContent = ResourceBundle.getBundle("VisibleContent", Locale.US);

        Enumeration<String> productQueries = ResourceBundle.getBundle("queries").getKeys();

        while (productQueries.hasMoreElements()) {
            String query = productQueries.nextElement();
            int productOrderedIndex = Integer.parseInt(ResourceBundle.getBundle("queries").getString(query).replace("?idp_=", ""));
            if (productOrderedIndex >= 8 && productOrderedIndex <= 15 && productOrderedIndex != 10 && productOrderedIndex != 14) {
                new HomePage().openPage()
                        .openLaptopsLinkOnSideMenu(resourceBundleVisibleContent.getString("LaptopsLinkOnSideMenu"), getPhoneModelsListInResourceBundle(), getMonitorModelsListInResourceBundle())
                        .openALaptopModelPage(ResourceBundle.getBundle("laptops").getString(query))
                        .pushAddToCartButton(productOrderedIndex)
                        .acceptAlertOnAddToCart();
                new HomePage().openPage().openLaptopsLinkOnSideMenu(resourceBundleVisibleContent.getString("LaptopsLinkOnSideMenu"), getPhoneModelsListInResourceBundle(), getMonitorModelsListInResourceBundle());
            } else if (productOrderedIndex >= 1 && productOrderedIndex <= 7) {
                new HomePage().openPage()
                        .openPhonesLinkOnSideMenu(resourceBundleVisibleContent.getString("PhonesLinkOnSideMenu"), getLaptopModelsListInResourceBundle(), getMonitorModelsListInResourceBundle())
                        .openAPhoneModelPage(ResourceBundle.getBundle("phones").getString(query))
                        .pushAddToCartButton(productOrderedIndex)
                        .acceptAlertOnAddToCart();
                new HomePage().openPage().openPhonesLinkOnSideMenu(resourceBundleVisibleContent.getString("PhonesLinkOnSideMenu"), getLaptopModelsListInResourceBundle(), getMonitorModelsListInResourceBundle());
            } else if ((productOrderedIndex == 10) && (productOrderedIndex == 14)) {
                new HomePage().openPage();
                ((JavascriptExecutor) driver).executeScript("window.scrollBy(0,300)", "");
                new HomePage().openMonitorsLinkOnSideMenu(resourceBundleVisibleContent.getString("MonitorsLinkOnSideMenu"), getLaptopModelsListInResourceBundle(), getPhoneModelsListInResourceBundle())
                        .openAMonitorModelPage(ResourceBundle.getBundle("monitors").getString(query))
                        .pushAddToCartButton(productOrderedIndex)
                        .acceptAlertOnAddToCart();
                new HomePage().openPage().openMonitorsLinkOnSideMenu(resourceBundleVisibleContent.getString("MonitorsLinkOnSideMenu"), getLaptopModelsListInResourceBundle(), getPhoneModelsListInResourceBundle());

            }
        }
        new HomePage().openCartPage().placeOrder();
        int expectedTotalPriceDisplayedInACart = new CartPage().getTotalPriceDisplayedInACart();
        int actualTotalPriceDisplayedInPlaceOrderForm = new PlaceOrderModalWindow().getTotalPriceOnTopOfPlaceOrderWindow();

        String errorMessageIfTestFails = "Actual Total Price displayed in a cart isn't equal to the sum of the ordered products prices from resource bundle.";

        assertEquals(errorMessageIfTestFails, expectedTotalPriceDisplayedInACart, actualTotalPriceDisplayedInPlaceOrderForm);
    }
}