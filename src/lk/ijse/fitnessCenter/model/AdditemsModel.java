package lk.ijse.fitnessCenter.model;

import javafx.scene.input.InputMethodTextRun;
import lk.ijse.fitnessCenter.db.DBConnection;
import lk.ijse.fitnessCenter.to.Additems;
import lk.ijse.fitnessCenter.to.Member;
import lk.ijse.fitnessCenter.util.CrudUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AdditemsModel {
    public static boolean save(Additems additems) throws SQLException, ClassNotFoundException {


        return CrudUtil.execute("INSERT INTO additems VALUES(?, ?, ?, ?, ?, ?, ?)",
                additems.getItemId(),
                additems.getItemName(),
                additems.getBrandName(),
                additems.getPrice(),
                additems.getSize(),
                additems.getQty(),
                additems.getAbout()

        );
    }

    public static Additems searchNumber(String id) throws SQLException, ClassNotFoundException {
        ResultSet result = CrudUtil.execute("SELECT * FROM additems WHERE Itemid = ?", id);

        if (result.next()) {
            return new Additems(
                    result.getInt(1),
                    result.getString(2),
                    result.getString(3),
                    result.getDouble(4),
                    result.getDouble(5),
                    result.getInt(6),
                    result.getString(7)
            );
        }
        return null;
    }


    public static boolean deleteItem(String id) throws ClassNotFoundException, SQLException {
        return DBConnection.getInstance().getConnection().createStatement().executeUpdate("Delete From additems where Itemid='" + id + "'") > 0;

    }

    public static boolean updateItems(Additems additems) throws ClassNotFoundException, SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement stm = connection.prepareStatement("Update additems set ItemName=?, BrandName=?, Price=?,Size=? ,Qty=? ,About=? where Itemid=?");

        stm.setObject(1, additems.getItemName());
        stm.setObject(2, additems.getBrandName());
        stm.setObject(3, additems.getPrice());
        stm.setObject(4, additems.getSize());
        stm.setObject(5, additems.getQty());
        stm.setObject(6, additems.getAbout());
        stm.setObject(7, additems.getItemId());

        return stm.executeUpdate() > 0;
    }


    public static boolean UpdateQty(Additems addItem) throws SQLException, ClassNotFoundException {
        Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement stm = connection.prepareStatement("Update additems set Qty=? where Itemid=?");

        stm.setObject(1, addItem.getUpdateQty());
        stm.setObject(2, addItem.getItemId());


        return stm.executeUpdate() > 0;
    }
}



