package utils;

import java.text.NumberFormat;
import java.util.Currency;
import java.util.Locale;

public class CurrencyFormatter {
    private static String language;
    private static String country;

    public String getCurrencyAmount(double currencyAmount) {
        Locale countryName = new Locale(language, country);
        Currency currency = Currency.getInstance(countryName);
        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(countryName);
        return (currency.getDisplayName() + currencyFormat.format(currencyAmount));
    }
}