package controller;

import javafx.collections.ObservableList;
import model.Appointment;

import java.net.URL;
import java.util.ResourceBundle;

public class NewAppointment extends AppointmentCtrl {

    NewAppointment(ObservableList<Appointment> appointment) {
        super(appointment);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        super.initialize(url, resourceBundle);
        windowTitle.setText("New Appointment");
        idField.setText("Auto-Generated");
    }

    @Override
    public void submitAppointment() {
        validateAppointment();

    }
}

