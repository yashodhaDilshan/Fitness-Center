package lk.ijse.fitnessCenter.model;

import javafx.scene.input.InputMethodTextRun;
import lk.ijse.fitnessCenter.db.DBConnection;
import lk.ijse.fitnessCenter.to.Employee;
import lk.ijse.fitnessCenter.util.CrudUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EmployeeModel {

    public static boolean save(Employee employee) throws SQLException, ClassNotFoundException {


        return CrudUtil.execute("INSERT INTO employee VALUES(?, ?, ?, ?, ?, ?, ?)",
                employee.getTrainerId(),
                employee.getTrainerName(),
                employee.getNic(),
                employee.getAge(),
                employee.getGender(),
                employee.getPhoneNo(),
                employee.getAddress()
        );
    }
    private Object EmployeeModel;

    public static boolean deleteTrainer(String id) throws ClassNotFoundException, SQLException {
        return DBConnection.getInstance().getConnection().createStatement().executeUpdate("Delete From employee where TrainerId='" + id + "'") > 0;

    }

    public static boolean updateEmployee(Employee employee) throws SQLException, ClassNotFoundException {
        Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement stm = connection.prepareStatement("Update employee set TrainerName=?, nic=?, Age=? ,Gender=? ,Phoneno=? ,Address=?  where TrainerId=?");

        stm.setObject(1, employee.getTrainerName());
        stm.setObject(2, employee.getNic());
        stm.setObject(3, employee.getAge());
        stm.setObject(4, employee.getGender());
        stm.setObject(5, employee.getPhoneNo());
        stm.setObject(6, employee.getAddress());
        stm.setObject(7, employee.getTrainerId());

        return stm.executeUpdate() > 0;
    }

    public static Employee searchNumber(String id) throws SQLException, ClassNotFoundException {
        ResultSet result = CrudUtil.execute("SELECT * FROM employee WHERE TrainerId = ?", id);

        if (result.next()) {
            return new Employee(
                    result.getInt(1),
                    result.getString(2),
                    result.getString(3),
                    result.getInt(4),
                    result.getString(5),
                    result.getInt(6),
                    result.getString(7)
            );
        }
        return null;
    }



}
