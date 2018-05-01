package com.example.raza.total_logix_customer.DTO;

import java.util.Date;

public class userProfile {

    private String name;
    private String email;
    private String dpURL;
    private String phone;
    private Date createDate;
    private Date updateDate;
    private float totalrides;
    private float stars;


    public userProfile(){

    }
    public userProfile(String name, String email, String dpURL, String phone, Date createDate, Date updateDate,float totalrides, float stars){

        this.name = name;
        this.email = email;
        this.dpURL = dpURL;
        this.phone = phone;
        this.createDate = createDate;
        this.updateDate = updateDate;
        this.totalrides = totalrides;
        this.stars=stars;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDpURL() {
        return dpURL;
    }

    public void setDpURL(String dpURL) {
        this.dpURL = dpURL;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
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