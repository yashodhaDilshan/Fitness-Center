package lk.ijse.fitnessCenter.model;

import javafx.scene.control.Alert;
import javafx.scene.input.InputMethodTextRun;
import lk.ijse.fitnessCenter.db.DBConnection;
import lk.ijse.fitnessCenter.to.Member;
import lk.ijse.fitnessCenter.util.CrudUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MemberModel {
    public static boolean save(Member member) throws SQLException, ClassNotFoundException {


        return CrudUtil.execute("INSERT INTO member VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)",
                member.getId(),
                member.getName(),
                member.getNic(),
                member.getPhoneno(),
                member.getAge(),
                member.getWeight(),
                member.getHeight(),
                member.getGender(),
                member.getGoal(),
                member.getAddress()


        );
    }

    private Object MemberModel;
    private InputMethodTextRun txtId;
    /*
            public static Employee search(String id) throws SQLException, ClassNotFoundException {
                ResultSet result = CrudUtil.execute("SELECT * FROM Employee WHERE employeeId = ?", id);

                if(result.next()) {
                    return new Employee(
                            result.getString(1),
                            result.getString(2),
                            result.getString(3),
                            result.getInt(4),
                            result.getString(5),
                            result.getString(6)

                    );
                }
                return null;
            }

            public static Employee searchNumber(String number) throws SQLException, ClassNotFoundException {
                ResultSet result = CrudUtil.execute("SELECT * FROM Employee WHERE employeePhone = ?", number);

                if(result.next()) {
                    return new Employee(
                            result.getString(1),
                            result.getString(2),
                            result.getString(3),
                            result.getInt(4),
                            result.getString(5),
                            result.getString(6)

                    );
                }
                return null;
            }

            public static boolean deleteEmployee(String id) throws ClassNotFoundException, SQLException {
                return  DBConnection.getInstance().getConnection().createStatement().executeUpdate("Delete From Employee where EmployeeId='"+id+"'")>0;
            }

            public static boolean updateEmployee(Employee employee) throws ClassNotFoundException, SQLException {
                Connection connection = DBConnection.getInstance().getConnection();
                PreparedStatement stm = connection.prepareStatement("Update Employee set employeeName=?, employeeAddress=?, employeePhone=?, employeeEmail=?, employeeJob=?  where employeeId=?");
                //PreparedStatement stm = CrudUtil.execute("Update Employee set employeeName=?, employeeAddress=?, employeePhone=?, employeeEmail=?, employeeJob=?  where id=?");
                stm.setObject(1, employee.getName());
                stm.setObject(2, employee.getAdress());
                stm.setObject(3, employee.getNumber());
                stm.setObject(4, employee.getEmail());
                stm.setObject(5, employee.getJob());
                stm.setObject(6, employee.getId());

                return  stm.executeUpdate()>0;
            }

         */

    public static boolean deleteMember(String id) throws ClassNotFoundException, SQLException {
        return DBConnection.getInstance().getConnection().createStatement().executeUpdate("Delete From Member where MemberId='" + id + "'") > 0;


    }

    public static boolean updateMember(Member member) throws ClassNotFoundException, SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement stm = connection.prepareStatement("Update Member set memberName=?, nic=?, Phoneno=?,Age=? ,Weight=? ,Height=? ,Gender=? ,Goal=?,Address=? where memberId=?");
        //PreparedStatement stm = CrudUtil.execute("Update Member set memberId=?, memberName=?, memberNic=?, memberPhoneno=?, memberAge=? ,memberWeight=? ,memberHeight=? ,memberGender=? ,memberGoal=?,MemberAddress where MemberId=?");
       ;
        stm.setObject(1, member.getName());
        stm.setObject(2, member.getNic());
        stm.setObject(3, member.getPhoneno());
        stm.setObject(4, member.getAge());
        stm.setObject(5, member.getWeight());
        stm.setObject(6, member.getHeight());
        stm.setObject(7, member.getGender());
        stm.setObject(8, member.getGoal());
        stm.setObject(9, member.getAddress());
        stm.setObject(10, member.getId());


        return stm.executeUpdate() > 0;
    }

    public static Member searchNumber(String id) throws SQLException, ClassNotFoundException {
        ResultSet result = CrudUtil.execute("SELECT * FROM Member WHERE MemberID = ?", id);

        if (result.next()) {
            return new Member(
                    result.getInt(1),
                    result.getString(2),
                    result.getString(3),
                    result.getInt(4),
                    result.getInt(5),
                    result.getDouble(6),
                    result.getDouble(7),
                    result.getString(8),
                    result.getString(9),
                    result.getString(10)
            );
        }
        return null;
    }
}
