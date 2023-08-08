package controller;

import Database.ContactDao;
import Database.CustomerDao;
import Database.UserDao;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Appointment;
import model.Contact;
import model.Customer;
import model.User;

import java.net.URL;
import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

public class EditAppointment implements Initializable {

    public Label windowTitle;
    public TextField idField;
    public TextField titleField;
    public TextField locationField;
    public TextField typeField;
    public ComboBox<User> userCBox;
    public ComboBox<Customer> customerCBox;
    public ComboBox<Contact> contactCBox;
    public DatePicker startDate;
    public ComboBox<Time> startTime;
    public ComboBox<String> duration;
    public TextField descriptionField;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setContacts();
        setCustomers();
        setUsers();
    }

    public void setAppointment(Appointment appointment) {
        idField.setText(String.valueOf(appointment.getID()));
        titleField.setText(appointment.getTitle());
        locationField.setText(appointment.getLocation());
        typeField.setText(appointment.getType());
        descriptionField.setText(appointment.getDescription());

        LocalDateTime startLocal = appointment.getStart().toLocalDateTime();
        startDate.setValue(startLocal.toLocalDate());
        startTime.setValue(Time.valueOf(startLocal.toLocalTime()));

        long milliseconds = appointment.getEnd().getTime() - appointment.getStart().getTime();
        duration.setValue(parseTime(milliseconds));

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

    private String parseTime(long milliseconds) {
        String duration = "";
        long seconds = milliseconds / 1000;
        int minutes = 0;
        int hours = 0;

        while(seconds >= 3600) {
            hours++;
            seconds -= 3600;
        }
        while(seconds >= 60) {
            minutes++;
            seconds -= 60;
        }
        duration += hours + ":";

        if(minutes < 10) {
            duration += 0;
        }
        duration += minutes;

        return duration;
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

}
