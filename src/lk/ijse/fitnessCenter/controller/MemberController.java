package lk.ijse.fitnessCenter.controller;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import lk.ijse.fitnessCenter.model.MemberModel;
import lk.ijse.fitnessCenter.to.Member;
import lk.ijse.fitnessCenter.util.SetDate;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.Optional;
import java.util.ResourceBundle;

public class MemberController {
    Parent parent;
    Scene scene;
    Stage stage;
    public Text lblDate;
    public Text lblTime;
    public TextField txtMemberId, txtMemberName, txtAge, txtNic, txtPhoneNo, txtAddress, txtHeight, txtWeight, txtGoal, txtGender;

    public void initialize() {
        SetDate.setDateTime(lblTime, lblDate);
    }

    //Exit Button
    public void btnExitOnAction(ActionEvent actionEvent) {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Please Confirm");
        alert.setHeaderText("Are You Sure Do you wont to Exit");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            Platform.exit();
        }
    }

    //Minimize Button
    public void btnMinimizeOnAction(ActionEvent actionEvent) {
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setIconified(true);
    }

    //Back Button
    public void btnBackOnAction(ActionEvent actionEvent) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/MainMenu.fxml"));
        parent = loader.load();
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        scene = new Scene(parent);
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }

    //Search Button
    public void btnSearchOnAction(MouseEvent mouseEvent) {
            String id = txtMemberId.getText();
            try {
                Member member = MemberModel.searchNumber(id);
                if( member!= null) {
                    fillData(member);
                } else {
                    new Alert(Alert.AlertType.WARNING, "Member Not Found!").show();
                }

            } catch (SQLException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }

    private void fillData(Member member) {
        txtMemberId.setText(String.valueOf(member.getId()));
        txtMemberName.setText(member.getName());
        txtNic.setText(member.getNic());
        txtPhoneNo.setText(String.valueOf(member.getPhoneno()));
        txtAge.setText(member.getAge());
        txtWeight.setText(member.getWeight());
        txtHeight.setText(String.valueOf(member.getHeight()));
        txtGender.setText(member.getGender());
        txtGoal.setText(member.getGoal());
        txtAddress.setText(member.getAddress());
    }

    //Add Member Button
    public void btnAddMemberOnAction(ActionEvent actionEvent) throws IOException {

        if (txtMemberId.getText().equals("") || txtMemberName.getText().equals("") || txtAge.getText().equals("") || txtNic.getText().equals("") || txtPhoneNo.getText().equals("") || txtAddress.getText().equals("") || txtGender.getText().equals("") || txtHeight.getText().equals("") || txtWeight.getText().equals("") || txtGoal.getText().equals("")){
            new Alert(Alert.AlertType.WARNING,"Please Fill All Fields").show();
        }
        else {
            int id = Integer.parseInt(txtMemberId.getText());
            String name = txtMemberName.getText();
            String address = txtAddress.getText();
            int phoneno = Integer.parseInt(txtPhoneNo.getText());
            int age = Integer.parseInt(txtAge.getText());
            double weight = new Double(txtWeight.getText());
            double height = new Double(txtHeight.getText());
            String gender = txtGender.getText();
            String goal = txtGoal.getText();
            String nic = txtNic.getText();


            Member member = new Member(id, name, nic, phoneno, age, weight, height, gender, goal, address);
            try {
                boolean isAdded = MemberModel.save(member);
                if (isAdded) {
                    new Alert(Alert.AlertType.CONFIRMATION, "Member Added!").show();
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/MembershipPayemts.fxml"));
                    parent = loader.load();
                    Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
                    scene = new Scene(parent);
                    stage.setScene(scene);
                    stage.centerOnScreen();
                    stage.show();
                }
            } catch (SQLException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
            clearBoxes();
        }
    }

    //Update Member Button
    public void btnUpdateMemberOnAction(ActionEvent actionEvent) {

        if (txtMemberId.getText().equals("") || txtMemberName.getText().equals("") || txtAge.getText().equals("") || txtNic.getText().equals("") || txtPhoneNo.getText().equals("") || txtAddress.getText().equals("") || txtGender.getText().equals("") || txtHeight.getText().equals("") || txtWeight.getText().equals("") || txtGoal.getText().equals("")){
            new Alert(Alert.AlertType.WARNING,"Please Fill All Fields").show();
        }
        else {
            int id = Integer.parseInt(txtMemberId.getText());
            String name = txtMemberName.getText();
            String address = txtAddress.getText();
            int phoneno = Integer.parseInt(txtPhoneNo.getText());
            int age = Integer.parseInt(txtAge.getText());
            double weight = new Double(txtWeight.getText());
            double height = new Double(txtHeight.getText());
            String gender = txtGender.getText();
            String goal = txtGoal.getText();
            String nic = txtNic.getText();


            Member member = new Member(id, name, nic, phoneno, age, weight, height, gender, goal, address);
            try {
                boolean isUpdate = MemberModel.updateMember(member);
                if (isUpdate) {
                    new Alert(Alert.AlertType.CONFIRMATION,"Information Updated").show();
                    clearBoxes();
                } else {
                    new Alert(Alert.AlertType.ERROR,"Update error...").show();

                }
            } catch (ClassNotFoundException | SQLException ex) {
                throw new RuntimeException(ex);
            }
        }


    }

    //Delete Member Button
    public void btnDeleteMemberOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {

        if (txtMemberId.getText().equals("") ){
            new Alert(Alert.AlertType.WARNING,"Please Insert Member Id").show();
        }
        else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Please Confirm");
            alert.setHeaderText("Are You Sure Do you want to Delete");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                boolean isDeleted = MemberModel.deleteMember(txtMemberId.getText());
                if (isDeleted) {
                    new Alert(Alert.AlertType.CONFIRMATION, "Successfully deleted").show();
                } else {
                    new Alert(Alert.AlertType.ERROR, "error").show();
                }
            }
            clearBoxes();
        }
    }

    //All Member Button
    public void btnAllMembersOnAction(ActionEvent actionEvent) throws IOException, ClassNotFoundException, SQLException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/lk/ijse/fitnessCenter/view/AllMembers.fxml"));
        parent = loader.load();
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        scene = new Scene(parent);
        stage.setScene(scene);
        stage.show();
    }


    public void searchMemberOnAction1(ActionEvent event) {
        String id = txtMemberId.getText();
        try {
            Member member = MemberModel.searchNumber(id);
            if( member!= null) {
                fillData(member);
            } else {
                new Alert(Alert.AlertType.WARNING, "Member Not Found!").show();
            }

        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void clearBoxes(){
        txtMemberId.setText("");
        txtMemberName.setText("");
        txtNic.setText("");
        txtPhoneNo.setText("");
        txtAge.setText("");
        txtWeight.setText("");
        txtHeight.setText("");
        txtGender.setText("");
        txtGoal.setText("");
        txtAddress.setText("");
    }









}
