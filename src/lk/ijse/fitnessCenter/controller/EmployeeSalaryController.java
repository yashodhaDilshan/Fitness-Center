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
import lk.ijse.fitnessCenter.model.EmployeeSalaryModel;
import lk.ijse.fitnessCenter.model.MemberModel;
import lk.ijse.fitnessCenter.model.MembershipPaymentModel;
import lk.ijse.fitnessCenter.to.*;
import lk.ijse.fitnessCenter.util.SetDate;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;
import java.util.ResourceBundle;

public class EmployeeSalaryController implements Initializable {

    Parent parent;
    Scene scene;
    Stage stage;
    public Text lblDate;
    public Text lblTime;

    public TableView<EmployeeSalary> tblSalary;

    public TableColumn<Object, Object> colId;
    public TableColumn<Object, Object> colTrainerId;
    public TableColumn<Object, Object> colTrainerName;
    public TableColumn<Object, Object> colSalary;
    public TableColumn<Object, Object> colAllowance;
    public TableColumn<Object, Object> colTotal;
    public TableColumn<Object, Object> colDate;
    public TableColumn<Object, Object> colStatus;


    public TextField txtEmployeeId, txtSalary, txtAllowance, txtSearchBar;
    public Label lblEmployeeName;
    public Button btnPay;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        SetDate.setDateTime(lblTime,lblDate);

        btnPay.setDisable(true);

        colId.setCellValueFactory(new PropertyValueFactory<>("SaleryId"));
        colTrainerId.setCellValueFactory(new PropertyValueFactory<>("TrainerId"));
        colTrainerName.setCellValueFactory(new PropertyValueFactory<>("Name"));
        colSalary.setCellValueFactory(new PropertyValueFactory<>("salary"));
        colAllowance.setCellValueFactory(new PropertyValueFactory<>("Allowance"));
        colTotal.setCellValueFactory(new PropertyValueFactory<>("Total"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("PaymentDate"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("Status"));

        loadTable();

    }

    public void loadTable(){
        Connection connection = null;
        try {
            connection = DBConnection.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM EmployeeSalary order by SalaryId desc ");
            ResultSet resultSet = preparedStatement.executeQuery();
            ObservableList<EmployeeSalary> list = tblSalary.getItems();
            list.clear();

            while (resultSet.next()){
                EmployeeSalary employeeSalary = new EmployeeSalary(
                        resultSet.getInt(1),
                        resultSet.getInt(2),
                        resultSet.getString(3),
                        resultSet.getDouble(4),
                        resultSet.getDouble(5),
                        resultSet.getDouble(6),
                        resultSet.getString(7),
                        resultSet.getString(8)
                );
                list.add(employeeSalary);
            }
            tblSalary.refresh();

        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }

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
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/Payments.fxml"));
        parent = loader.load();
        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        scene = new Scene(parent);
        stage.setScene(scene);
        stage.show();
    }




    public void btnSearchEmployeeOnAction(ActionEvent actionEvent) {
        String id = txtEmployeeId.getText();
        try {
            Employee employee = EmployeeModel.searchNumber(id);
            if( employee!= null) {
                fillData(employee);
            } else {
                new Alert(Alert.AlertType.WARNING, "Trainer Not Found!").show();
                txtEmployeeId.setText("");
                loadTable();
            }

        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void fillData(Employee employee) {
        lblEmployeeName.setText(employee.getTrainerName());
        btnPay.setDisable(false);
    }
    public void btnSearchOnAction(ActionEvent actionEvent) {
        if(txtSearchBar.getText().equals("")){
            loadTable();
        }
        else {
            String search = txtSearchBar.getText();
            Connection connection = null;
            try {
                connection = DBConnection.getInstance().getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM EmployeeSalary WHERE TrainerId = ? OR Name = ? ");
                preparedStatement.setObject(1,search);
                preparedStatement.setObject(2,search);

                ResultSet resultSet = preparedStatement.executeQuery();
                ObservableList<EmployeeSalary> list = tblSalary.getItems();
                list.clear();

                while (resultSet.next()){
                    EmployeeSalary employeeSalary = new EmployeeSalary(
                            resultSet.getInt(1),
                            resultSet.getInt(2),
                            resultSet.getString(3),
                            resultSet.getDouble(4),
                            resultSet.getDouble(5),
                            resultSet.getDouble(6),
                            resultSet.getString(7),
                            resultSet.getString(8)
                    );
                    list.add(employeeSalary);
                }
                tblSalary.refresh();

            } catch (SQLException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }


        }
    }

    public void btnBuyOnAction(ActionEvent actionEvent) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        Date date = new Date();
        String PaymentDate = dateFormat.format(date);

        // Find Last OrderId
        Connection connection = null;
        int Sid;
        try {
            connection = DBConnection.getInstance().getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT SalaryId FROM EmployeeSalary order by SalaryId desc limit 1");
            resultSet.next();

            Sid = resultSet.getInt(1);
            System.out.println(Sid);

        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        int TrainerId = Integer.parseInt(txtEmployeeId.getText());
        String Name = lblEmployeeName.getText();
        double salary = Integer.parseInt(txtSalary.getText());
        double Allowance = Integer.parseInt(txtAllowance.getText());
        double Total = salary + Allowance;
        String Status = "Paid";

        int SaleryId = 0;

        if (Sid == 0){
            SaleryId = 1;
        }
        else {
            SaleryId = ++Sid;
        }

        EmployeeSalary employeeSalary = new EmployeeSalary(SaleryId, TrainerId, Name, salary, Allowance, Total, PaymentDate, Status);

        try {
            boolean isAdded = EmployeeSalaryModel.save(employeeSalary);
            if (isAdded) {
                new Alert(Alert.AlertType.CONFIRMATION, "Payment Success!").show();
                btnPay.setDisable(true);
                clear();
                loadTable();

            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }



    public void btnReloadOnAction(ActionEvent actionEvent) {
        loadTable();
        txtSearchBar.setText("");
    }

    public void clear(){
        txtEmployeeId.setText("");
        txtSalary.setText("");
        txtAllowance.setText("0");
    }
}
