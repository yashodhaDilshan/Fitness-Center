package lk.ijse.fitnessCenter.model;

import lk.ijse.fitnessCenter.db.DBConnection;
import lk.ijse.fitnessCenter.to.Member;
import lk.ijse.fitnessCenter.to.Suppliers;
import lk.ijse.fitnessCenter.util.CrudUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SuppliersModel {
    public static boolean save(Suppliers suppliers) throws SQLException, ClassNotFoundException {

        return CrudUtil.execute("INSERT INTO Suppliers VALUES(?, ?, ?, ?, ?)",
                suppliers.getId(),
                suppliers.getName(),
                suppliers.getNic(),
                suppliers.getPhoneNo(),
                suppliers.getAddress()
        );
    }

    public static boolean deleteSupplier(String id) throws SQLException, ClassNotFoundException {
        return DBConnection.getInstance().getConnection().createStatement().executeUpdate("Delete From Suppliers where SupplierId='" + id + "'") > 0;
    }

    public static boolean updateSupplier(Suppliers supplier) throws SQLException, ClassNotFoundException {
        Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement stm = connection.prepareStatement("Update Suppliers set SupplierName=?, Nic=?, Phoneno=?,Address=? where SupplierId=?");

        stm.setObject(1, supplier.getName());
        stm.setObject(2, supplier.getNic());
        stm.setObject(3, supplier.getPhoneNo());
        stm.setObject(4, supplier.getAddress());
        stm.setObject(5, supplier.getId());

        return stm.executeUpdate() > 0;
    }

    public static Suppliers searchNumber(String id) throws SQLException, ClassNotFoundException {

        ResultSet result = CrudUtil.execute("SELECT * FROM Suppliers WHERE SupplierId = ?", id);

        if (result.next()) {
            return new Suppliers(
                    result.getInt(1),
                    result.getString(2),
                    result.getString(3),
                    result.getInt(4),
                    result.getString(5)
            );
        }
        return null;
    }



}
