package customer;

import java.util.Objects;

public class Customer {
    private String firstName;
    private String surname;
    private String country;
    private String city;
    private String creditCard;
    private String validThruMonth;
    private String validThruYear;
    private String email;
    private String password;

    public Customer(String firstName, String surname, String country,
                    String city, String creditCard, String validThruMonth,
                    String validThruYear, String email, String password) {

        this.firstName = firstName;
        this.surname = surname;
        this.country = country;
        this.city = city;
        this.creditCard = creditCard;
        this.validThruMonth = validThruMonth;
        this.validThruYear = validThruYear;
        this.email = email;
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCreditCard() {
        return creditCard;
    }

    public void setCreditCard(String creditCard) {
        this.creditCard = creditCard;
    }

    public String getValidThruMonth() {
        return validThruMonth;
    }

    public void setValidThruMonth(String validThruMonth) {
        this.validThruMonth = validThruMonth;
    }

    public String getValidThruYear() {
        return validThruYear;
    }

    public void setValidThruYear(String validThruYear) {
        this.validThruYear = validThruYear;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "firstName='" + firstName + '\'' +
                ", surname='" + surname + '\'' +
                ", country='" + country + '\'' +
                ", city='" + city + '\'' +
                ", creditCard='" + creditCard + '\'' +
                ", validThruMonth='" + validThruMonth + '\'' +
                ", validThruYear='" + validThruYear + '\'' +
                ", email='" + email + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Customer account = (Customer) o;
        return firstName.equals(account.firstName) && surname.equals(account.surname)
                && country.equals(account.country) && city.equals(account.city)
                && creditCard.equals(account.creditCard) && validThruMonth.equals(account.validThruMonth)
                && validThruYear.equals(account.validThruYear) && email.equals(account.email)
                && password.equals(account.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, surname, country, city, creditCard, validThruMonth,
                validThruYear, email, password);
    }
}
