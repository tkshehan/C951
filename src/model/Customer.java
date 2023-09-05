package model;

/** This class models customer data for appointments */
public class Customer {
    /** The customer id. */
    private final int id;
    /** The customer's postal code */
    private String postalCode;
    /** The customer's name */
    private String name;
    /** The customer's address. */
    private String address;
    /** The customer's phone number. */
    private String phoneNumber;
    /** The customer's state/province. */
    private String division;
    /** The customer's country. */
    private String country;

    public Customer(int id, String postalCode, String name, String address, String phoneNumber, String division, String country) {
        this.id = id;
        this.postalCode = postalCode;
        this.name = name;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.division = division;
        this.country = country;
    }

    /** Gets the customer's postal code.
     * @return The postal code to return.
     */
    public String getPostalCode() {
        return postalCode;
    }

    /** Sets the customer's postal code.
     * @param postalCode The postal code to set.
     */
    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    /** Gets the customers id.
     * @return The id to return.
     */
    public int getId() {
        return id;
    }

    /** Gets the customer's name.
     * @return The name to return.
     */
    public String getName() {
        return name;
    }

    /** Sets the customer's name.
     * @param name The name to set.
     */
    public void setName(String name) {
        this.name = name;
    }

    /** Gets the customer's address.
     * @return The address to return.
     */
    public String getAddress() {
        return address;
    }

    /** Sets the customer's address.
     * @param address The address to set.
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /** Gets the customer's phone number.
     * @return The number to return.
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /** Sets the customer's phone number.
     * @param phoneNumber The number to set.
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    /** Gets the customers state/province.
     * @return The division to return.
     */
    public String getDivision() {
        return division;
    }

    /** Sets the customer's state/province.
     * @param division The division to set.
     */
    public void setDivision(String division) {
        this.division = division;
    }

    /** Gets the customers country.
     * @return The country to return.
     */
    public String getCountry() {
        return country;
    }

    /** Sets the customers country.
     * @param country The country to set.
     */
    public void setCountry(String country) {
        this.country = country;
    }

    /** Gets the name of the user.
     * @return The name to return.
     */
    @Override
    public String toString() {
        return name;
    }
}
