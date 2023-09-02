package controller;

import Database.AppointmentDao;
import javafx.collections.ObservableList;
import model.Appointment;

import java.net.URL;
import java.sql.Timestamp;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ResourceBundle;

/** This class controls the Appointment View, adding a new customer.
 */
public class NewAppointment extends AppointmentCtrl {

    NewAppointment(ObservableList<Appointment> appointment) {
        super(appointment);
    }

    /** This method runs when the view finishes loading.
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        super.initialize(url, resourceBundle);
        windowTitle.setText("New Appointment");
        idField.setText("Auto-Generated");
    }

    /** This method creates a new appointment if all fields are valid. */
    @Override
    public void submitAppointment() {
        if(!validateAppointment()) return;

        ZoneId UTC = ZoneId.of("UTC");
        ZoneId localZone = ZoneId.systemDefault();
        ZonedDateTime localStart = ZonedDateTime.of(startDate.getValue(), startTime.getValue(), localZone);
        ZonedDateTime utcStart = localStart.withZoneSameInstant(UTC);
        ZonedDateTime utcEnd = utcStart.plusMinutes(duration.getValue().getDuration().toMinutes());

        // Without the LocalDateTime conversion, timestamp offsets the UTC time to the System time
        Timestamp startStamp = Timestamp.valueOf(utcStart.toLocalDateTime());
        Timestamp endStamp = Timestamp.valueOf(utcEnd.toLocalDateTime());

        Appointment newAppointment = new Appointment(-1,
                titleField.getText().trim().replace("'", ""),
                locationField.getText().trim().replace("'", ""),
                contactCBox.getValue().getId(),
                typeField.getText().trim().replace("'", ""),
                startStamp,
                endStamp,
                customerCBox.getValue().getId(),
                userCBox.getValue().id(),
                descriptionField.getText().trim().replace("'", ""));
        try {
            AppointmentDao.createAppointment(newAppointment);
            cancel.fire();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}

