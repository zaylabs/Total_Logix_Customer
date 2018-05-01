package com.example.raza.total_logix_customer.DTO;

public class driverRating {

    private float oldrating;
    private float newrating;
    private float updatedrating;
    private float totalride;

    private String driverID;
    private String customerID;
    private String uniqueID;

    public driverRating(){}

    public driverRating(float oldrating, float newrating, float updatedrating, float totalride, String driverID, String customerID, String uniqueID){


        this.oldrating = oldrating;
        this.newrating = newrating;
        this.updatedrating = updatedrating;
        this.totalride = totalride;
        this.driverID = driverID;
        this.customerID = customerID;
        this.uniqueID = uniqueID;
    }

    public float getOldrating() {
        return oldrating;
    }

    public void setOldrating(float oldrating) {
        this.oldrating = oldrating;
    }

    public float getNewrating() {
        return newrating;
    }

    public void setNewrating(float newrating) {
        this.newrating = newrating;
    }

    public float getUpdatedrating() {
        return updatedrating;
    }

    public void setUpdatedrating(float updatedrating) {
        this.updatedrating = updatedrating;
    }

    public float getTotalride() {
        return totalride;
    }

    public void setTotalride(float totalride) {
        this.totalride = totalride;
    }

    public String getDriverID() {
        return driverID;
    }

    public void setDriverID(String driverID) {
        this.driverID = driverID;
    }

    public String getCustomerID() {
        return customerID;
    }

    public void setCustomerID(String customerID) {
        this.customerID = customerID;
    }

    public String getUniqueID() {
        return uniqueID;
    }

    public void setUniqueID(String uniqueID) {
        this.uniqueID = uniqueID;
    }
}
