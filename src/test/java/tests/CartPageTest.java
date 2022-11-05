package tests;

import customer.Customer;
import org.openqa.selenium.JavascriptExecutor;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pages.*;
import storedataservice.CustomerAccountCompiler;
import utils.ResourceBundleManagerClass;

import java.net.MalformedURLException;
import java.util.*;
import java.util.stream.Collectors;

import static java.lang.Integer.getInteger;
import static org.testng.AssertJUnit.assertEquals;
import static utils.ResourceBundleManagerClass.*;

//for the correct purchasing products feature Customer Account Page should be implemented
//here are preliminary tests without implemented Customer Account Page
public class CartPageTest extends RequiredConditions {
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
    @Test(testName = "AddAProductToACartTest", suiteName = "CartPageTest",
            groups = {"positive"}, description = "Verifies that a customer can add a product to a cart")
    public void verifyThatACustomerCanAddAProductToACart() throws MalformedURLException {

        ResourceBundle resourceBundleVisibleContent = ResourceBundle.getBundle("VisibleContent", Locale.US);
        ResourceBundle resourceBundlePhones = ResourceBundle.getBundle("phones");

        String query = "phone5";
        String orderIndex = (ResourceBundle.getBundle("queries").getString(query)).replace("?idp_=", "");
        String phoneModelName = resourceBundlePhones.getString(query);

        new HomePage().openPhonesLinkOnSideMenu(resourceBundleVisibleContent
                        .getString("PhonesLinkOnSideMenu"), getLaptopModelsListInResourceBundle(), getMonitorModelsListInResourceBundle())
                .openAPhoneModelPage(phoneModelName)
                .pushAddToCartButton(Integer.parseInt(orderIndex))
                .acceptAlertOnAddToCart();
        new HomePage().openCartPage();
        String actualProductModelsInCart = new CartPage().getProductOrderItemTitle();
        String expectedProductModelsInCart = resourceBundlePhones.getString("phone5");

        String errorMessageIfTestFails = "Actual products in a cart are not that were added.";

        assertEquals(errorMessageIfTestFails, actualProductModelsInCart, expectedProductModelsInCart);
    }

    @Parameters("browser")
    @Test(testName = "ListOfProductModelsAddedToACartTest", suiteName = "CartPageTest",
            groups = {"positive"}, description = "Verifies that a customer can add a products' list to a cart")
    public void verifyTheListOfProductModelsAddedToACart() throws MalformedURLException {
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
        new HomePage().openCartPage();
        List<String> actualProductOrderListInCart = new CartPage().getProductOrderTitlesListInACart().stream().sorted().collect(Collectors.toList());
        List<String> expectedProductOrderListInCart = getProductOrdersListInResourceBundle(getQueryKeysOrderedEnumerationFromResourceBundle()).stream().sorted().collect(Collectors.toList());
        String errorMessageIfTestFails = "Actual product list in a cart isn't conformed to the added one from the product store.";

        assertEquals(errorMessageIfTestFails, actualProductOrderListInCart, expectedProductOrderListInCart);
    }

    @Parameters("browser")
    @Test(testName = "MapOfPriceListOfProductsInACartTest", suiteName = "CartPageTest",
            groups = {"positive"}, description = "Verifies Price List of Products in a cart")
    public void verifyMapOfPriceListOfProductsInACart() throws MalformedURLException {
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
        new HomePage().openCartPage();
        SortedMap<String, Integer> actualProductOrderListInCart = new CartPage().getPhoneModelsPriceListAddedInACartMap();
        SortedMap<String, Integer> expectedProductOrderListInCart = getProductModelsPriceListMapInResourceBundle(productQueries);
        String errorMessageIfTestFails = "Actual product price list in a cart isn't conformed to the one from resource bundle.";

        assertEquals(errorMessageIfTestFails, actualProductOrderListInCart, expectedProductOrderListInCart);
    }

    @Parameters("browser")
    @Test(testName = "TotalPriceOfOrderedProductListInACartTest", suiteName = "CartPageTest",
            groups = {"positive"}, description = "Verifies that a total price displayed in a cart is equal to the sum of products prices")
    public void verifyThatTotalPriceInACartIsEqualToTheSumOfTheOrderedProductsPrices() throws MalformedURLException {
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
        new HomePage().openCartPage();
        int actualTotalPriceInCart = new CartPage().getTotalPriceDisplayedInACart();
        int expectedTotalPriceInCart = ResourceBundleManagerClass.getTotalPriceOfTheOrderedProductListInResourceBundle(productQueries);
        String errorMessageIfTestFails = "Actual Total Price displayed in a cart isn't equal to the sum of the ordered products prices from resource bundle.";

        assertEquals(errorMessageIfTestFails, expectedTotalPriceInCart, actualTotalPriceInCart);
    }
}