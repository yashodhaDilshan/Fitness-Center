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
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import lk.ijse.fitnessCenter.db.DBConnection;
import lk.ijse.fitnessCenter.model.SuppliersModel;
import lk.ijse.fitnessCenter.to.Suppliers;
import lk.ijse.fitnessCenter.util.SetDate;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Observable;
import java.util.Optional;
import java.util.ResourceBundle;

public class SuppliersController implements Initializable {

    public AnchorPane pane;
    public Button btnAddMember1;
    public Text lblDate;
    public Text lblTime;
    Parent parent;
    Scene scene;
    Stage stage;
    public TextField txtSupplierId, txtSupplierName, txtNic, txtPhoneNo, txtAddress;

    public TableView<Suppliers> tblSuppliers;
    public TableColumn<Object, Object> colId;
    public TableColumn<Object, Object> colName;
    public TableColumn<Object, Object> colNic;
    public TableColumn<Object, Object> colPhoneNo;
    public TableColumn<Object, Object> colAddress;



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        SetDate.setDateTime(lblTime,lblDate);

        colId.setCellValueFactory(new PropertyValueFactory<>("Id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("Name"));
        colNic.setCellValueFactory(new PropertyValueFactory<>("Nic"));
        colPhoneNo.setCellValueFactory(new PropertyValueFactory<>("PhoneNo"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("Address"));

        loadTable();
    }

    public void loadTable(){
        Connection connection = null;
        try {
            connection = DBConnection.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM Suppliers order by SupplierId desc ");
            ResultSet resultSet = preparedStatement.executeQuery();
            ObservableList<Suppliers> list = tblSuppliers.getItems();
            list.clear();

            while (resultSet.next()){
                Suppliers suppliers = new Suppliers(
                        resultSet.getInt(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getInt(4),
                        resultSet.getString(5)
                );
                list.add(suppliers);
            }
            tblSuppliers.refresh();

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

    public void btnMinimizeOnAction(ActionEvent actionEvent) {
        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        stage.setIconified(true);
    }

    public void btnBackOnAction(ActionEvent actionEvent) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/MainMenu.fxml"));
        parent = loader.load();
        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        scene = new Scene(parent);
        stage.setScene(scene);
        stage.show();
    }

    public void btnSearchOnAction(ActionEvent actionEvent) {

        String id = txtSupplierId.getText();
        try {
            Suppliers suppliers = SuppliersModel.searchNumber(id);
            if( suppliers!= null) {
                fillData(suppliers);
            } else {
                new Alert(Alert.AlertType.WARNING, "Supplier Not Found!").show();
            }

        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void fillData(Suppliers suppliers) {
        txtSupplierId.setText(String.valueOf(suppliers.getId()));
        txtSupplierName.setText(suppliers.getName());
        txtNic.setText(suppliers.getNic());
        txtPhoneNo.setText(String.valueOf(suppliers.getPhoneNo()));
        txtAddress.setText(suppliers.getAddress());
    }

    public void btnAddSupplierOnAction(ActionEvent actionEvent) {

        if(txtSupplierId.getText().equals("") || txtSupplierName.getText().equals("") || txtNic.getText().equals("") || txtPhoneNo.getText().equals("") || txtAddress.getText().equals("")){
            new Alert(Alert.AlertType.WARNING,"Please Fill All Fields").show();
        }
        else{
            int Id = Integer.parseInt(txtSupplierId.getText());
            String Name = txtSupplierName.getText();
            String Nic = txtNic.getText();
            int PhoneNo = Integer.parseInt(txtPhoneNo.getText());
            String Address = txtAddress.getText();

            Suppliers supplier = new Suppliers(Id, Name, Nic, PhoneNo, Address);
            boolean isAdded = false;
            try {
                isAdded = SuppliersModel.save(supplier);
                if (isAdded) {
                    new Alert(Alert.AlertType.CONFIRMATION, "supplier Added!").show();
                }
                else {
                    new Alert(Alert.AlertType.ERROR, "supplier Cant Added!").show();
                }
            } catch (SQLException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
            clearBoxes();
            loadTable();
        }


    }

    public void btnUpdateSupplierOnAction(ActionEvent actionEvent) {

        if(txtSupplierId.getText().equals("") || txtSupplierName.getText().equals("") || txtNic.getText().equals("") || txtPhoneNo.getText().equals("") || txtAddress.getText().equals("")){
            new Alert(Alert.AlertType.WARNING,"Please Fill All Fields").show();
        }
        else {
            int Id = Integer.parseInt(txtSupplierId.getText());
            String Name = txtSupplierName.getText();
            String Nic = txtNic.getText();
            int PhoneNo = Integer.parseInt(txtPhoneNo.getText());
            String Address = txtAddress.getText();

            Suppliers supplier = new Suppliers(Id, Name, Nic, PhoneNo, Address);

            try {
                boolean isUpdate = SuppliersModel.updateSupplier(supplier);
                if (isUpdate) {
                    new Alert(Alert.AlertType.CONFIRMATION,"Information Updated").show();
                    clearBoxes();
                    loadTable();
                } else {
                    new Alert(Alert.AlertType.ERROR,"Update error...").show();
                }
            } catch (ClassNotFoundException | SQLException ex) {
                throw new RuntimeException(ex);
            }
        }


    }

    public void btnDeleteSupplierOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {

        if(txtSupplierId.getText().equals("") ){
            new Alert(Alert.AlertType.WARNING, "Please Enter Supplier Id").show();
        }
        else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Please Confirm");
            alert.setHeaderText("Are You Sure Do you want to Delete");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                boolean isDeleted = SuppliersModel.deleteSupplier(txtSupplierId.getText());
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
        txtSupplierId.setText("");
        txtSupplierName.setText("");
        txtNic.setText("");
        txtPhoneNo.setText("");
        txtPhoneNo.setText("");
        txtAddress.setText("");
    }


}
