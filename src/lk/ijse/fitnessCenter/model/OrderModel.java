package lk.ijse.fitnessCenter.model;

import lk.ijse.fitnessCenter.to.Orders;
import lk.ijse.fitnessCenter.util.CrudUtil;

import java.sql.SQLException;

public class OrderModel {
    public static boolean save(Orders orders) throws SQLException, ClassNotFoundException {

        return CrudUtil.execute("INSERT INTO Orders VALUES(?, ?, ?, ?, ?, ?, ?, ?)",
                orders.getOrderId(),
                orders.getItemId(),
                orders.getName(),
                orders.getPrice(),
                orders.getQty(),
                orders.getTotal(),
                orders.getMemberId(),
                orders.getPayDate()

        );
    }
}
