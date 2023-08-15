package controller;

import Database.ContactDao;
import Database.CustomerDao;
import Database.UserDao;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.*;

import java.net.URL;
import java.sql.SQLException;
import java.time.*;
import java.util.ResourceBundle;

public abstract class AppointmentCtrl implements Initializable {

    public Label windowTitle;
    public TextField idField;
    public TextField titleField;
    public TextField locationField;
    public TextField typeField;
    public ComboBox<User> userCBox;
    public ComboBox<Customer> customerCBox;
    public ComboBox<Contact> contactCBox;
    public DatePicker startDate;
    public ComboBox<LocalTime> startTime;
    public ComboBox<AppointmentDuration> duration;
    public TextField descriptionField;

    private final ZoneId BUSINESSTIMEZONE = ZoneId.of("American/New_York"); // American/New_York
    private final int OPENHOUR = 8; // 8am
    private final int CLOSEHOUR = 17; // 5pm



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setContacts();
        setCustomers();
        setUsers();
        setDurations();
    }

    protected void setBusinessHours() {
        ZonedDateTime localTime = startDate.getValue().atStartOfDay(ZoneId.systemDefault());

        // For every hour in local day, check if between business hours in businessTimeZone
        for (int i = 0; i < 24; i++, localTime = localTime.plusHours(1)) {
            int businessHour = localTime.withZoneSameInstant(BUSINESSTIMEZONE).getHour();
            if(businessHour >= OPENHOUR && businessHour < CLOSEHOUR) {
                for (int o = 0; o < 4; o++) {
                    startTime.getItems().add(localTime.plusMinutes(o * 15).toLocalTime());
                }
            }
        }

    }

    public abstract void submitAppointment();

    private void setDurations() {
        ObservableList<AppointmentDuration> durations = FXCollections.observableArrayList();
        int fifteenMinIntervals = 16;
        for (int i = 1; i <= fifteenMinIntervals; i++) {
            durations.add(new AppointmentDuration(
                    Duration.ofMinutes(15L * i)
            ));
        }
        duration.setItems(durations);
    }


    private void setCustomers() {
        try {
            ObservableList<Customer> customers = CustomerDao.getAllCustomers();
            customerCBox.setItems(customers);
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    private void setContacts() {
        try {
            ObservableList<Contact> contacts = ContactDao.getAllContacts();
            contactCBox.setItems(contacts);
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    private void setUsers() {
        try {
            ObservableList<User> users = UserDao.getAllUsers();
            userCBox.setItems(users);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    protected boolean validateAppointment() {
        String errorMessage = "";
        boolean valid = true;

        String title = titleField.getText().trim();
        if (title.isEmpty()) {
            errorMessage = errorMessage.concat(
                    "Title must not be empty\n"
            );
            valid = false;
        }

        String location = locationField.getText().trim();
        if (location.isEmpty()) {
            errorMessage = errorMessage.concat("Location must not be empty\n");
            valid = false;
        }

        String type = typeField.getText().trim();
        if (type.isEmpty()) {
            errorMessage = errorMessage.concat("Type must not be empty\n");
            valid = false;
        }

        String description = descriptionField.getText().trim();
        if (description.isEmpty()) {
            errorMessage = errorMessage.concat("Description must not be empty\n");
            valid = false;
        }

        // check for null
        //user, customer, contact
        if (userCBox.getValue() == null) {
            errorMessage += "Select a User\n";
            valid = false;
        }


        if (customerCBox.getValue() == null) {
            errorMessage += "Select a Customer\n";
            valid = false;
        }


        if (contactCBox.getValue() == null) {
            errorMessage += "Select a Contact\n";
            valid = false;
        }


        // Time must be checked against other appointment times for customer
        // Time must be between 8am-5pm Eastern Time
        ZoneId localZone = ZoneId.systemDefault();
        ZonedDateTime localStart = ZonedDateTime.of(startDate.getValue(), startTime.getValue(), localZone);
        ZonedDateTime localEnd = localStart.plus(duration.getValue().getDuration());

        ZonedDateTime businessStart = localStart.withZoneSameInstant(BUSINESSTIMEZONE);
        ZonedDateTime businessEnd = localEnd.withZoneSameInstant(BUSINESSTIMEZONE);

        if (businessStart.getHour() < OPENHOUR || businessStart.getHour() >= CLOSEHOUR
                || businessEnd.getHour() < OPENHOUR || businessEnd.getHour() > CLOSEHOUR) {
            errorMessage += "All appointments must be between 8am and 5pm EST";
            valid = false;
        } else if (businessEnd.getHour() == OPENHOUR && businessEnd.getMinute() > 0) {
            errorMessage += "All appointments must be between 8am and 5pm EST";
            valid = false;
        }

        System.out.println(errorMessage);
        return valid;
    }

    public void closeWindow(ActionEvent actionEvent) {
        Stage stage = (Stage)((Node) actionEvent.getSource()).getScene().getWindow();
        stage.close();
    }
}