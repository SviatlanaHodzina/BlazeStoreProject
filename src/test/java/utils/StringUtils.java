package utils;

import java.util.Locale;
import java.util.Random;
import java.util.ResourceBundle;

public class StringUtils {

    private static final String ALFANUMERICAL_ALL_CAPS = "1234567890abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ!@#$%^&*().,+";
    private static final String ALFANUMERICAL_FOR_USERNAME = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

    private static final Random random = new Random();

    public static String getRandomString(int stringLength) {
        StringBuilder stringbuilder = new StringBuilder(stringLength);
        for (int i = 0; i < stringLength; i++) {
            stringbuilder.append(ALFANUMERICAL_ALL_CAPS.charAt(random.nextInt(ALFANUMERICAL_ALL_CAPS.length())));
        }
        return stringbuilder.toString();
    }

    public static String getRandomStringForUsernameSignUp(int stringLength) {
        StringBuilder stringbuilder = new StringBuilder(stringLength);
        for (int i = 0; i < stringLength; i++) {
            stringbuilder.append(ALFANUMERICAL_FOR_USERNAME
                    .charAt(random.nextInt(ALFANUMERICAL_FOR_USERNAME.length())));
        }
        return stringbuilder.toString();
    }

    public static String generateRandomStringUsernameLength() {
        ResourceBundle resourceBundle = ResourceBundle.getBundle("PasswordAndCreditCardSettings", Locale.US);
        int upperBoundLengthForUsername = Integer.parseInt(resourceBundle.getString("upperBoundLengthForUsername"));
        int randomWeakPasswordLength = new Random().nextInt(upperBoundLengthForUsername);
        return getRandomStringForUsernameSignUp(randomWeakPasswordLength);
    }

    public static String generateRandomWeakPasswordWithRandomLength() {
        ResourceBundle resourceBundle = ResourceBundle.getBundle("PasswordAndCreditCardSettings", Locale.US);
        int upperBoundLengthOfWeakPassword = Integer.parseInt(resourceBundle.getString("upperBoundLengthOfWeakPassword"));
        int randomWeakPasswordLength = new Random().nextInt(upperBoundLengthOfWeakPassword);
        return getRandomString(randomWeakPasswordLength);
    }

    public static String generateRandomStrongPasswordWithRandomLength() {
        ResourceBundle resourceBundle = ResourceBundle.getBundle("PasswordAndCreditCardSettings", Locale.US);
        int upperBoundLengthOfWeakPassword = Integer.parseInt(resourceBundle.getString("upperBoundLengthOfWeakPassword"));
        int upperBoundLengthOfStrongPassword = Integer.parseInt(resourceBundle.getString("upperBoundLengthOfStrongPassword"));
        int randomStrongPasswordLength = upperBoundLengthOfWeakPassword + new Random().nextInt(upperBoundLengthOfStrongPassword);
        return getRandomString(randomStrongPasswordLength);
    }
}
