package controller;

import Database.AppointmentDao;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import model.*;

import java.net.URL;
import java.sql.Timestamp;
import java.time.*;
import java.util.ResourceBundle;

public class EditAppointment extends AppointmentCtrl {


    private Appointment selectedAppointment;


    EditAppointment(ObservableList<Appointment> appointments, Appointment selectedAppointment) {
        super(appointments);
        this.selectedAppointment = selectedAppointment;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        super.initialize(url, resourceBundle);
        setAppointment(selectedAppointment);
    }

    public void setAppointment(Appointment appointment) {
            selectedAppointment = appointment;

            idField.setText(String.valueOf(appointment.getID()));
            titleField.setText(appointment.getTitle());
            locationField.setText(appointment.getLocation());
            typeField.setText(appointment.getType());
            descriptionField.setText(appointment.getDescription());

            LocalDateTime startLocal = appointment.getStart().toLocalDateTime();
            startDate.setValue(startLocal.toLocalDate());
            startTime.setValue(startLocal.toLocalTime());

            Duration between = Duration.between(appointment.getStart().toLocalDateTime(), appointment.getEnd().toLocalDateTime());
            duration.setValue(new AppointmentDuration(between));

            for (Contact contact : contactCBox.getItems()) {
                if (contact.id() == appointment.getContactID()) {
                    contactCBox.setValue(contact);
                    break;
                }
            }

            for (User user : userCBox.getItems()) {
                if (user.id() == appointment.getUserID()) {
                    userCBox.setValue(user);
                    break;
                }
            }

            for (Customer customer : customerCBox.getItems()) {
                if (customer.getId() == appointment.getCustomerID()) {
                    customerCBox.setValue(customer);
                    break;
                }
            }

            setBusinessHours();
        }

    @Override
    public void submitAppointment() {
        if(!validateAppointment()) return;

        selectedAppointment.setTitle(titleField.getText().trim().replace("'", ""));
        selectedAppointment.setLocation(locationField.getText().trim().replace("'", ""));
        selectedAppointment.setType(typeField.getText().trim().replace("'", ""));
        selectedAppointment.setDescription(descriptionField.getText().trim().replace("'", ""));

        selectedAppointment.setUserID(userCBox.getValue().id());
        selectedAppointment.setCustomerID(customerCBox.getValue().getId());
        selectedAppointment.setContact(contactCBox.getValue().id());

        ZoneId UTC = ZoneId.of("UTC");
        ZoneId localZone = ZoneId.systemDefault();
        ZonedDateTime localStart = ZonedDateTime.of(startDate.getValue(), startTime.getValue(), localZone);
        ZonedDateTime utcStart = localStart.withZoneSameInstant(UTC);
        ZonedDateTime utcEnd = utcStart.plusMinutes(duration.getValue().getDuration().toMinutes());

        // Without the LocalDateTime conversion, timestamp offsets the UTC time to the System time
        Timestamp startStamp = Timestamp.valueOf(utcStart.toLocalDateTime());
        Timestamp endStamp = Timestamp.valueOf(utcEnd.toLocalDateTime());

        selectedAppointment.setStart(startStamp);
        selectedAppointment.setEnd(endStamp);

        try {
            AppointmentDao.updateAppointment(selectedAppointment);
            cancel.fire();
        }
        catch(Exception e) {
            System.out.println(e);
        }
    }

}


