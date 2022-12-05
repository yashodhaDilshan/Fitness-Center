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
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import lk.ijse.fitnessCenter.db.DBConnection;
import lk.ijse.fitnessCenter.to.MembershipPayment;
import lk.ijse.fitnessCenter.to.Orders;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

public class StorePaymentController implements Initializable {

    public TableView<Orders> tblStorePayment;
    public TableColumn<Object, Object> colOrderId;
    public TableColumn<Object, Object> colItemId;
    public TableColumn<Object, Object> colName;
    public TableColumn<Object, Object> colPrice;
    public TableColumn<Object, Object> colQty;
    public TableColumn<Object, Object> colTotal;
    public TableColumn<Object, Object> colDate;
    public TableColumn<Object, Object> colMemberId;

    public TextField txtSearchBar;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        colOrderId.setCellValueFactory(new PropertyValueFactory<>("OrderId"));
        colItemId.setCellValueFactory(new PropertyValueFactory<>("ItemId"));
        colName.setCellValueFactory(new PropertyValueFactory<>("Name"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("Price"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colTotal.setCellValueFactory(new PropertyValueFactory<>("Total"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("PayDate"));
        colMemberId.setCellValueFactory(new PropertyValueFactory<>("MemberId"));

        loadTable();


    }
    public void loadTable(){
        Connection connection = null;
        try {
            connection = DBConnection.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM Orders order by OrderId desc ");
            ResultSet resultSet = preparedStatement.executeQuery();
            ObservableList<Orders> list = tblStorePayment.getItems();
            list.clear();

            while (resultSet.next()){
                Orders orders = new Orders(
                        resultSet.getInt(1),
                        resultSet.getInt(2),
                        resultSet.getString(3),
                        resultSet.getInt(4),
                        resultSet.getInt(5),
                        resultSet.getInt(6),
                        resultSet.getInt(7),
                        resultSet.getString(8)

                );
                list.add(orders);
            }
            tblStorePayment.refresh();

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

        FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/Payments.fxml"));
        Parent parent = loader.load();
        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(parent);
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
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
                PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM Orders WHERE OrderId = ? OR ItemName = ? OR MemberId = ?");
                preparedStatement.setObject(1,search);
                preparedStatement.setObject(2,search);
                preparedStatement.setObject(3,search);
                ResultSet resultSet = preparedStatement.executeQuery();
                ObservableList<Orders> list = tblStorePayment.getItems();
                list.clear();

                while (resultSet.next()){
                    Orders orders = new Orders(
                            resultSet.getInt(1),
                            resultSet.getInt(2),
                            resultSet.getString(3),
                            resultSet.getInt(4),
                            resultSet.getInt(5),
                            resultSet.getInt(6),
                            resultSet.getInt(7),
                            resultSet.getString(8)
                    );
                    list.add(orders);
                }

            } catch (SQLException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }


    public void btnReloadOnAction(ActionEvent actionEvent) {
        loadTable();
        txtSearchBar.setText("");
    }
}
