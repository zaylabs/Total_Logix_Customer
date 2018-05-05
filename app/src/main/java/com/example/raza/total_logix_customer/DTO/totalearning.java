package com.example.raza.total_logix_customer.DTO;

import java.util.Date;

public class totalearning {

    private float totalearning;
    private float totalrides;
    private float totallogixearning;
    private float moneyinwallets;
    private Date dateupdated;


    public totalearning(){}

    public totalearning(float totalearning, float totalrides, float totallogixearning, float moneyinwallets, Date dateupdated){


        this.totalearning = totalearning;
        this.totalrides = totalrides;
        this.totallogixearning = totallogixearning;
        this.moneyinwallets = moneyinwallets;
        this.dateupdated = dateupdated;
    }

    public float getTotalearning() {
        return totalearning;
    }

    public void setTotalearning(float totalearning) {
        this.totalearning = totalearning;
    }

    public float getTotalrides() {
        return totalrides;
    }

    public void setTotalrides(float totalrides) {
        this.totalrides = totalrides;
    }

    public float getTotallogixearning() {
        return totallogixearning;
    }

    public void setTotallogixearning(float totallogixearning) {
        this.totallogixearning = totallogixearning;
    }

    public Date getDateupdated() {
        return dateupdated;
    }

    public void setDateupdated(Date dateupdated) {
        this.dateupdated = dateupdated;
    }

    public float getMoneyinwallets() {
        return moneyinwallets;
    }

    public void setMoneyinwallets(float moneyinwallets) {
        this.moneyinwallets = moneyinwallets;
    }
}
