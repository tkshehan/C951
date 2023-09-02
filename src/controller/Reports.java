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
    public TableView<AppointmentsByMonthAndType> monthlyTable;
    public TableColumn monthMCol;
    public TableColumn typeMCol;
    public TableColumn countMCol;

    public TableView<Appointment> ContactTable;
    public TableColumn idCCol;
    public TableColumn titleCCol;
    public TableColumn typeCCol;
    public TableColumn descriptionCCol;
    public TableColumn startCCol;
    public TableColumn endCCol;
    public TableColumn customerIDCCol;
    public ComboBox<Contact> contactCBox;

    /** List of all appointments */
    private ObservableList<Appointment> appointments;

    /** This method runs when the view finishes loading
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setContacts();
        initMonthlyReport();
        initContactReport();
    }

    /** Populates the Contact ComboBox */
    private void setContacts() {
        try {
            ObservableList<Contact> contacts = ContactDao.getAllContacts();
            contactCBox.setItems(contacts);
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    /** This method initializes the Contact Report Table
     */
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
        startCCol.setCellValueFactory(new PropertyValueFactory<>("start"));
        endCCol.setCellValueFactory(new PropertyValueFactory<>("end"));
        customerIDCCol.setCellValueFactory(new PropertyValueFactory<>("customerID"));
    }

    /** This method initializes the MonthlyReport Table.
     */
    private void initMonthlyReport() {
        ObservableList<AppointmentsByMonthAndType> monthlyRecords = ReportsDao.getMonthAndTypeReport();

        monthlyTable.setItems(monthlyRecords);
        monthMCol.setCellValueFactory(new PropertyValueFactory<>("month"));
        typeMCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        countMCol.setCellValueFactory(new PropertyValueFactory<>("count"));

    }

    /** This method navigates to this View.
     * @param actionEvent An action from the user.
     * @throws IOException
     */
    public static void navigateTo(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(CustomerRecords.class.getResource("/view/Reports.fxml"));
        Parent root = loader.load();

        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 835, 500);
        stage.setScene(scene);
        stage.show();
    }


    /** This method navigates to Customer Records.
     * @param actionEvent An action from the user.
     * @throws IOException
     */
    public void toCustomerRecords(ActionEvent actionEvent) throws IOException {
        CustomerRecords.navigateTo(actionEvent);
    }


    /** This method navigates to Schedule
     * @param actionEvent An action from the user.
     * @throws IOException
     */
    public void toSchedule(ActionEvent actionEvent) throws IOException {
        Schedule.navigateTo(actionEvent);
    }

    /** This method quits the program.
     * @param actionEvent An action from the user.
     */
    public void quit(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to exit?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            Platform.exit();
        }
    }

    /** Populates the ContactTable for the selected Contact. <BR>
     * <BR>
     * LAMBDA: Filters out appointments that don't have the same contact id as the selected contact.
     * @param actionEvent An action from the user.
     */
    public void onContactSelect(ActionEvent actionEvent) {
        int contactID = contactCBox.getValue().getId();
        ObservableList<Appointment> contactAppointments;

        contactAppointments = appointments.stream()
                .filter(appointment ->appointment.getContactID() == contactID)
                .collect(Collectors.toCollection(FXCollections::observableArrayList));

        ContactTable.setItems(contactAppointments);
    }

    /** Loads the Contact Report window.
     * @param actionEvent An action from the user.
     * @throws IOException
     */
    public void toContactReport(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/ContactReport.fxml"));
        Parent root = loader.load();

        Stage newWindow = new Stage();
        newWindow.initOwner(((Node)actionEvent.getSource()).getScene().getWindow());
        newWindow.setScene(new Scene(root));
        newWindow.show();
    }
}
