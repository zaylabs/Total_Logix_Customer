package com.example.raza.total_logix_customer.DTO;


import java.util.Date;

public class cashVoucherApplied {


    Date dateapplied;



    public cashVoucherApplied(){

    }

    public cashVoucherApplied(Date dateapplied){

        this.dateapplied=dateapplied;

    }

    public Date getDateapplied() {
        return dateapplied;
    }

    public void setDateapplied(Date dateapplied) {
        this.dateapplied = dateapplied;
    }
}

