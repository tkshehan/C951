package model;

public class Customer {
    private final int id;
    private int postalCode;
    private String name;
    private String address;
    private String phoneNumber;

    public Customer(int id, int postalCode, String name, String address, String phoneNumber) {
        this.id = id;
        this.postalCode = postalCode;
        this.name = name;
        this.address = address;
        this.phoneNumber = phoneNumber;
    }

    public int getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(int postalCode) {
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
}
