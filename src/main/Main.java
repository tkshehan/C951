package main;

import Database.DBConnection;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


import java.util.Locale;
import java.util.ResourceBundle;


/** This class Launches the Application. <br />
 */
public class Main extends Application {

    /** This is thew first method called when the application starts.
     * @param args
     */
    public static void main(String[] args) {
        launch(args);
    }

    /** This method runs when the application closes.
     */
    @Override
    public void stop() {
        DBConnection.closeConnection();
    }


    /** This method loads the first View.
     * @param primaryStage
     * @throws Exception
     */
    @Override
    public void start(Stage primaryStage) throws Exception {

        //Locale.setDefault(new Locale("fr", "US")); //Uncomment to Test French Locale
        ResourceBundle resources = ResourceBundle.getBundle("Login", Locale.getDefault());
        Parent root = FXMLLoader.load(getClass().getResource("/view/Login.fxml"), resources);
        primaryStage.setTitle(resources.getString("SchedulingManager"));
        primaryStage.setScene(new Scene(root, 800, 600));
        primaryStage.show();
    }

}