package main;

import Database.DBConnection;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


import java.util.Locale;


public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        DBConnection.openConnection();

        //  Locale.setDefault(new Locale("fr")); //Uncomment to Test French Locale

        Parent root = FXMLLoader.load(getClass().getResource("/view/FirstScreen.fxml"));
        primaryStage.setTitle("Scheduling Manager");
        primaryStage.setScene(new Scene(root, 800, 600));
        primaryStage.show();
    }




}