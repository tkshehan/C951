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
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.Appointment;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class Schedule implements Initializable {
    public ToggleGroup timeFrame;
    public AnchorPane monthPane;
    public ToggleGroup month;
    public TableView monthTable;
    public Label currentWeek;
    public TableView weekTable;
    public AnchorPane weekPane;
    private ObservableList<Appointment> appointments;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
             appointments = AppointmentDao.getAllAppointments();
        } catch (Exception e) {
            System.out.println(e);
        }
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
        Scene scene = new Scene(root, 750, 500);
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
