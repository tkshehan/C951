package model;

import java.util.Objects;

public final class Contact {
    private final int id;
    private final String name;
    private final String email;
    private int appointmentCount;

    public Contact(int id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    public Contact(int id, String name, String email, int count) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.appointmentCount = count;
    }

    @Override
    public String toString() {
        return name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public void setAppointmentCount(int count) {
        this.appointmentCount = count;
    }

    public int getAppointmentCount() {
        return appointmentCount;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (Contact) obj;
        return this.id == that.id &&
                Objects.equals(this.name, that.name) &&
                Objects.equals(this.email, that.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, email);
    }

}
