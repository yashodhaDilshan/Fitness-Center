package lk.ijse.fitnessCenter.to;

public class Employee {

    private int TrainerId;
    private String TrainerName;
    private String Nic;
    private int age;
    private String gender;
    private int PhoneNo;
    private String address;

    public Employee(int trainerId, String trainerName, String nic, int age, String gender, int phoneNo, String address) {
        TrainerId = trainerId;
        TrainerName = trainerName;
        Nic = nic;
        this.age = age;
        this.gender = gender;
        PhoneNo = phoneNo;
        this.address = address;
    }

    public int getTrainerId() {
        return TrainerId;
    }

    public void setTrainerId(int trainerId) {
        TrainerId = trainerId;
    }

    public String getTrainerName() {
        return TrainerName;
    }

    public void setTrainerName(String trainerName) {
        TrainerName = trainerName;
    }

    public String getNic() {
        return Nic;
    }

    public void setNic(String nic) {
        Nic = nic;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
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
        return "Employee{" +
                "TrainerId=" + TrainerId +
                ", TrainerName='" + TrainerName + '\'' +
                ", Nic='" + Nic + '\'' +
                ", age=" + age +
                ", gender='" + gender + '\'' +
                ", PhoneNo=" + PhoneNo +
                ", address='" + address + '\'' +
                '}';
    }
}
