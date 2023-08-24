package controller;

import Database.AppointmentDao;
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

import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

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
    public TableColumn customerCol;
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
        customerCol.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        userCol.setCellValueFactory(new PropertyValueFactory<>("userID"));
        descriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));

        refreshAppointments();
        displayAll.fire();
    }

    public static void navigateTo(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(CustomerRecords.class.getResource("/view/Schedule.fxml"));
        Parent root = loader.load();

        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 835, 500);
        stage.setScene(scene);
        stage.show();
    }


    public void newAppointment(ActionEvent actionEvent) throws IOException {
        NewAppointment controller = new NewAppointment(appointments);
        Stage newWindow = appointmentWindow(actionEvent, controller);

        newWindow.setTitle("New Appointment");
        newWindow.show();

    }

    public void editAppointment(ActionEvent actionEvent) throws IOException {
        Appointment selected = appointmentTable.getSelectionModel().getSelectedItem();
        if(selected == null) return;

        EditAppointment controller = new EditAppointment(appointments, selected);
        Stage newWindow = appointmentWindow(actionEvent, controller);

        newWindow.setTitle("Edit Appointment");
        newWindow.show();
    }

    private Stage appointmentWindow(ActionEvent actionEvent, AppointmentCtrl controller) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Appointment.fxml"));
        loader.setController(controller);
        Parent root = loader.load();

        Stage newWindow = new Stage();
        newWindow.setOnHiding(windowEvent -> {
            refreshAppointments();
        });

        newWindow.initOwner(((Node)actionEvent.getSource()).getScene().getWindow());
        newWindow.setScene(new Scene(root));

        return newWindow;
    }

    public void deleteAppointment(ActionEvent actionEvent) {
        Appointment selected = appointmentTable.getSelectionModel().getSelectedItem();
        if(selected == null) return;

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "This will delete Appointment with ID " + selected.getID() + ". \n Do you want to continue?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            AppointmentDao.deleteAppointment(selected);
            refreshAppointments();
        }
    }

    public void refreshAppointments() {
        try {
            appointments = AppointmentDao.getAllAppointments();
            appointmentTable.setItems(appointments);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void quit(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to exit?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            Platform.exit();
        }
    }

    public void toCustomerRecords(ActionEvent actionEvent) throws IOException {
        CustomerRecords.navigateTo(actionEvent);
    }

    public void displayAll(ActionEvent actionEvent) {
        appointmentTable.setItems(appointments);
    }

    public void displayByMonth(ActionEvent actionEvent) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(Timestamp.valueOf(LocalDateTime.now()));
        cal.add(Calendar.MONTH, 1);

        ObservableList<Appointment> monthAppointments = appointments.stream().filter(appointment -> {
            int result = appointment.getStart().compareTo(new Timestamp(cal.getTime().getTime()));
            return result <= 0;
        }).collect(Collectors.toCollection(FXCollections::observableArrayList));
        appointmentTable.setItems(monthAppointments);
    }

    public void displayByWeek(ActionEvent actionEvent) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(Timestamp.valueOf(LocalDateTime.now()));
        cal.add(Calendar.DAY_OF_WEEK, 7);

        ObservableList<Appointment> weekAppointments = appointments.stream().filter(appointment -> {
            int result = appointment.getStart().compareTo(new Timestamp(cal.getTime().getTime()));
            return result <= 0;
        }).collect(Collectors.toCollection(FXCollections::observableArrayList));
        appointmentTable.setItems(weekAppointments);
    }

    public void toReports(ActionEvent actionEvent) throws IOException {
        Reports.navigateTo(actionEvent);
    }
}
