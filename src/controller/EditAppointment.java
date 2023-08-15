package controller;

import Database.AppointmentDao;
import javafx.scene.control.Button;
import model.*;

import java.net.URL;
import java.sql.Timestamp;
import java.time.*;
import java.util.ResourceBundle;

public class EditAppointment extends AppointmentCtrl {
    public Button cancel;
    private Appointment setAppointment;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        super.initialize(url, resourceBundle);

    }

    public void setAppointment(Appointment appointment) {
            setAppointment = appointment;

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
        }

    @Override
    public void submitAppointment() {
        if(!validateAppointment()) return;

        setAppointment.setTitle(titleField.getText());
        setAppointment.setLocation(locationField.getText());
        setAppointment.setType(typeField.getText());
        setAppointment.setDescription(descriptionField.getText());

        setAppointment.setUserID(userCBox.getValue().id());
        setAppointment.setCustomerID(customerCBox.getValue().getId());
        setAppointment.setContact(contactCBox.getValue().id());

        ZoneId UTC = ZoneId.of("UTC");
        ZoneId localZone = ZoneId.systemDefault();
        ZonedDateTime localStart = ZonedDateTime.of(startDate.getValue(), startTime.getValue(), localZone);
        ZonedDateTime utcStart = localStart.withZoneSameInstant(UTC);
        ZonedDateTime utcEnd = utcStart.plusMinutes(duration.getValue().getDuration().toMinutes());

        // Without the LocalDateTime conversion, timestamp offsets the UTC time to the System time
        Timestamp startStamp = Timestamp.valueOf(utcStart.toLocalDateTime());
        Timestamp endStamp = Timestamp.valueOf(utcEnd.toLocalDateTime());

        setAppointment.setStart(startStamp);
        setAppointment.setEnd(endStamp);

        try {
            AppointmentDao.updateAppointment(setAppointment);
            cancel.fire();
        }
        catch(Exception e) {
            System.out.println(e);
        }
    }

}


