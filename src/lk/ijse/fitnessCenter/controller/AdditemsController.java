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
import javafx.scene.input.InputMethodTextRun;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.scene.text.Text;
import lk.ijse.fitnessCenter.db.DBConnection;
import lk.ijse.fitnessCenter.model.AdditemsModel;
import lk.ijse.fitnessCenter.model.MemberModel;
import lk.ijse.fitnessCenter.to.Additems;
import lk.ijse.fitnessCenter.to.Member;
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

public class AdditemsController implements Initializable {

    Parent parent;
    Scene scene;
    Stage stage;
    public Text lblDate;
    public Text lblTime;
    public TextField txtItemId,txtItemName,txtBrandName, txtPrice, txtSize, txtQty, txtAbout, txtSearchBar;

    public TableView<Additems> tblItems;
    public TableColumn<Object, Object> colId;
    public TableColumn<Object, Object> colItemName;
    public TableColumn<Object, Object> colBrandName;
    public TableColumn<Object, Object> colSize;
    public TableColumn<Object, Object> colQty;
    public TableColumn<Object, Object> colPrice;
    public TableColumn<Object, Object> colAbout;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        SetDate.setDateTime(lblTime, lblDate);

        colId.setCellValueFactory(new PropertyValueFactory<>("itemId"));
        colItemName.setCellValueFactory(new PropertyValueFactory<>("ItemName"));
        colBrandName.setCellValueFactory(new PropertyValueFactory<>("BrandName"));
        colSize.setCellValueFactory(new PropertyValueFactory<>("Size"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("Qty"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("Price"));
        colAbout.setCellValueFactory(new PropertyValueFactory<>("About"));

        loadTable();
    }

    public void loadTable(){
        Connection connection = null;
        try {
            connection = DBConnection.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM additems ");
            ResultSet resultSet = preparedStatement.executeQuery();
            ObservableList<Additems> list = tblItems.getItems();
            list.clear();

            while (resultSet.next()){
                Additems additems = new Additems(
                        resultSet.getInt(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getDouble(4),
                        resultSet.getDouble(5),
                        resultSet.getInt(6),
                        resultSet.getString(7)
                );
                list.add(additems);
            }
            tblItems.refresh();

        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }

    public void btnExitOnAction(ActionEvent actionEvent) {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Please Confirm");
        alert.setHeaderText("Are You Sure Do you wont to Exit");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            Platform.exit();
        }
    }

    public void btnMinimizeOnAction(ActionEvent actionEvent) {
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setIconified(true);
    }

    public void btnBackOnAction(ActionEvent actionEvent) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/MainMenu.fxml"));
        parent = loader.load();
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        scene = new Scene(parent);
        stage.setScene(scene);
        stage.show();

    }

    public void btntestonaction(ActionEvent actionEvent) {
        String id = txtItemId.getText();
        try {
            Additems additems = AdditemsModel.searchNumber(id);
            if( additems!= null) {
                fillData(additems);
            } else {
                new Alert(Alert.AlertType.WARNING, "Member Not Found!").show();
            }

        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    private void fillData(Additems additems) {
        txtItemId.setText(String.valueOf(additems.getItemId()));
        txtItemName.setText(additems.getItemName());
        txtBrandName.setText(additems.getBrandName());
        txtPrice.setText(String.valueOf(additems.getPrice()));
        txtSize.setText(String.valueOf(additems.getSize()));
        txtQty.setText(String.valueOf(additems.getQty()));
        txtAbout.setText(additems.getAbout());
    }

    public void btnAddItemOnAction(ActionEvent event) {

        if (txtItemId.getText().equals("") || txtItemName.getText().equals("") || txtBrandName.getText().equals("") || txtPrice.getText().equals("") || txtSize.getText().equals("") || txtQty.getText().equals("") || txtAbout.getText().equals(""))
        {
            new Alert(Alert.AlertType.WARNING,"Please Fill All Fields").show();
        }
        else {
            int ItemId = Integer.parseInt(txtItemId.getText());
            String ItemName = txtItemName.getText();
            String BrandName = txtBrandName.getText();
            double Price = new Double(txtPrice.getText());
            double Size = new Double(txtSize.getText());
            int Qty = Integer.parseInt(txtQty.getText());
            String About = txtAbout.getText();

            Additems addItem = new Additems(ItemId, ItemName, BrandName, Price, Size, Qty, About);

            try {
                boolean isAdded = AdditemsModel.save(addItem);
                if (isAdded){
                    new Alert(Alert.AlertType.CONFIRMATION, "Item Added").show();
                }
            } catch (SQLException | ClassNotFoundException e){
                throw new RuntimeException(e);
            }
            clearBox(); // Clean Boxes
            loadTable();
        }
    }

    public void btnUpdateItemOnAction(ActionEvent event) {

        if (txtItemId.getText().equals("") || txtItemName.getText().equals("") || txtBrandName.getText().equals("") || txtPrice.getText().equals("") || txtSize.getText().equals("") || txtQty.getText().equals("") || txtAbout.getText().equals(""))
        {
            new Alert(Alert.AlertType.WARNING,"Please Fill All Fields").show();
        }
        else {
            int ItemId = Integer.parseInt(txtItemId.getText());
            String ItemName = txtItemName.getText();
            String BrandName = txtBrandName.getText();
            double Price = new Double(txtPrice.getText());
            double Size = new Double(txtSize.getText());
            int Qty = Integer.parseInt(txtQty.getText());
            String About = txtAbout.getText();

            Additems addItem = new Additems(ItemId, ItemName, BrandName, Price, Size, Qty, About);

            try {
                boolean isUpdate = AdditemsModel.updateItems(addItem);
                if (isUpdate){
                    new Alert(Alert.AlertType.CONFIRMATION, "Information Updated").show();
                    loadTable();
                } else {
                    new Alert(Alert.AlertType.ERROR,"Update error...").show();
                }
            } catch (SQLException | ClassNotFoundException e){
                throw new RuntimeException(e);
            }
            clearBox(); // Clean Boxes

        }
    }

    public void btnDeleteOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {

        if (txtItemId.getText().equals(""))
        {
            new Alert(Alert.AlertType.WARNING,"Please Insert Item Id").show();
        }
        else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Please Confirm");
            alert.setHeaderText("Are You Sure Do you want to Delete");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK)
            {
                boolean isDeleted = AdditemsModel.deleteItem(txtItemId.getText());
                if (isDeleted) {
                    new Alert(Alert.AlertType.CONFIRMATION, "Successfully deleted").show();
                } else {
                    new Alert(Alert.AlertType.ERROR, "error").show();
                }
            }
            clearBox(); // Clean Boxes
            loadTable();
        }
    }

    public void btnSearchTableOnAction(ActionEvent actionEvent) {
        if(txtSearchBar.getText().equals("")){
            loadTable();
        }
        else {
            String search = txtSearchBar.getText();
            Connection connection = null;
            try {
                connection = DBConnection.getInstance().getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM additems WHERE Itemid = ? OR ItemName = ? OR BrandName = ?");
                preparedStatement.setObject(1,search);
                preparedStatement.setObject(2,search);
                preparedStatement.setObject(3,search);
                ResultSet resultSet = preparedStatement.executeQuery();
                ObservableList<Additems> list = tblItems.getItems();
                list.clear();

                while (resultSet.next()){
                    Additems additems = new Additems(
                            resultSet.getInt(1),
                            resultSet.getString(2),
                            resultSet.getString(3),
                            resultSet.getDouble(4),
                            resultSet.getDouble(5),
                            resultSet.getInt(6),
                            resultSet.getString(7)
                    );
                    list.add(additems);
                }
                tblItems.refresh();

            } catch (SQLException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }


        }
    }

    public void btnReloadOnAction(ActionEvent actionEvent) {
        loadTable();
        txtSearchBar.setText("");
    }



    public void clearBox(){
        txtItemId.setText("");
        txtItemName.setText("");
        txtBrandName.setText("");
        txtPrice.setText("");
        txtSize.setText("");
        txtQty.setText("");
        txtAbout.setText("");
    }



}

