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
import javafx.stage.Stage;
import lk.ijse.fitnessCenter.db.DBConnection;
import lk.ijse.fitnessCenter.to.Additems;
import lk.ijse.fitnessCenter.to.Employee;
import lk.ijse.fitnessCenter.to.Member;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.Optional;
import java.util.ResourceBundle;

public class AllMemberController implements Initializable {

    public TableView<Member> tblMember;
    public TableColumn<Object, Object> colId;
    public TableColumn<Object, Object> colName;
    public TableColumn<Object, Object> colNic;
    public TableColumn<Object, Object> colPhoneNo;
    public TableColumn<Object, Object> colAge;
    public TableColumn<Object, Object> colGender;
    public TableColumn<Object, Object> colAddress;
    public TableColumn<Object, Object> colWeight;
    public TableColumn<Object, Object> colHeight;
    public TableColumn<Object, Object> colGoal;

    public TextField txtSearchBar;

    Parent parent;
    Scene scene;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        colId.setCellValueFactory(new PropertyValueFactory<>("Id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("Name"));
        colNic.setCellValueFactory(new PropertyValueFactory<>("Nic"));
        colPhoneNo.setCellValueFactory(new PropertyValueFactory<>("Phoneno"));
        colAge.setCellValueFactory(new PropertyValueFactory<>("age"));
        colGender.setCellValueFactory(new PropertyValueFactory<>("gender"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colWeight.setCellValueFactory(new PropertyValueFactory<>("weight"));
        colHeight.setCellValueFactory(new PropertyValueFactory<>("height"));
        colGoal.setCellValueFactory(new PropertyValueFactory<>("goal"));

        loadTable();
    }

    public void loadTable(){
        Connection connection = null;
        try {
            connection = DBConnection.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM Member order by MemberID desc ");
            ResultSet resultSet = preparedStatement.executeQuery();
            ObservableList<Member> list = tblMember.getItems();
            list.clear();

            while (resultSet.next()){
                Member member = new Member(
                        resultSet.getInt(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getInt(4),
                        resultSet.getInt(5),
                        resultSet.getDouble(6),
                        resultSet.getDouble(7),
                        resultSet.getString(8),
                        resultSet.getString(9),
                        resultSet.getString(10)
                );
                list.add(member);
            }
            tblMember.refresh();

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

        FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/Member.fxml"));
        parent = loader.load();
        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        scene = new Scene(parent);
        stage.setScene(scene);
        stage.show();
    }




    public void btnSearchOnAction(ActionEvent actionEvent) {

        String search = txtSearchBar.getText();
        Connection connection = null;
        try {
            connection = DBConnection.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM Member WHERE MemberId = ? OR memberName = ? OR Nic = ? OR Address = ?");
            preparedStatement.setObject(1,search);
            preparedStatement.setObject(2,search);
            preparedStatement.setObject(3,search);
            preparedStatement.setObject(4,search);
            ResultSet resultSet = preparedStatement.executeQuery();
            ObservableList<Member> list = tblMember.getItems();
            list.clear();

            while (resultSet.next()){
                Member member = new Member(
                        resultSet.getInt(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getInt(4),
                        resultSet.getInt(5),
                        resultSet.getDouble(6),
                        resultSet.getDouble(7),
                        resultSet.getString(8),
                        resultSet.getString(9),
                        resultSet.getString(10)
                );
                list.add(member);
            }
            tblMember.refresh();

        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }


    public void btnReloadOnAction(ActionEvent actionEvent) {
        loadTable();
        txtSearchBar.setText("");
    }
}

