package lk.ijse.fitnessCenter.model;


import lk.ijse.fitnessCenter.db.DBConnection;
import lk.ijse.fitnessCenter.to.MembershipPayment;
import lk.ijse.fitnessCenter.util.CrudUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class MembershipPaymentModel {


    public static boolean save(MembershipPayment membershipPayment) throws SQLException, ClassNotFoundException {

        return CrudUtil.execute("INSERT INTO membershipPayment VALUES(?, ?, ?, ?)",
                membershipPayment.getMemberId(),
                membershipPayment.getType(),
                membershipPayment.getPaymentDate(),
                membershipPayment.getExpireDate()

        );
    }

    public static boolean updateMembershipPayment(MembershipPayment membershipPayment) throws SQLException, ClassNotFoundException {
        Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement stm = connection.prepareStatement("Update membershipPayment set Type=?, paymentdate=?, expirydate=? where MemberId=?");

        stm.setObject(1, membershipPayment.getType());
        stm.setObject(2, membershipPayment.getPaymentDate());
        stm.setObject(3, membershipPayment.getExpireDate());
        stm.setObject(4, membershipPayment.getMemberId());

        return stm.executeUpdate() > 0;
    }

    public static boolean deleteSupplier(String id) throws SQLException, ClassNotFoundException {
        return DBConnection.getInstance().getConnection().createStatement().executeUpdate("Delete From membershipPayment where MemberId='" + id + "'") > 0;
    }
}
