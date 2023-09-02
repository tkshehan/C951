package controller;

import Database.ReportsDao;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Contact;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

/**
 * This class controls the ContactReport View.
 */
public class ContactReport implements Initializable {

    public TableView<Contact> reportTable;
    public TableColumn idCol;
    public TableColumn nameCol;
    public TableColumn emailCol;
    public TableColumn appointmentsCol;

    /** This method runs when the view finishes loading.
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            ObservableList<Contact> contacts = ReportsDao.getContactReport();
            idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
            nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
            emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));
            appointmentsCol.setCellValueFactory(new PropertyValueFactory<>("appointmentCount"));
            reportTable.setItems(contacts);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /** This method closes the window.
     * @param actionEvent An action from the user.
     */
    @FXML
    public void closeWindow(ActionEvent actionEvent) {
        Stage stage = (Stage)((Node) actionEvent.getSource()).getScene().getWindow();
        stage.close();
    }
}
