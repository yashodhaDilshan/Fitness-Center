package lk.ijse.fitnessCenter.to;

public class MembershipPayment {

    private Integer MemberId;
    private String Type;
    private String PaymentDate;
    private String ExpireDate;

    public MembershipPayment(Integer memberId, String type, String paymentDate, String expireDate) {
        MemberId = memberId;
        Type = type;
        PaymentDate = paymentDate;
        ExpireDate = expireDate;
    }


    public Integer getMemberId() {
        return MemberId;
    }

    public void setMemberId(Integer memberId) {
        MemberId = memberId;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public String getPaymentDate() {
        return PaymentDate;
    }

    public void setPaymentDate(String paymentDate) {
        PaymentDate = paymentDate;
    }

    public String getExpireDate() {
        return ExpireDate;
    }

    public void setExpireDate(String expireDate) {
        ExpireDate = expireDate;
    }

    @Override
    public String toString() {
        return "MembershipPayment{" +
                "MemberId=" + MemberId +
                ", Type='" + Type + '\'' +
                ", PaymentDate='" + PaymentDate + '\'' +
                ", ExpireDate='" + ExpireDate + '\'' +
                '}';
    }
}
