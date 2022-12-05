package lk.ijse.fitnessCenter.controller;

import javafx.application.Platform;
import javafx.collections.FXCollections;
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
import javafx.scene.text.Text;
import javafx.stage.Stage;
import lk.ijse.fitnessCenter.db.DBConnection;
import lk.ijse.fitnessCenter.model.MemberModel;
import lk.ijse.fitnessCenter.model.MembershipPaymentModel;
import lk.ijse.fitnessCenter.model.SuppliersModel;
import lk.ijse.fitnessCenter.to.Additems;
import lk.ijse.fitnessCenter.to.Member;
import lk.ijse.fitnessCenter.to.MembershipPayment;
import lk.ijse.fitnessCenter.to.Suppliers;
import lk.ijse.fitnessCenter.util.SetDate;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Optional;
import java.util.ResourceBundle;

public class MembershipPayemtsController implements Initializable {

    public Text lblDate;
    public Text lblTime;
    public ComboBox cmbMembership;
    public TextField txtMemberId, txtSearchBar;
    public Label lblMemberName;
    public Button btnBuy, btnUpdate, btnDelete;

    public TableView<MembershipPayment> tblMembershipPayments;
    public TableColumn<Object, Object> colType;
    public TableColumn<Object, Object> colPayDate;
    public TableColumn<Object, Object> colExpireDate;
    public TableColumn<Object, Object> colMemberId;
    public TableColumn<Object, Object> colSituation;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        SetDate.setDateTime(lblTime, lblDate);

        cmbMembership.getItems().add("1 Months");
        cmbMembership.getItems().add("3 Months");
        cmbMembership.getItems().add("6 Months");
        cmbMembership.getItems().add("Annual");

        colMemberId.setCellValueFactory(new PropertyValueFactory<>("MemberId"));
        colType.setCellValueFactory(new PropertyValueFactory<>("Type"));
        colPayDate.setCellValueFactory(new PropertyValueFactory<>("paymentDate"));
        colExpireDate.setCellValueFactory(new PropertyValueFactory<>("ExpireDate"));
        loadTable();

        btnBuy.setDisable(true);
        btnUpdate.setDisable(true);
        btnDelete.setDisable(true);

        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        Date date = new Date();
        String paymentDate = dateFormat.format(date);

        System.out.println(paymentDate);


    }

    public void loadTable(){
        Connection connection = null;
        try {
            connection = DBConnection.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM membershipPayment order by MemberID desc");
            ResultSet resultSet = preparedStatement.executeQuery();
            ObservableList<MembershipPayment> list = tblMembershipPayments.getItems();
            list.clear();

            while (resultSet.next()){
                MembershipPayment membershipPayment = new MembershipPayment(
                        resultSet.getInt(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4)

                );
                list.add(membershipPayment);
            }
            tblMembershipPayments.refresh();

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
        lblMemberName.setText(member.getName());
        btnBuy.setDisable(false);
        btnUpdate.setDisable(false);
        btnDelete.setDisable(false);
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
                PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM membershipPayment WHERE MemberId = ? OR Type = ? ");
                preparedStatement.setObject(1,search);
                preparedStatement.setObject(2,search);
                ResultSet resultSet = preparedStatement.executeQuery();
                ObservableList<MembershipPayment> list = tblMembershipPayments.getItems();
                list.clear();

                while (resultSet.next()){
                    MembershipPayment membershipPayment = new MembershipPayment(
                            resultSet.getInt(1),
                            resultSet.getString(2),
                            resultSet.getString(3),
                            resultSet.getString(4)
                    );
                    list.add(membershipPayment);
                }

            } catch (SQLException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void btnBuyOnAction(ActionEvent actionEvent) {
        int memberId = Integer.parseInt(txtMemberId.getText());
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        Date date = new Date();

        String type = cmbMembership.getSelectionModel().getSelectedItem().toString();
        String paymentDate = dateFormat.format(date);
        String ExpireDate = null;

        if (type.equals("1 Months")){
            ExpireDate = addMonth(1);
        }
        else if (type.equals("3 Months")){
            ExpireDate = addMonth(3);
        }
        else if (type.equals("6 Months")){
            ExpireDate = addMonth(6);
        }
        else{
            ExpireDate = addMonth(12);
        }

        MembershipPayment membershipPayment = new MembershipPayment(memberId, type, paymentDate, ExpireDate);
        try {
            boolean isAdded = MembershipPaymentModel.save(membershipPayment);
            if (isAdded) {
                new Alert(Alert.AlertType.CONFIRMATION, "Payment Success!").show();
                loadTable();
                afterbuttonClick();
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void btnUpdateOnAction(ActionEvent actionEvent) {
        int memberId = Integer.parseInt(txtMemberId.getText());
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        Date date = new Date();

        String type = cmbMembership.getSelectionModel().getSelectedItem().toString();
        String paymentDate = dateFormat.format(date);
        String ExpireDate = null;

        if (type.equals("1 Months")){
            ExpireDate = addMonth(1);
        }
        else if (type.equals("3 Months")){
            ExpireDate = addMonth(3);
        }
        else if (type.equals("6 Months")){
            ExpireDate = addMonth(6);
        }
        else{
            ExpireDate = addMonth(12);
        }
        MembershipPayment membershipPayment = new MembershipPayment(memberId, type, paymentDate, ExpireDate);

        try {
            boolean isUpdate = MembershipPaymentModel.updateMembershipPayment(membershipPayment);
            if (isUpdate) {
                new Alert(Alert.AlertType.CONFIRMATION,"Information Updated").show();
                loadTable();
                afterbuttonClick();
            } else {
                new Alert(Alert.AlertType.ERROR,"Update error...").show();
            }
        } catch (ClassNotFoundException | SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    public void btnDeleteOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        if(txtMemberId.getText().equals("") ){
            new Alert(Alert.AlertType.WARNING, "Please Enter Supplier Id").show();
        }
        else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Please Confirm");
            alert.setHeaderText("Are You Sure Do you want to Delete");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                boolean isDeleted = MembershipPaymentModel.deleteSupplier(txtMemberId.getText());
                if (isDeleted) {
                    new Alert(Alert.AlertType.CONFIRMATION, "Successfully deleted").show();
                    afterbuttonClick();
                } else {
                    new Alert(Alert.AlertType.ERROR, "error").show();
                }
            }

            loadTable();
        }
    }

    public void btnDuePaymentOnAction(ActionEvent actionEvent) {
    }


    public static String addMonth(int n){

        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH,n);
        String result = dateFormat.format(cal.getTime());

        return result;
    }

    public void afterbuttonClick(){
        txtMemberId.setText("");
        btnBuy.setDisable(true);
        btnUpdate.setDisable(true);
        btnDelete.setDisable(true);
    }

    public void btnReloadOnAction(ActionEvent actionEvent) {
        loadTable();
        txtSearchBar.setText("");
    }
}
