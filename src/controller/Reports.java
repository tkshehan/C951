package controller;

import Database.AppointmentDao;
import Database.ContactDao;
import Database.ReportsDao;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Appointment;
import model.Contact;
import model.reports.AppointmentsByMonthAndType;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class Reports implements Initializable {
    public TableView monthlyTable;
    public TableColumn monthMCol;
    public TableColumn typeMCol;
    public TableColumn countMCol;

    public TableView ContactTable;
    public TableColumn idCCol;
    public TableColumn titleCCol;
    public TableColumn typeCCol;
    public TableColumn descriptionCCol;
    public TableColumn startCCol;
    public TableColumn endCCol;
    public TableColumn customerIDCCol;
    public ComboBox<Contact> contactCBox;

    private ObservableList<AppointmentsByMonthAndType> monthlyRecords;
    private ObservableList<Appointment> appointments;
    private ObservableList<Contact> contacts;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setContacts();
        initMonthlyReport();
        initContactReport();
    }

    private void setContacts() {
        try {
            ObservableList<Contact> contacts = ContactDao.getAllContacts();
            contactCBox.setItems(contacts);
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    private void initContactReport() {
        try {
            appointments = AppointmentDao.getAllAppointments();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        idCCol.setCellValueFactory(new PropertyValueFactory<>("ID"));
        titleCCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        typeCCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        descriptionCCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        titleCCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        startCCol.setCellValueFactory(new PropertyValueFactory<>("start"));
        endCCol.setCellValueFactory(new PropertyValueFactory<>("end"));
        customerIDCCol.setCellValueFactory(new PropertyValueFactory<>("customerID"));
    }

    private void initMonthlyReport() {
        monthlyRecords = ReportsDao.getMonthAndTypeReport();

        monthlyTable.setItems(monthlyRecords);
        monthMCol.setCellValueFactory(new PropertyValueFactory<>("month"));
        typeMCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        countMCol.setCellValueFactory(new PropertyValueFactory<>("count"));

    }

    public static void navigateTo(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(CustomerRecords.class.getResource("/view/Reports.fxml"));
        Parent root = loader.load();

        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 835, 500);
        stage.setScene(scene);
        stage.show();
    }

    public void toCustomerRecords(ActionEvent actionEvent) throws IOException {
        CustomerRecords.navigateTo(actionEvent);
    }

    public void toSchedule(ActionEvent actionEvent) throws IOException {
        Schedule.navigateTo(actionEvent);
    }

    public void quit(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to exit?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            Platform.exit();
        }
    }

    public void onContactSelect(ActionEvent actionEvent) {
        int contactID = contactCBox.getValue().getId();
        ObservableList<Appointment> contactAppointments = FXCollections.observableArrayList();

        contactAppointments = appointments.stream()
                .filter(appointment ->appointment.getContactID() == contactID)
                .collect(Collectors.toCollection(FXCollections::observableArrayList));

        ContactTable.setItems(contactAppointments);
    }

    public void toContactReport(ActionEvent actionEvent) {

    }
}
