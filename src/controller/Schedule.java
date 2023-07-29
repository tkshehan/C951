package controller;

import Database.AppointmentDao;
import javafx.application.Platform;
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

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class Schedule implements Initializable {
    public RadioButton displayAll;
    public ToggleGroup timeFrame;
    public RadioButton displayByMonth;
    public RadioButton displayByWeek;
    public TableColumn idCol;
    public TableColumn titleCol;
    public TableColumn locationCol;
    public TableColumn contactCol;
    public TableColumn typeCol;
    public TableColumn startCol;
    public TableColumn endCol;
    public TableColumn userCol;
    public TableColumn custCol;
    public TableColumn descriptionCol;
    public TableView<Appointment> appointmentTable;
    private ObservableList<Appointment> appointments;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        appointmentTable.setItems(appointments);
        idCol.setCellValueFactory(new PropertyValueFactory<>("ID"));
        titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        locationCol.setCellValueFactory(new PropertyValueFactory<>("location"));
        contactCol.setCellValueFactory(new PropertyValueFactory<>("contactID"));
        typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        startCol.setCellValueFactory(new PropertyValueFactory<>("start"));
        endCol.setCellValueFactory(new PropertyValueFactory<>("end"));
        custCol.setCellValueFactory(new PropertyValueFactory<>("custID"));
        userCol.setCellValueFactory(new PropertyValueFactory<>("userID"));
        descriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));

        try {
             appointments = AppointmentDao.getAllAppointments();
             appointmentTable.setItems(appointments);
        } catch (Exception e) {
            System.out.println(e);
        }
        displayAll.fire();
    }


    public void newAppointment(ActionEvent actionEvent) {

    }

    public void editAppointment(ActionEvent actionEvent) {

    }

    public void deleteAppointment(ActionEvent actionEvent) {

    }


    public void quit(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to exit?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            Platform.exit();
        }
    }

    public void toCustomerRecords(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/CustomerRecords.fxml"));
        Parent root = loader.load();

        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 835, 500);
        stage.setScene(scene);
        stage.show();
    }

    public void displayAll(ActionEvent actionEvent) {

    }

    public void displayByMonth(ActionEvent actionEvent) {

    }

    public void displayByWeek(ActionEvent actionEvent) {

    }
}
