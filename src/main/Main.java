package main;

import Database.DBConnection;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


import java.util.Locale;
import java.util.ResourceBundle;


public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void stop() {
        DBConnection.closeConnection();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        //Locale.setDefault(new Locale("fr")); //Uncomment to Test French Locale
        ResourceBundle resources = ResourceBundle.getBundle("Login", Locale.getDefault());
        Parent root = FXMLLoader.load(getClass().getResource("/view/Login.fxml"), resources);
        primaryStage.setTitle(resources.getString("SchedulingManager"));
        primaryStage.setScene(new Scene(root, 800, 600));
        primaryStage.show();
    }




}