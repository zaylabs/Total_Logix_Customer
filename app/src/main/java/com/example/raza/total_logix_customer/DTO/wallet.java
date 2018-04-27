package com.example.raza.total_logix_customer.DTO;

import java.util.Date;

public class wallet {

    float amount;
    Date dateupdated;


    public wallet(){

    }

    public wallet(float amount, Date dateupdated){


        this.amount = amount;
        this.dateupdated = dateupdated;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public Date getDateupdated() {
        return dateupdated;
    }

    public void setDateupdated(Date dateupdated) {
        this.dateupdated = dateupdated;
    }
}
