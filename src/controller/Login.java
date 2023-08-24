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
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.*;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class Login implements Initializable {
    ResourceBundle resources;
    public TextField username;
    public PasswordField password;
    public Text errorText;
    public Label locationLabel;
    public static User activeUser;

    ObservableList<User> Users = FXCollections.observableArrayList();
    Logger log;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            Users.addAll(UserDao.getAllUsers());
        } catch (Exception e) {
            System.out.println(e);
        }
        setupLogger();
        resources = resourceBundle;
        displayLocation();
    }

    public void displayLocation() {
        String location = Locale.getDefault().getDisplayCountry();
        locationLabel.setText(location);
    }

    public void setupLogger() {
        log = Logger.getLogger("login_activity.txt");
        log.setLevel(Level.CONFIG);
        try {
            FileHandler fh = new FileHandler("login_activity.txt", true);
            System.setProperty("java.util.logging.SimpleFormatter.format",
                    "%4$s: %5$s%n");
            SimpleFormatter sf = new SimpleFormatter();
            fh.setFormatter(sf);
            log.addHandler(fh);
        } catch (IOException ex) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SecurityException ex) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void login(ActionEvent actionEvent) throws IOException {
        errorText.setText(""); // Reset error message
        // Validate
        User loginUser = null;
        boolean isValid = true;
        try {
            loginUser = Users.stream().filter(user -> user.name().equals(username.getText()))
                    .findFirst()
                    .get();
        } catch (Exception e) {
            errorText.setText(resources.getString("ErrorUsername"));
            isValid = false;
        }

        if (isValid) {
            if (!loginUser.password().equals(password.getText())) {
                // Display error, incorrect password
                errorText.setText(resources.getString("ErrorPassword"));
                isValid = false;
            }
        }

        logLoginAttempt(isValid);
        if(isValid) {
            // Login Successful
            activeUser = loginUser;
            toSchedule(actionEvent);
        }
    }

    public void logLoginAttempt(boolean isValid) {
        Date currentUtcTime = Date.from(Instant.now());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss z");
        sdf.setTimeZone(TimeZone.getTimeZone("Etc/UTC"));

        String result = "FAILED";
        if (isValid) result = "SUCCESS";
        String attempt = "Login attempt: " + result + " | " + sdf.format(currentUtcTime) ;

        log.info(attempt);
    }

    private void toSchedule(ActionEvent actionEvent) throws IOException {
        Schedule.navigateTo(actionEvent);
    }

    public void quit(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to exit?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            Platform.exit();
        }

    }
}
