package lk.ijse.fitnessCenter.to;

public class Additems {
    private Integer Itemid;
    private String ItemName;
    private String BrandName;
    private Double Price;
    private Double Size;
    private Integer Qty;
    private String About;

    private Integer UpdateQty;

    public Additems(Integer itemId, String itemName, String brandName, Double price, Double size, Integer qty, String about) {
        Itemid = itemId;
        ItemName = itemName;
        this.BrandName = brandName;
        this.Price = price;
        this.Size = size;
        this.Qty = qty;
        this.About = about;
    }

    public Additems(Integer itemid, Integer updateQty) {
        Itemid = itemid;
        UpdateQty = updateQty;
    }

    public Integer getItemId() {
        return Itemid;
    }

    public void setItemId(Integer itemId) {
        Itemid = itemId;
    }

    public String getItemName() {
        return ItemName;
    }

    public void setItemName(String itemName) {
        ItemName = itemName;
    }

    public String getBrandName() {
        return BrandName;
    }

    public void setBrandName(String brandName) {
        BrandName = brandName;
    }

    public Double getPrice() {
        return Price;
    }

    public void setPrice(Double price) {
        Price = price;
    }

    public Double getSize() {
        return Size;
    }

    public void setSize(Double size) {
        Size = size;
    }

    public Integer getQty() {
        return Qty;
    }

    public void setQty(Integer qty) {
        Qty = qty;
    }

    public String getAbout() {
        return About;
    }

    public void setAbout(String about) {
        About= about;
    }

    public Integer getUpdateQty() {
        return UpdateQty;
    }

    public void setUpdateQty(Integer updateQty) {
        UpdateQty = updateQty;
    }

    @Override
    public String toString() {
        return "Additems{" +
                "Itemid=" + Itemid +
                ", ItemName='" + ItemName + '\'' +
                ", BrandName='" + BrandName + '\'' +
                ", Price=" + Price +
                ", Size=" + Size +
                ", Qty=" + Qty +
                ", About='" + About + '\'' +
                ", UpdateQty=" + UpdateQty +
                '}';
    }


}


