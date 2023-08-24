package controller;

import Database.AppointmentDao;
import Database.CustomerDao;
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
import model.Customer;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class CustomerRecords implements Initializable {
    public ComboBox<String> countryCombo;
    public ComboBox<String> stateComboBox;
    public TableView<Customer> customerTable;
    public TableColumn idCol;
    public TableColumn nameCol;
    public TableColumn addressCol;
    public TableColumn postalCol;
    public TableColumn phoneCol;
    public TableColumn divisionCol;
    public TableColumn countryCol;


    private ObservableList<Customer> customers;
    private ObservableList<Customer> countryCustomers;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        customerTable.setItems(customers);
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        addressCol.setCellValueFactory(new PropertyValueFactory<>("address"));
        postalCol.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
        phoneCol.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        divisionCol.setCellValueFactory(new PropertyValueFactory<>("division"));
        countryCol.setCellValueFactory(new PropertyValueFactory<>("country"));

        refreshCustomers();
        initializeCountries();
    }

    public static void navigateTo(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(CustomerRecords.class.getResource("/view/CustomerRecords.fxml"));
        Parent root = loader.load();

        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 835, 500);
        stage.setScene(scene);
        stage.show();
    }

    private void initializeCountries() {
        countryCombo.getItems().clear();
        countryCombo.getItems().add("All Countries");
        customers.stream().map(Customer::getCountry).distinct().sorted().forEach((country) -> {
            countryCombo.getItems().add(country);
        });
    }

    private void initializeStates(ObservableList<Customer> countryCustomers) {
        stateComboBox.getItems().clear();
        stateComboBox.getItems().add("All Divisions");
        countryCustomers.stream().map(Customer::getDivision).distinct().sorted().forEach((state) -> {
            stateComboBox.getItems().add(state);
        });
        stateComboBox.setDisable(false);
    }

    private void refreshCustomers() {
        try {
            customers = CustomerDao.getAllCustomers();
            customerTable.setItems(customers);
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

    public void newCustomer(ActionEvent actionEvent) throws IOException {
        NewCustomer controller = new NewCustomer();
        Stage newWindow = customerWindow(actionEvent, controller);

        newWindow.setTitle("New Customer");
        newWindow.show();
    }

    public void editCustomer(ActionEvent actionEvent) throws IOException {
        Customer selected = customerTable.getSelectionModel().getSelectedItem();
        if(selected == null) return;

        EditCustomer controller = new EditCustomer(selected);
        Stage newWindow = customerWindow(actionEvent, controller);

        newWindow.setTitle("Edit Customer");
        newWindow.show();
    }

    public void deleteCustomer(ActionEvent actionEvent) {
        Customer selected = customerTable.getSelectionModel().getSelectedItem();
        if(selected == null) return;

        try {
            if(AppointmentDao.checkCustomerAppointments(selected)) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Customer cannot be deleted with existing Appointments");
                alert.showAndWait();
            } else {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "This will delete Customer with ID " + selected.getId() + ". \n Do you want to continue?");
                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK) {
                    CustomerDao.deleteCustomer(selected);

                    refreshCustomers();
                    initializeCountries();
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Stage customerWindow(ActionEvent actionEvent, CustomerCtrl controller) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Customer.fxml"));
        loader.setController(controller);
        Parent root = loader.load();

        Stage newWindow = new Stage();
        newWindow.setOnHiding(windowEvent -> {
            refreshCustomers();
            initializeCountries();
        });

        newWindow.initOwner(((Node)actionEvent.getSource()).getScene().getWindow());
        newWindow.setScene(new Scene(root));

        return newWindow;
    }

    public void toSchedule(ActionEvent actionEvent) throws IOException {
        Schedule.navigateTo(actionEvent);
    }

    public void toReports(ActionEvent actionEvent) throws IOException {
        Reports.navigateTo(actionEvent);
    }


    public void selectCountry(ActionEvent actionEvent) {
        stateComboBox.getItems().clear();

        String country = countryCombo.getValue();
        if(Objects.equals(country, "All Countries")) {
            customerTable.setItems(customers);
            stateComboBox.setDisable(true);
        } else {
            countryCustomers = customers.stream()
                    .filter(customer -> Objects.equals(customer.getCountry(), country))
                    .collect(Collectors.toCollection(FXCollections::observableArrayList));
            customerTable.setItems(countryCustomers);
            initializeStates(countryCustomers);
        }
    }

    public void selectState(ActionEvent actionEvent) {
        String state = stateComboBox.getValue();
        if(Objects.equals(state, "All Divisions")) {
            customerTable.setItems(countryCustomers);
        } else {
            ObservableList<Customer> stateCustomers = countryCustomers.stream()
                    .filter(customer -> Objects.equals(customer.getDivision(), state))
                    .collect(Collectors.toCollection(FXCollections::observableArrayList));
            customerTable.setItems(stateCustomers);
        }
    }


}

