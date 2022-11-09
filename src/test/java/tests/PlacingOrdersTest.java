package tests;

import customer.Customer;
import org.openqa.selenium.JavascriptExecutor;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pages.CartPage;
import pages.HomePage;
import pages.PlaceOrderModalWindow;
import pages.PurchaseInfoConfirmationPopUpWindowPage;
import storedataservice.CustomerAccountCompiler;
import utils.ResourceBundleManagerClass;

import java.net.MalformedURLException;
import java.util.*;
import java.util.stream.Collectors;

import static org.testng.AssertJUnit.assertEquals;
import static org.testng.AssertJUnit.assertTrue;
import static utils.ResourceBundleManagerClass.*;

//for the correct purchasing products feature Customer Account Page should be implemented
//here are preliminary tests without implemented Customer Account Page
public class PlacingOrdersTest extends RequiredConditions {
    Customer account = CustomerAccountCompiler.withCredentialFromProperty();

    @Parameters("browser")
    @BeforeTest
    public void logInAndAddProductsToACart() throws MalformedURLException {

        ResourceBundle resourceBundleVisibleContent = ResourceBundle.getBundle("VisibleContent", Locale.US);
        new HomePage()
                .openPage()
                .openLogInModalWindowOnTopMenu()
                .logIn(account);

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
    }

    @Parameters("browser")
    @Test(testName = "TotalPriceOfOrderedProductListInPlaceOrderFormTest", suiteName = "PlacingOrdersTest",
            groups = {"positive"}, description = "Verifies that a total price displayed in Place Order form is equal the price displayed in a cart with ordered products")
    public void verifyThatTotalPriceDisplayedInPlaceOrderFormIsEqualToThePriceDisplayedInACart() throws MalformedURLException {

        new HomePage().openCartPage().placeOrder();
        int expectedTotalPriceDisplayedInACart = new CartPage().getTotalPriceDisplayedInACart();
        int actualTotalPriceDisplayedInPlaceOrderForm = new PlaceOrderModalWindow().getTotalPriceOnTopOfPlaceOrderWindow();

        String errorMessageIfTestFails = "Actual Total Price displayed in a cart isn't equal to the sum of the ordered products prices from resource bundle.";

        assertEquals(errorMessageIfTestFails, expectedTotalPriceDisplayedInACart, actualTotalPriceDisplayedInPlaceOrderForm);
    }

    @Parameters("browser")
    @Test(testName = "PurchaseMessageConfirmationDisplayTest", suiteName = "PlacingOrdersTest",
            groups = {"positive"}, description = "Verifies that a purchase message confirmation is displayed.")
    public void verifyPurchaseMessageConfirmationDisplay() throws MalformedURLException {

        boolean purchaseConfirmationMessageIsDisplayed = new HomePage().openCartPage()
                .placeOrder()
                .purchaseTheOrder(account)
                .orderInfoContentOnPurchaseConfirmationWindowPageIsDisplayed();

        String errorMessageIfTestFails = "Purchase message confirmation isn't displayed.";

        assertTrue(errorMessageIfTestFails, purchaseConfirmationMessageIsDisplayed);
    }

    @Parameters("browser")
    @Test(testName = "PurchaseMessageConfirmationContextTest", suiteName = "PlacingOrdersTest",
            groups = {"positive"}, description = "Verifies that a relevant purchase message confirmation context is displayed")
    public void verifyAppropriateMessageConfirmationContextDisplay() throws MalformedURLException {
        ResourceBundle resourceBundleVisibleContent = ResourceBundle.getBundle("VisibleContent", Locale.US);

        String actualMessageConfirmationContextDisplayed = new HomePage().openCartPage()
                .placeOrder()
                .purchaseTheOrder(account)
                .getPurchaseMessageConfirmationContextDisplayed();
        String expectedMessageConfirmationContextDisplayed = resourceBundleVisibleContent.getString("PurchaseConfirmationMessageContext");

        String errorMessageIfTestFails = "Purchase message confirmation isn't displayed.";

        assertEquals(errorMessageIfTestFails, expectedMessageConfirmationContextDisplayed, actualMessageConfirmationContextDisplayed);
    }

    @Parameters("browser")
    @Test(testName = "ConformanceOfDisplayedOrderInfoListToTheInputOneTest", suiteName = "PlacingOrdersTest",
            groups = {"positive"}, description = "Verifies a conformance of the displayed order info list to the input order info list")
    public void verifyConformaceOfDisplayedOrderInfoListToTheInputOrderInfoList() throws MalformedURLException {
        List<String> actualDisplayedOrderInfoList = new HomePage().openCartPage()
                .placeOrder()
                .purchaseTheOrder(account)
                .getDisplayedOrderInfoListWithoutIDOrder();
        List<String> expectedDisplayedOrderInfoList = new PlaceOrderModalWindow().getInputOrderInfoListWithCurrentDateWithoutIDOrder(account);

        String errorMessageIfTestFails = "The displayed order info list in confirmation doesn't conforms to the input order info list";

        assertEquals(errorMessageIfTestFails, expectedDisplayedOrderInfoList, actualDisplayedOrderInfoList);
    }
}
