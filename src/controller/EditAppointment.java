package controller;

import Database.AppointmentDao;
import javafx.collections.ObservableList;
import model.*;

import java.net.URL;
import java.sql.Timestamp;
import java.time.*;
import java.util.ResourceBundle;

/** This method controls the Appointment Window, editing a single selected appointment. */
public class EditAppointment extends AppointmentCtrl {

    /** The appointment to edit. */
    private Appointment selectedAppointment;


    /** Constructor: Passes the selected appointment.
     * @param appointments All appointments.
     * @param selectedAppointment The appointment to edit.
     */
    EditAppointment(ObservableList<Appointment> appointments, Appointment selectedAppointment) {
        super(appointments);
        this.selectedAppointment = selectedAppointment;
    }

    /** This method runs when the View finishes loading.
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        super.initialize(url, resourceBundle);
        setAppointment(selectedAppointment);
    }

    /** Sets the fields to the selected appointment's data.
     * @param appointment The appointment to set.
     */
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
                if (contact.getId() == appointment.getContactID()) {
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

    /** This method updates the appointment if all fields are valid.
     */
    @Override
    public void submitAppointment() {
        if(!validateAppointment()) return;

        selectedAppointment.setTitle(titleField.getText().trim().replace("'", ""));
        selectedAppointment.setLocation(locationField.getText().trim().replace("'", ""));
        selectedAppointment.setType(typeField.getText().trim().replace("'", ""));
        selectedAppointment.setDescription(descriptionField.getText().trim().replace("'", ""));

        selectedAppointment.setUserID(userCBox.getValue().id());
        selectedAppointment.setCustomerID(customerCBox.getValue().getId());
        selectedAppointment.setContact(contactCBox.getValue().getId());

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


