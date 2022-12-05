package lk.ijse.fitnessCenter.controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.IOException;
import java.util.Optional;

public class LoginFormController {

    public TextField txtUserName;
    public PasswordField txtPassword;
    Parent parent;
    Scene scene;
    Stage stage;

    public void btnExitOnAction(ActionEvent actionEvent) {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Please Confirm");
        alert.setHeaderText(" Do you want to Exit");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK)
        {
            Platform.exit();
        }
    }

    public void btnMinimizeOnAction(ActionEvent actionEvent) {
        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        stage.setIconified(true);
    }


    public void btnLoginOnAction(ActionEvent actionEvent) throws IOException {

        if(txtUserName.getText().equals("") && txtPassword.getText().equals("")){
            JOptionPane.showMessageDialog(null, "Please Enter Details");
        }
        else if(txtUserName.getText().equals("Yash") && txtPassword.getText().equals("1234")){

            FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/MainMenu.fxml"));
            parent = loader.load();
            Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
            scene = new Scene(parent);
            stage.setScene(scene);
            stage.centerOnScreen();
            stage.show();
        }
        else {
            JOptionPane.showMessageDialog(null, "Username Or password is wrong");
        }
    }

    public void passwordOnAction(ActionEvent actionEvent) throws IOException {
        btnLoginOnAction(actionEvent);
    }
}
