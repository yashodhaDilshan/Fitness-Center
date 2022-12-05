package lk.ijse.fitnessCenter.to;

public class EmployeeSalary {
    private int SaleryId;
    private int TrainerId;
    private String Name;
    private double salary;
    private double Allowance;
    private double Total;
    private String PaymentDate;
    private String Status;

    public EmployeeSalary(int saleryId, int trainerId, String name, double salary, double allowance, double total, String paymentDate, String status) {
        SaleryId = saleryId;
        TrainerId = trainerId;
        Name = name;
        this.salary = salary;
        Allowance = allowance;
        Total = total;
        PaymentDate = paymentDate;
        Status = status;
    }

    public int getSaleryId() {
        return SaleryId;
    }

    public void setSaleryId(int saleryId) {
        SaleryId = saleryId;
    }

    public int getTrainerId() {
        return TrainerId;
    }

    public void setTrainerId(int trainerId) {
        TrainerId = trainerId;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public double getAllowance() {
        return Allowance;
    }

    public void setAllowance(double allowance) {
        Allowance = allowance;
    }

    public double getTotal() {
        return Total;
    }

    public void setTotal(double total) {
        Total = total;
    }

    public String getPaymentDate() {
        return PaymentDate;
    }

    public void setPaymentDate(String paymentDate) {
        PaymentDate = paymentDate;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    @Override
    public String toString() {
        return "EmployeeSalary{" +
                "SaleryId=" + SaleryId +
                ", TrainerId=" + TrainerId +
                ", Name='" + Name + '\'' +
                ", salary=" + salary +
                ", Allowance=" + Allowance +
                ", Total=" + Total +
                ", PaymentDate='" + PaymentDate + '\'' +
                ", Status='" + Status + '\'' +
                '}';
    }
}

