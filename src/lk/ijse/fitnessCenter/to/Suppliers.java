package lk.ijse.fitnessCenter.to;

public class Suppliers {

    private int Id;
    private String Name;
    private String Nic;
    private int PhoneNo;
    private  String address;

    public Suppliers(int id, String name, String nic, int phoneNo, String address) {
        Id = id;
        Name = name;
        Nic = nic;
        PhoneNo = phoneNo;
        this.address = address;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getNic() {
        return Nic;
    }

    public void setNic(String nic) {
        Nic = nic;
    }

    public int getPhoneNo() {
        return PhoneNo;
    }

    public void setPhoneNo(int phoneNo) {
        PhoneNo = phoneNo;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "Suppliers{" +
                "Id=" + Id +
                ", Name='" + Name + '\'' +
                ", Nic='" + Nic + '\'' +
                ", PhoneNo=" + PhoneNo +
                ", address='" + address + '\'' +
                '}';
    }
}
