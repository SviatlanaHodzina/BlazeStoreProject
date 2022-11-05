package utils;

import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pages.CartPage;
import pages.HomePage;

import java.net.MalformedURLException;
import java.util.*;
import java.util.stream.Collectors;

import static org.testng.AssertJUnit.assertEquals;

public class ResourceBundleManagerClass {

    public static List<String> getPhoneModelsListInResourceBundle() {
        ResourceBundle resourceBundlePhones = ResourceBundle.getBundle("phones");
        Enumeration<String> phoneKeys = resourceBundlePhones.getKeys();
        List<String> phoneKeysList = new ArrayList<>();
        while (phoneKeys.hasMoreElements()) {
            String phoneKey = phoneKeys.nextElement();
            String phoneValue = resourceBundlePhones.getString(phoneKey);
            phoneKeysList.add(phoneValue);
        }
        return phoneKeysList;
    }

    public static List<String> getLaptopModelsListInResourceBundle() {
        ResourceBundle resourceBundleLaptops = ResourceBundle.getBundle("laptops");
        Enumeration<String> laptopKeys = resourceBundleLaptops.getKeys();
        List<String> laptopKeysList = new ArrayList<>();
        while (laptopKeys.hasMoreElements()) {
            String laptopKey = laptopKeys.nextElement();
            String laptopValue = resourceBundleLaptops.getString(laptopKey);
            laptopKeysList.add(laptopValue);
        }
        return laptopKeysList.stream().sorted().collect(Collectors.toList());
    }

    public static List<String> getMonitorModelsListInResourceBundle() {
        ResourceBundle resourceBundleMonitors = ResourceBundle.getBundle("monitors");
        Enumeration<String> monitorKeys = resourceBundleMonitors.getKeys();
        List<String> monitorKeysList = new ArrayList<>();
        while (monitorKeys.hasMoreElements()) {
            String monitorKey = monitorKeys.nextElement();
            String monitorValue = resourceBundleMonitors.getString(monitorKey);
            monitorKeysList.add(monitorValue);
        }
        return monitorKeysList.stream().sorted().collect(Collectors.toList());
    }

    public static Map<String, Integer> getPhoneModelsPriceMapInResourceBundle() {
        ResourceBundle resourceBundlePhonePrices = ResourceBundle.getBundle("PhonesPriceList");
        Enumeration<String> phoneKeys = resourceBundlePhonePrices.getKeys();
        Map<String, Integer> phoneKeysMap = new HashMap<>();
        while (phoneKeys.hasMoreElements()) {
            String phoneKey = phoneKeys.nextElement();
            int phonePriceValue = Integer.parseInt((resourceBundlePhonePrices.getString(phoneKey)).replace("$", ""));
            String phoneModelKey = ResourceBundle.getBundle("phones").getString(phoneKey);
            phoneKeysMap.put(phoneModelKey, phonePriceValue);
        }
        return phoneKeysMap;
    }

    public static Map<String, Integer> getLaptopModelsPriceMapInResourceBundle() {
        ResourceBundle resourceBundleLaptopPrices = ResourceBundle.getBundle("LaptopsPriceList");
        Enumeration<String> laptopKeys = resourceBundleLaptopPrices.getKeys();
        Map<String, Integer> laptopKeysMap = new HashMap<>();
        while (laptopKeys.hasMoreElements()) {
            String laptopKey = laptopKeys.nextElement();
            int laptopPriceValue = Integer.parseInt((resourceBundleLaptopPrices.getString(laptopKey)).replace("$", ""));
            String phoneModelKey = ResourceBundle.getBundle("phones").getString(laptopKey);
            laptopKeysMap.put(phoneModelKey, laptopPriceValue);
        }
        return laptopKeysMap;
    }

    public static Map<String, Integer> getMonitorModelsPriceMapInResourceBundle() {
        ResourceBundle resourceBundleMonitorPrices = ResourceBundle.getBundle("MonitorsPriceList");
        Enumeration<String> monitorKeys = resourceBundleMonitorPrices.getKeys();
        Map<String, Integer> monitorKeysMap = new HashMap<>();
        while (monitorKeys.hasMoreElements()) {
            String monitorKey = monitorKeys.nextElement();
            int monitorPriceValue = Integer.parseInt((resourceBundleMonitorPrices.getString(monitorKey)).replace("$", ""));
            String monitorModelKey = ResourceBundle.getBundle("monitors").getString(monitorKey);
            monitorKeysMap.put(monitorModelKey, monitorPriceValue);
        }
        return monitorKeysMap;
    }

    public static Map<String, Integer> getProductModelsQueryIndexMapInResourceBundle(Enumeration<String> productQueries) {
        productQueries = ResourceBundle.getBundle("queries").getKeys();
        Map<String, Integer> productKeysMap = new HashMap<>();
        while (productQueries.hasMoreElements()) {
            String query = productQueries.nextElement();
            int productOrderedIndex = Integer.parseInt(ResourceBundle.getBundle("queries").getString(query).replace("?idp_=", ""));
            if (productOrderedIndex >= 8 && productOrderedIndex <= 15 && productOrderedIndex != 10 && productOrderedIndex != 14) {
                productKeysMap.put(ResourceBundle.getBundle("laptops").getString(query), productOrderedIndex);
            } else if (productOrderedIndex >= 1 && productOrderedIndex <= 7) {
                productKeysMap.put(ResourceBundle.getBundle("phones").getString(query), productOrderedIndex);
            } else if ((productOrderedIndex == 10) && (productOrderedIndex == 14))
                productKeysMap.put(ResourceBundle.getBundle("monitors").getString(query), productOrderedIndex);
        }
        return productKeysMap;
    }

