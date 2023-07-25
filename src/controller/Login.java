package controller;

import Database.UserDao;
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
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.User;

import java.io.IOException;
import java.net.URL;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;

public class Login implements Initializable {
    ResourceBundle resources;
    public TextField username;
    public PasswordField password;
    public Text errorText;
    public Label locationLabel;

    ObservableList<User> Users = FXCollections.observableArrayList();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            Users.addAll(UserDao.getAllUsers());
        } catch (Exception e) {
            System.out.println(e);
        }
        resources = resourceBundle;
        displayLocation();
    }

    public void displayLocation() {
        String location = Locale.getDefault().getDisplayCountry();
        locationLabel.setText(location);
    }

    public void login(ActionEvent actionEvent) throws IOException {
        errorText.setText(""); // Reset error message
        // Validate
        User loginUser = null;
        boolean valid = true;
        try {
            loginUser = Users.stream().filter(user -> user.getName().equals(username.getText()))
                    .findFirst()
                    .get();
        } catch (Exception e) {
            errorText.setText(resources.getString("ErrorUsername"));
            valid = false;
        }

        if (valid) {
            if (loginUser.getPassword().equals(password.getText())) {
                // Successful Login
                toSchedule(actionEvent);
            } else {
                // Display error, incorrect password
                errorText.setText(resources.getString("ErrorPassword"));
            }
        }
    }


    private void toSchedule(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Schedule.fxml"));
        Parent root = loader.load();

        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 750, 500);
        stage.setScene(scene);
        stage.show();
    }

    public void quit(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to exit?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            Platform.exit();
        }

    }
}
