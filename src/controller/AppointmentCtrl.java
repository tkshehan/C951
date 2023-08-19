package controller;

import Database.ContactDao;
import Database.CustomerDao;
import Database.UserDao;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.text.Text;
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
    public Text errorText;
    public Button cancel;

    private ObservableList<Appointment> appointments;
    private final ZoneId BUSINESSTIMEZONE = ZoneId.of("America/New_York"); // America/New_York
    private final int OPENHOUR = 8; // 8am
    private final int CLOSEHOUR = 17; // 5pm

    AppointmentCtrl(ObservableList<Appointment> appointments) {
        this.appointments = appointments;
    }

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

        String title = titleField.getText().trim();
        if (title.isEmpty()) {
            errorMessage = errorMessage.concat(
                    "Title must not be empty\n"
            );
        }

        String location = locationField.getText().trim();
        if (location.isEmpty()) {
            errorMessage = errorMessage.concat("Location must not be empty\n");
        }

        String type = typeField.getText().trim();
        if (type.isEmpty()) {
            errorMessage = errorMessage.concat("Type must not be empty\n");
        }

        String description = descriptionField.getText().trim();
        if (description.isEmpty()) {
            errorMessage = errorMessage.concat("Description must not be empty\n");
        }

        if (userCBox.getValue() == null || customerCBox.getValue() == null || contactCBox.getValue() == null) {
            errorMessage += "Select a User, Customer, and Contact\n";
        }



        if (startDate.getValue() == null || startTime.getValue() == null || duration.getValue() == null) {
            errorMessage += "Select an start date, time, and duration";
        } else {
            ZoneId localZone = ZoneId.systemDefault();
            ZonedDateTime localStart = ZonedDateTime.of(startDate.getValue(), startTime.getValue(), localZone);
            ZonedDateTime localEnd = localStart.plus(duration.getValue().getDuration());

            // Time must be between 8am-5pm Eastern Time
            ZonedDateTime appZonedStart = localStart.withZoneSameInstant(BUSINESSTIMEZONE);
            ZonedDateTime appZonedEnd = localEnd.withZoneSameInstant(BUSINESSTIMEZONE);
            if (appZonedStart.getHour() < OPENHOUR || appZonedStart.getHour() >= CLOSEHOUR
                || appZonedEnd.getHour() < OPENHOUR || appZonedEnd.getHour() > CLOSEHOUR) {
                errorMessage += "All appointments must be between 8am and 5pm EST\n" ;
            } else if (appZonedEnd.getHour() == CLOSEHOUR && appZonedEnd.getMinute() > 0) {
                errorMessage += "All appointments must be between 8am and 5pm EST\n";
            }

            // Appointment must not overlap with another appointment for same customer
            if (customerCBox.getValue() != null) {
                LocalDateTime appStart = localStart.toLocalDateTime();
                LocalDateTime appEnd = localEnd.toLocalDateTime();
                int customerId = customerCBox.getValue().getId();
                FilteredList<Appointment> customerAppointments = appointments.filtered(
                        appointment -> appointment.getCustomerID() == customerId);

                boolean isAppointmentConflict = customerAppointments.stream().anyMatch(
                        appointment -> {
                            LocalDateTime checkStart = appointment.getStart().toLocalDateTime().minusSeconds(1);
                            LocalDateTime checkEnd = appointment.getEnd().toLocalDateTime().plusSeconds(1);
                            // Ignore appointments with the same ID
                            if (this instanceof EditAppointment && String.valueOf(appointment.getID()).equals(idField.getText())) {
                                return false;
                            }
                            return appStart.isAfter(checkStart) && appStart.isBefore(checkEnd) ||
                                    appEnd.isAfter(checkStart) && appEnd.isBefore(checkEnd);
                        }
                );
                if(isAppointmentConflict) {
                    errorMessage += "Customer already has appointment during this timeframe\n";
                }
            }
        }

        if( errorMessage.length() > 0){
            errorText.setText(errorMessage);
            return false;
        } else {
            return true;
        }
    }

    @FXML
    public void onDateSelected(ActionEvent actionEvent) {
        setBusinessHours();
    }
    @FXML
    public abstract void submitAppointment();
    @FXML
    public void closeWindow(ActionEvent actionEvent) {
        Stage stage = (Stage)((Node) actionEvent.getSource()).getScene().getWindow();
        stage.close();
    }
}