    public static SortedMap<String, Integer> getProductModelsPriceListMapInResourceBundle(Enumeration<String> queryKeys) {
        queryKeys = ResourceBundle.getBundle("queries").getKeys();
        SortedMap<String, Integer> productKeysMap = new TreeMap<>(new HashMap<>());
        while (queryKeys.hasMoreElements()) {
            String query = queryKeys.nextElement();
            int productOrderedIndex = Integer.parseInt(ResourceBundle.getBundle("queries").getString(query).replace("?idp_=", ""));
            if (productOrderedIndex >= 8 && productOrderedIndex <= 15 && productOrderedIndex != 10 && productOrderedIndex != 14) {
                int laptopPriceValue = Integer.parseInt(ResourceBundle.getBundle("LaptopsPriceList").getString(query).replace("$", ""));
                String laptopModelName = ResourceBundle.getBundle("laptops").getString(query);
                productKeysMap.put(laptopModelName, laptopPriceValue);
            } else if (productOrderedIndex >= 1 && productOrderedIndex <= 7) {
                int phonePriceValue = Integer.parseInt(ResourceBundle.getBundle("PhonesPriceList").getString(query).replace("$", ""));
                String phoneModelName = ResourceBundle.getBundle("phones").getString(query);
                productKeysMap.put(phoneModelName, phonePriceValue);
            } else if ((productOrderedIndex == 10) && (productOrderedIndex == 14)) {
                int monitorPriceValue = Integer.parseInt(ResourceBundle.getBundle("MonitorsPriceList").getString(query).replace("$", ""));
                String monitorModelName = ResourceBundle.getBundle("monitors").getString(query);
                productKeysMap.put(monitorModelName, monitorPriceValue);
            }
        }
        return productKeysMap;
    }

    public static List<String> getQueryKeyListInResourceBundle() {
        ResourceBundle resourceBundleQueries = ResourceBundle.getBundle("queries");
        Enumeration<String> queryKeys = resourceBundleQueries.getKeys();
        List<String> productKeysList = new ArrayList<>();
        while (queryKeys.hasMoreElements()) {
            String queryKey = queryKeys.nextElement();
            productKeysList.add(queryKey);
        }
        return productKeysList;
    }

    public static List<String> getProductOrdersListInResourceBundle(Enumeration<String> queryKeys) {
        List<String> productKeysList = new ArrayList<>();
        while (queryKeys.hasMoreElements()) {
            String queryKey = queryKeys.nextElement();
            int queryIndex = Integer.parseInt(((ResourceBundle.getBundle("queries").getString(queryKey)).replace("?idp_=", "")));
            if (queryIndex >= 8 && queryIndex <= 15 && queryIndex != 10 && queryIndex != 14) {
                productKeysList.add(ResourceBundle.getBundle("laptops").getString("laptop" + String.valueOf(queryIndex)));
            } else if (queryIndex >= 1 && queryIndex <= 7) {
                productKeysList.add(ResourceBundle.getBundle("phones").getString("phone" + String.valueOf(queryIndex)));
            }
        }
        return productKeysList;
    }

    public static int getTotalPriceOfTheOrderedProductListInResourceBundle(Enumeration<String> queryKeys) {
        int sum = 0;
        int productListSize = new ArrayList<>(getProductModelsPriceListMapInResourceBundle(queryKeys).values()).size();
        for (int i = 0; i < productListSize; i++)
            sum += new ArrayList<>(getProductModelsPriceListMapInResourceBundle(queryKeys).values()).get(i);
        return sum;
    }

    public static int generateRandomOrdersNumber() {
        return new Random().nextInt(getQueryKeyListInResourceBundle().size());
    }

    public static Enumeration<String> getQueryKeysOrderedEnumerationFromResourceBundle() {
        ResourceBundle resourceBundleQueries = ResourceBundle.getBundle("queries");
        Enumeration<String> queryKeys = resourceBundleQueries.getKeys();
        Vector<String> query = new Vector<>();
        while (queryKeys.hasMoreElements()) {
            String queryKey = queryKeys.nextElement();
            query.add(queryKey);
        }
        return query.elements();
    }

    public static Enumeration<String> getRandomQueryKeysOrderedEnumerationFromResourceBundle() {
        ResourceBundle resourceBundleQueries = ResourceBundle.getBundle("queries");
        Enumeration<String> queryKeys = resourceBundleQueries.getKeys();
        Vector<String> query = new Vector<>();
        while (queryKeys.hasMoreElements()) {
            for (int i = 0; i < generateRandomOrdersNumber(); i++) {
                String queryKey = queryKeys.nextElement();
                query.add(queryKey);
            }
        }
        return query.elements();
    }
}