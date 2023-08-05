package model;

public record Contact(int id, String name, String email) {

    @Override
    public String toString() {
        return name;
    }
}
