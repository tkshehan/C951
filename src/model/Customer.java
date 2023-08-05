package model;

public class Customer {
    private final int id;
    private String postalCode;
    private String name;
    private String address;
    private String phoneNumber;
    private String division;
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

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getDivision() {
        return division;
    }

    public void setDivision(String division) {
        this.division = division;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @Override
    public String toString() {
        return name;
    }
}
