package storedataservice;

import customer.Customer;

public class CustomerAccountCompiler {
    public static final String TESTDATA_CUSTOMER_FIRST_NAME = "testdata.account.firstName";
    public static final String TESTDATA_CUSTOMER_SURNAME = "testdata.account.surname";
    public static final String TESTDATA_CUSTOMER_COUNTRY = "testdata.account.country";
    public static final String TESTDATA_CUSTOMER_CITY = "testdata.account.city";
    public static final String TESTDATA_CUSTOMER_CREDIT_CARD = "testdata.account.creditcard";
    public static final String TESTDATA_CUSTOMER_CREDIT_CARD_VALID_THRU_MONTH = "testdata.account.validthrumonth";
    public static final String TESTDATA_CUSTOMER_CREDIT_CARD_VALID_THRU_YEAR = "testdata.account.validthruyear";
    public static final String TESTDATA_CUSTOMER_EMAIL = "testdata.account.email";
    public static final String TESTDATA_CUSTOMER_PASSWORD = "testdata.account.password";

    public static Customer withCredentialFromProperty() {
        return new Customer(TestDataReader.getTestData(TESTDATA_CUSTOMER_FIRST_NAME),
                TestDataReader.getTestData(TESTDATA_CUSTOMER_SURNAME),
                TestDataReader.getTestData(TESTDATA_CUSTOMER_COUNTRY),
                TestDataReader.getTestData(TESTDATA_CUSTOMER_CITY),
                TestDataReader.getTestData(TESTDATA_CUSTOMER_CREDIT_CARD),
                TestDataReader.getTestData(TESTDATA_CUSTOMER_CREDIT_CARD_VALID_THRU_MONTH),
                TestDataReader.getTestData(TESTDATA_CUSTOMER_CREDIT_CARD_VALID_THRU_YEAR),
                TestDataReader.getTestData(TESTDATA_CUSTOMER_EMAIL),
                TestDataReader.getTestData(TESTDATA_CUSTOMER_PASSWORD));
    }
}