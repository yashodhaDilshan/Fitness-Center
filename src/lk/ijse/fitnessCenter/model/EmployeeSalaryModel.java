package lk.ijse.fitnessCenter.model;

import lk.ijse.fitnessCenter.to.EmployeeSalary;
import lk.ijse.fitnessCenter.util.CrudUtil;

import java.sql.SQLException;

public class EmployeeSalaryModel {
    public static boolean save(EmployeeSalary employeeSalary) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("INSERT INTO EmployeeSalary VALUES(?, ?, ?, ?, ?, ?, ?, ?)",
                employeeSalary.getSaleryId(),
                employeeSalary.getTrainerId(),
                employeeSalary.getName(),
                employeeSalary.getSalary(),
                employeeSalary.getAllowance(),
                employeeSalary.getTotal(),
                employeeSalary.getPaymentDate(),
                employeeSalary.getStatus()


        );
    }
}
