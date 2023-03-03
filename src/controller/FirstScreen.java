package controller;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class FirstScreen implements Initializable {

    public Label label;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("Initialized");
    }

    public void changeLabelText(ActionEvent actionEvent) {
        label.setText("Changed!");
    }
}
