package com.example.raza.total_logix_customer.DTO;


public class cashVoucherApplied {


    String cashvouchercode;
    String userID;


    public cashVoucherApplied(){

    }

    public cashVoucherApplied(String cashvouchercode, String userID){

        this.cashvouchercode=cashvouchercode;
        this.userID=userID;
    }

    public String getCashvouchercode() {
        return cashvouchercode;
    }

    public void setCashvouchercode(String cashvouchercode) {
        this.cashvouchercode = cashvouchercode;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }
}

