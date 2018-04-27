package com.example.raza.total_logix_customer.DTO;

import java.util.Date;



public class transactionhistory {

    float amount;
    Date dateupdated;
    String customerID;
    String source;
    float oldbalance;
    float currentbalance;



    public transactionhistory(){

    }

    public transactionhistory(float amount, Date dateupdated, String customerID, String source, float oldbalance, float currentbalance){


        this.amount = amount;
        this.dateupdated = dateupdated;
        this.customerID = customerID;
        this.source = source;
        this.oldbalance = oldbalance;
        this.currentbalance = currentbalance;
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

    public String getCustomerID() {
        return customerID;
    }

    public void setCustomerID(String customerID) {
        this.customerID = customerID;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public float getOldbalance() {
        return oldbalance;
    }

    public void setOldbalance(float oldbalance) {
        this.oldbalance = oldbalance;
    }

    public float getCurrentbalance() {
        return currentbalance;
    }

    public void setCurrentbalance(float currentbalance) {
        this.currentbalance = currentbalance;
    }
}
