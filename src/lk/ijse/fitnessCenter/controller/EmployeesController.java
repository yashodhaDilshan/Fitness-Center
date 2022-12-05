package lk.ijse.fitnessCenter.controller;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import lk.ijse.fitnessCenter.db.DBConnection;
import lk.ijse.fitnessCenter.model.EmployeeModel;
import lk.ijse.fitnessCenter.model.MemberModel;
import lk.ijse.fitnessCenter.to.Employee;
import lk.ijse.fitnessCenter.to.Member;
import lk.ijse.fitnessCenter.to.Suppliers;
import lk.ijse.fitnessCenter.util.SetDate;
import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

public class EmployeesController implements Initializable {


    Parent parent;
    Scene scene;
    Stage stage;
    public Text lblDate;
    public Text lblTime;
    public TextField txtTrainerId, txtTrainerName, txtAge, txtGender, txtTrainerNic, txtTrainerPhoneNo, txtTrainerAddress;

    public TableView<Employee> tblTrainer;
    public TableColumn<Object, Object> colId;
    public TableColumn<Object, Object> colName;
    public TableColumn<Object, Object> colAge;
    public TableColumn<Object, Object> colNic;
    public TableColumn<Object, Object> colGender;
    public TableColumn<Object, Object> colPhoneNo;
    public TableColumn<Object, Object> colAddress;




    @Override
    public void initialize(URL location, ResourceBundle resources) {
        SetDate.setDateTime(lblTime,lblDate);

        colId.setCellValueFactory(new PropertyValueFactory<>("TrainerId"));
        colName.setCellValueFactory(new PropertyValueFactory<>("TrainerName"));
        colAge.setCellValueFactory(new PropertyValueFactory<>("age"));
        colNic.setCellValueFactory(new PropertyValueFactory<>("Nic"));
        colGender.setCellValueFactory(new PropertyValueFactory<>("gender"));
        colPhoneNo.setCellValueFactory(new PropertyValueFactory<>("PhoneNo"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));

        loadTable();
    }

    public void loadTable(){
        Connection connection = null;
        try {
            connection = DBConnection.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM employee order by TrainerId desc ");
            ResultSet resultSet = preparedStatement.executeQuery();
            ObservableList<Employee> list = tblTrainer.getItems();
            list.clear();

            while (resultSet.next()){
                Employee employee = new Employee(
                        resultSet.getInt(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getInt(4),
                        resultSet.getString(5),
                        resultSet.getInt(6),
                        resultSet.getString(7)
                );
                list.add(employee);
            }
            tblTrainer.refresh();

        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }

    //Exit Button
    public void btnExitOnAction(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Please Confirm");
        alert.setHeaderText("Are You Sure Do you wont to Exit");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK)
        {
            Platform.exit();
        }
    }

    //Minimize Button
    public void btnMinimizeOnAction(ActionEvent actionEvent) {
        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        stage.setIconified(true);
    }

    //Back Button
    public void btnBackOnAction(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/MainMenu.fxml"));
        parent = loader.load();
        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        scene = new Scene(parent);
        stage.setScene(scene);
        stage.show();
    }

    public void btntestonaction(ActionEvent mouseEvent) {
        String id = txtTrainerId.getText();
        try {
            Employee employee = EmployeeModel.searchNumber(id);
            if( employee!= null) {
                fillData(employee);
            } else {
                new Alert(Alert.AlertType.WARNING, "Member Not Found!").show();
            }

        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    private void fillData(Employee employee) {
        txtTrainerId.setText(String.valueOf(employee.getTrainerId()));
        txtTrainerName.setText(employee.getTrainerName());
        txtTrainerNic.setText(employee.getNic());
        txtAge.setText(String.valueOf(employee.getAge()));
        txtGender.setText(employee.getGender());
        txtTrainerPhoneNo.setText(String.valueOf(employee.getPhoneNo()));
        txtTrainerAddress.setText(employee.getAddress());
    }




    // Add Trainer Button
    public void btnAddTrainerOnAction(ActionEvent actionEvent) throws IOException {

        if (txtTrainerId.getText().equals("") || txtTrainerName.getText().equals("") || txtAge.getText().equals("") || txtTrainerNic.getText().equals("") || txtTrainerPhoneNo.getText().equals("") || txtTrainerAddress.getText().equals("") || txtGender.getText().equals("")){
            new Alert(Alert.AlertType.WARNING,"Please Fill All Fields").show();
        }
        else{
            int id = Integer.parseInt(txtTrainerId.getText());
            String name = txtTrainerName.getText();
            String Nic = txtTrainerNic.getText();
            int phoneno = Integer.parseInt(txtTrainerPhoneNo.getText());
            int age = Integer.parseInt(txtAge.getText());
            String gender = txtGender.getText();
            String Address = txtTrainerAddress.getText();

            Employee employee = new Employee(id, name, Nic,  age, gender, phoneno, Address);
            try {
                boolean isAdded = EmployeeModel.save(employee);
                if (isAdded) {
                    new Alert(Alert.AlertType.CONFIRMATION, "Trainer Added!").show();
                }
            } catch (SQLException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
            clearBoxes();
            loadTable();
        }
    }


    public void btnUpdateOnAction(ActionEvent actionEvent) {

        if (txtTrainerId.getText().equals("") || txtTrainerName.getText().equals("") || txtAge.getText().equals("") || txtTrainerNic.getText().equals("") || txtTrainerPhoneNo.getText().equals("") || txtTrainerAddress.getText().equals("") || txtGender.getText().equals("")){
            new Alert(Alert.AlertType.WARNING,"Please Fill All Fields").show();
        }
        else {
            int id = Integer.parseInt(txtTrainerId.getText());
            String name = txtTrainerName.getText();
            String Nic = txtTrainerNic.getText();
            int phoneno = Integer.parseInt(txtTrainerPhoneNo.getText());
            int age = Integer.parseInt(txtAge.getText());
            String gender = txtGender.getText();
            String Address = txtTrainerAddress.getText();

            Employee employee = new Employee(id, name, Nic,  age, gender, phoneno, Address);

            boolean isUpdate = false;
            try {
                isUpdate = EmployeeModel.updateEmployee(employee);

                if (isUpdate) {
                    new Alert(Alert.AlertType.CONFIRMATION,"Information Updated").show();
                    System.out.println("done");
                    clearBoxes();
                    loadTable();
                } else {
                    new Alert(Alert.AlertType.ERROR,"Update error...").show();
                    System.out.println("fail");

                }
            } catch (SQLException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }

    // Delete Trainer Button
    public void btnDeleteTrainerOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        if (txtTrainerId.getText().equals("")){
            new Alert(Alert.AlertType.WARNING,"Please Insert Trainer Id").show();
        }
        else{
            //database Delete Trainer
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Please Confirm");
            alert.setHeaderText("Are You Sure Do you want to Delete");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                boolean isDeleted = EmployeeModel.deleteTrainer(txtTrainerId.getText());
                if (isDeleted) {
                    new Alert(Alert.AlertType.CONFIRMATION, "Successfully deleted").show();
                } else {
                    new Alert(Alert.AlertType.ERROR, "error").show();
                }
            }
            clearBoxes();
            loadTable();
        }
    }



    public void clearBoxes(){
        txtTrainerId.setText("");
        txtTrainerName.setText("");
        txtTrainerNic.setText("");
        txtAge.setText("");
        txtGender.setText("");
        txtTrainerPhoneNo.setText("");
        txtTrainerAddress.setText("");
        txtGender.setText("");

    }



}
