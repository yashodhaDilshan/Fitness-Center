package lk.ijse.fitnessCenter.to;

public class Member {
    private int Id;
    private String Name;
    private String Nic;
    private int Phoneno;
    private int age;
    private double weight;
    private double height;
    private String gender;
    private String goal;

    private  String address;

    public Member(int id, String name, String nic, int phoneno, int age, double weight, double height, String gender, String goal, String address) {
        Id = id;
        Name = name;
        Nic = nic;
        Phoneno = phoneno;
        this.age = age;
        this.weight = weight;
        this.height = height;
        this.gender = gender;
        this.goal = goal;
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

    public int getPhoneno() {
        return Phoneno;
    }

    public void setPhoneno(int phoneno) {
        Phoneno = phoneno;
    }

    public String getAge() {
        return String.valueOf(age);
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getWeight() {
        return String.valueOf(weight);
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getGoal() {
        return goal;
    }

    public void setGoal(String goal) {
        this.goal = goal;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "member{" +
                "Id='" + Id + '\'' +
                ", Name='" + Name + '\'' +
                ", Nic='" + Nic + '\'' +
                ", Phoneno='" + Phoneno + '\'' +
                ", age=" + age +
                ", weight=" + weight +
                ", height=" + height +
                ", gender='" + gender + '\'' +
                ", goal='" + goal + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
