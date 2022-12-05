package lk.ijse.fitnessCenter.to;

public class Orders {
    private int OrderId;
    private int ItemId;
    private String Name;
    private double Price;
    private int qty;
    private double Total;
    private int MemberId;
    private String PayDate;

    public Orders(int orderId, int itemId, String name, double price, int qty, double total, int memberId, String payDate) {
        OrderId = orderId;
        ItemId = itemId;
        Name = name;
        Price = price;
        this.qty = qty;
        Total = total;
        MemberId = memberId;
        PayDate = payDate;
    }

    public int getOrderId() {
        return OrderId;
    }

    public void setOrderId(int orderId) {
        OrderId = orderId;
    }

    public int getItemId() {
        return ItemId;
    }

    public void setItemId(int itemId) {
        ItemId = itemId;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public double getPrice() {
        return Price;
    }

    public void setPrice(double price) {
        Price = price;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public double getTotal() {
        return Total;
    }

    public void setTotal(double total) {
        Total = total;
    }

    public int getMemberId() {
        return MemberId;
    }

    public void setMemberId(int memberId) {
        MemberId = memberId;
    }

    public String getPayDate() {
        return PayDate;
    }

    public void setPayDate(String payDate) {
        PayDate = payDate;
    }

    @Override
    public String toString() {
        return "Orders{" +
                "OrderId=" + OrderId +
                ", ItemId=" + ItemId +
                ", Name='" + Name + '\'' +
                ", Price=" + Price +
                ", qty=" + qty +
                ", Total=" + Total +
                ", MemberId=" + MemberId +
                ", PayDate='" + PayDate + '\'' +
                '}';
    }
}
