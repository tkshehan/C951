package model.reports;

public record AppointmentsByMonthAndType(String month, String type, int count) {

    public String getMonth() {
        return month;
    }

    public String getType() {
        return type;
    }

    public int getCount() {
        return count;
    }
}
