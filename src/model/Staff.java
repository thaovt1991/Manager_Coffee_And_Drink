package model;

import java.io.Serializable;
import java.text.DecimalFormat;

public class Staff implements Serializable {
    public String idStaff;
    public String fullName;
    public String gender;
    public String dateOfBirth;
    public String identityCard;
    public String numberPhone;
    public String address;
    public long payStaff;
    public String other;

    public Staff() {
    }

    ;

    public Staff(String idStaff, String fullName, String gender, String dateOfBirth, String identityCard, String numberPhone, String address, long payStaff, String other) {
        this.idStaff = idStaff;
        this.fullName = fullName;
        this.gender = gender;
        this.numberPhone = numberPhone;
        this.dateOfBirth = dateOfBirth;
        this.identityCard = identityCard;
        this.address = address;
        this.payStaff = payStaff;
        this.other = other;
    }


    public String getIdStaff() {
        return idStaff;
    }

    public void setIdStaff(String idStaff) {
        this.idStaff = idStaff;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getNumberPhone() {
        return numberPhone;
    }

    public void setNumberPhone(String numberPhone) {
        this.numberPhone = numberPhone;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getIdentityCard() {
        return identityCard;
    }

    public void setIdentityCard(String identityCard) {
        this.identityCard = identityCard;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public long getPayStaff() {
        return payStaff;
    }

    public void setPayStaff(long payStaff) {
        this.payStaff = payStaff;
    }

    public String getOther() {
        return other;
    }

    public void setOther(String other) {
        this.other = other;
    }

    @Override
    public String toString() {
        DecimalFormat formater = new DecimalFormat("###,###,###");
        return "Nhân viên - ID : '" + getIdStaff() + "' ; Họ và tên : " + getFullName() +" ; Giới tính : " +getGender() + " ; Ngày sinh : " + getDateOfBirth() +
                " ; CMND : " + getIdentityCard() + " ;  Số điện thoại : " + getNumberPhone() + " ; Đia chỉ : " + getAddress() +
                " ; Lương : " + formater.format(getPayStaff()) + " VND ; Thông tin khác : " + getOther();
    }
}
