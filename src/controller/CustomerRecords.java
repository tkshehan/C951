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
import model.Customer;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

/**
 * This class controls the CustomerRecords View.
 */
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


    /** The list of every customer.
     */
    private ObservableList<Customer> customers;
    /** The list of customers in a selected country.
     */
    private ObservableList<Customer> countryCustomers;

    /** This method runs when the view finishes loading.
     * @param url
     * @param resourceBundle
     */
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


    /** This method navigates to the CustomerRecords View.
     * @param actionEvent An action from the user.
     * @throws IOException
     */
    public static void navigateTo(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(CustomerRecords.class.getResource("/view/CustomerRecords.fxml"));
        Parent root = loader.load();

        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 835, 500);
        stage.setScene(scene);
        stage.show();
    }

    /** This method populates the Country Combobox
     * LAMBDA: Adds each distinct country to the countryComboBox
     */
    private void initializeCountries() {
        countryCombo.getItems().clear();
        countryCombo.getItems().add("All Countries");
        // LAMBDA
        customers.stream().map(Customer::getCountry).distinct().sorted().forEach((country) -> {
            countryCombo.getItems().add(country);
        });
    }


    /** This method adds each state to the State ComboBox
     * LAMBDA: Adds each distinct state from customers to the stateComboBox
     * @param countryCustomers
     */
    private void initializeStates(ObservableList<Customer> countryCustomers) {
        stateComboBox.getItems().clear();
        stateComboBox.getItems().add("All Divisions");
        // LAMBDA
        countryCustomers.stream().map(Customer::getDivision).distinct().sorted().forEach((state) -> {
            stateComboBox.getItems().add(state);
        });
        stateComboBox.setDisable(false);
    }

    /** This method refreshes the customer table from the database */
    private void refreshCustomers() {
        try {
            customers = CustomerDao.getAllCustomers();
            customerTable.setItems(customers);
        } catch (Exception e) {
            System.out.println(e);
        }
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


    /** This method loads the New Customer window.
     * @param actionEvent An action from the user.
     * @throws IOException
     */
    public void newCustomer(ActionEvent actionEvent) throws IOException {
        NewCustomer controller = new NewCustomer();
        Stage newWindow = customerWindow(actionEvent, controller);

        newWindow.setTitle("New Customer");
        newWindow.show();
    }

    /** This method loads the Edit Customer window.
     * @param actionEvent An action from the user.
     * @throws IOException
     */
    public void editCustomer(ActionEvent actionEvent) throws IOException {
        Customer selected = customerTable.getSelectionModel().getSelectedItem();
        if(selected == null) return;

        EditCustomer controller = new EditCustomer(selected);
        Stage newWindow = customerWindow(actionEvent, controller);

        newWindow.setTitle("Edit Customer");
        newWindow.show();
    }

    /** This method deletes the selected customer after confirming with the user.
     * @param actionEvent An action from the user.
     */
    public void deleteCustomer(ActionEvent actionEvent) {
        Customer selected = customerTable.getSelectionModel().getSelectedItem();
        if(selected == null) return;

        try {
            if(AppointmentDao.checkCustomerAppointments(selected)) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Customer cannot be deleted with existing Appointments");
                alert.showAndWait();
            } else {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "This will delete Customer with ID " +
                        selected.getId() + ". \n Do you want to continue?");
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

    /** This method initializes the customer window with the chosen controller. <BR>
     * <BR>
     * LAMBDA: Sets an inline method to fire when the window is closed, refreshing CustomerRecords with new data.
     * @param actionEvent An action from the user.
     * @param controller The controller for the Customer View.
     * @return The initialized window.
     * @throws IOException
     */
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

    /** Navigates to the Schedule View.
     * @param actionEvent An action from the user.
     * @throws IOException
     */
    public void toSchedule(ActionEvent actionEvent) throws IOException {
        Schedule.navigateTo(actionEvent);
    }

    /** Navigates to the Reports View.
     * @param actionEvent An action from the user.
     * @throws IOException
     */
    public void toReports(ActionEvent actionEvent) throws IOException {
        Reports.navigateTo(actionEvent);
    }

    /** When a country is selected, filters out customers not in selected country.<BR>
     * <BR>
     * LAMBDA: Compares the Customer's country against the selected country.
     * @param actionEvent An action event from the user.
     */
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

    /** When a state is selected, filters out customers not in selected state. <BR>
     * <BR>
     * LAMBDA: Compares the customer's state/division against the selected state/division.
     * @param actionEvent An action from the user.
     */
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

