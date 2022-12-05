package lk.ijse.fitnessCenter.controller;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
import lk.ijse.fitnessCenter.model.AdditemsModel;
import lk.ijse.fitnessCenter.model.MemberModel;
import lk.ijse.fitnessCenter.model.OrderModel;
import lk.ijse.fitnessCenter.to.Additems;
import lk.ijse.fitnessCenter.to.Member;
import lk.ijse.fitnessCenter.to.Orders;
import lk.ijse.fitnessCenter.util.SetDate;
import lk.ijse.fitnessCenter.model.MemberModel;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;
import java.util.ResourceBundle;

public class OrdersController implements Initializable {

    Parent parent;
    Scene scene;
    Stage stage;
    public Text lblDate;
    public Text lblTime;

    public TableView<Additems> tblItems;
    public TableColumn<Object, Object> colId;
    public TableColumn<Object, Object> colItemName;
    public TableColumn<Object, Object> colBrandName;
    public TableColumn<Object, Object> colSize;
    public TableColumn<Object, Object> colQty;
    public TableColumn<Object, Object> colPrice;
    public TableColumn<Object, Object> colAbout;

    public TextField txtSearchBar, txtQty, txtMemberId;
    public Label lblItemId, lblItemName, lblBrandName, lblQty, lblPrice, lblTotal;
    public Button btnPay;

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
        btnPay.setDisable(true);

        tblItems.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {

                Additems selectItem = tblItems.getSelectionModel().getSelectedItem();

                if (selectItem == null)
                {
                    return;
                }
                lblItemId.setText(String.valueOf(selectItem.getItemId()));
                lblItemName.setText(selectItem.getItemName());
                lblBrandName.setText(selectItem.getBrandName());
                lblQty.setText(String.valueOf(selectItem.getQty()));
                lblPrice.setText(String.valueOf(selectItem.getPrice()));
            }
        });
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

    public void btnBackOnAction(ActionEvent actionEvent) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/MainMenu.fxml"));
        parent = loader.load();
        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        scene = new Scene(parent);
        stage.setScene(scene);
        stage.show();
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

    public void btnPayOnAction(ActionEvent actionEvent) {


        lblItemId.getText().toString();
        if(txtQty.getText().equals("")){
            new Alert(Alert.AlertType.WARNING,"Please Fill All Fields").show();
        }
        else {
            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
            java.util.Date date = new Date();
            String PayDate = dateFormat.format(date);

            // Find Last OrderId
            Connection connection = null;
            int Oid;
            try {
                connection = DBConnection.getInstance().getConnection();
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery("SELECT OrderId FROM Orders order by OrderId desc limit 1");
                resultSet.next();

                Oid = resultSet.getInt(1);
                System.out.println(Oid);

            } catch (SQLException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }

            int ItemId = Integer.parseInt(lblItemId.getText());
            String Name = lblItemName.getText();
            double price = new Double(lblPrice.getText());
            int Qty = Integer.parseInt(txtQty.getText());
            int nowQty = Integer.parseInt(lblQty.getText());
            double Total = price * Qty;
            int OrderId = 0;
            int UpdateQty = nowQty - Qty;
            int MemberId = Integer.parseInt(txtMemberId.getText());

            if (Oid == 0){
                OrderId = 1;
            }
            else {
                OrderId = ++Oid;
            }

            //Add Database
            Orders orders = new Orders(OrderId, ItemId, Name, price, Qty, Total, MemberId, PayDate);
            boolean isAdded = false;
            try {
                isAdded = OrderModel.save(orders);
                if (isAdded) {
                    new Alert(Alert.AlertType.CONFIRMATION, "Order Done!").show();

                } else {
                    new Alert(Alert.AlertType.ERROR, "Something Wrong!").show();
                }
            } catch (SQLException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }

            // Update Quantity
            Additems addItem = new Additems(ItemId, UpdateQty);
            try {
                AdditemsModel.UpdateQty(addItem);

            } catch (SQLException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
            loadTable();
            reset();
            btnPay.setDisable(true);

        }
    }

    public void reset(){
        txtQty.setText("");
        txtMemberId.setText("");
        txtSearchBar.setText("");
        lblItemId.setText("");
        lblItemName.setText("");
        lblBrandName.setText("");
        lblQty.setText("");
        lblPrice.setText("");
    }


    public void btnReloadOnAction(ActionEvent actionEvent) {
        loadTable();
        txtQty.setText("");
        txtMemberId.setText("");
        txtSearchBar.setText("");
        lblItemId.setText("");
        lblItemName.setText("");
        lblBrandName.setText("");
        lblQty.setText("");
        lblPrice.setText("");
    }


    public void btnSearchMemberOnAction(ActionEvent actionEvent) {
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
        btnPay.setDisable(false);
    }
}
