package model;

public record User(int id, String name, String password) {

    @Override
    public String toString() {
        return name;
    }
}
