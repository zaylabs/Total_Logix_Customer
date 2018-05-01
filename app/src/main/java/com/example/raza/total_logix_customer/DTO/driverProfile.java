package com.example.raza.total_logix_customer.DTO;

import java.util.Date;

public class driverProfile {


    private String name;
    private String cnic;
    private String phone;
    private String vt;
    private String reg_number;
    private String displaypic;
    private Date currentDate;
    private float totalrides;
    private float stars;



    public driverProfile() {
    }


    public driverProfile(String name, String phone, String cnic, String reg_number, String vt, Date currentDate, String displaypic, float totalrides, float stars) {

        this.name = name;
        this.cnic = cnic;
        this.phone = phone;
        this.vt = vt;
        this.reg_number = reg_number;
        this.currentDate = currentDate;
        this.displaypic=displaypic;
        this.totalrides = totalrides;
        this.stars=stars;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCnic() {
        return cnic;
    }

    public void setCnic(String cnic) {
        this.cnic = cnic;
    }
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getVt() {
        return vt;
    }

    public void setVt(String vt) {
        this.vt = vt;
    }

   public String getReg_number() {
        return reg_number;
    }

    public void setReg_number(String reg_number) {
        this.reg_number = reg_number;
    }

    public Date getCurrentDate() {
        return currentDate;
    }

    public void setCurrentDate(Date currentDate) {
        this.currentDate = currentDate;
    }

    public String getDisplaypic() {
        return displaypic;
    }

    public void setDisplaypic(String name) {
        this.displaypic = displaypic;
    }

    public float getStars() {
        return stars;
    }

    public void setStars(float stars) {
        this.stars = stars;
    }

    public float getTotalrides() {
        return totalrides;
    }

    public void setTotalrides(float totalrides) {
        this.totalrides = totalrides;
    }
}



