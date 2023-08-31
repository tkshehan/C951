package controller;

import Database.AppointmentDao;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
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

    private static boolean isFirstVisit = true;

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

        if (isFirstVisit) {
            upcomingAppointmentAlert();
            isFirstVisit = false;
        }
    }

    private void upcomingAppointmentAlert() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime now15 = LocalDateTime.now().plusMinutes(15);


        FilteredList<Appointment> upcomingAppointments = appointments.filtered(appointment -> {
                LocalDateTime start = appointment.getStart().toLocalDateTime();
                return start.isBefore(now15) && start.isAfter(now);
                }
        );

        if (!upcomingAppointments.isEmpty()) {
            Appointment upcoming = upcomingAppointments.get(0);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Upcoming Appointment!");
            alert.setHeaderText("Upcoming appointment");
            alert.setContentText("""
                    ID: %s,
                    Date and Time: %s
                    """.formatted(upcoming.getID(), upcoming.getStart()));

            alert.showAndWait();
        }
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
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime nextMonth = now.plusMonths(1);

        ObservableList<Appointment> monthAppointments = appointments.stream().filter(appointment -> {
            LocalDateTime start = appointment.getStart().toLocalDateTime();
            return start.isBefore(nextMonth) && start.isAfter(now);
        }).collect(Collectors.toCollection(FXCollections::observableArrayList));
        appointmentTable.setItems(monthAppointments);
    }

    public void displayByWeek(ActionEvent actionEvent) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime nextWeek = now.plusWeeks(1);

        ObservableList<Appointment> monthAppointments = appointments.stream().filter(appointment -> {
            LocalDateTime start = appointment.getStart().toLocalDateTime();
            return start.isBefore(nextWeek) && start.isAfter(now);
        }).collect(Collectors.toCollection(FXCollections::observableArrayList));
        appointmentTable.setItems(monthAppointments);
    }

    public void toReports(ActionEvent actionEvent) throws IOException {
        Reports.navigateTo(actionEvent);
    }
}
