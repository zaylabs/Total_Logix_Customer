package com.example.raza.total_logix_customer.DTO;


import java.util.Date;

public class cashVoucher {


        private String cashvouchercode;

        private Date expire;
        private float cash;



    public cashVoucher(){

        }

        public cashVoucher(String cashvouchercode,Date expire, float cash){


            this.cashvouchercode = cashvouchercode;
            this.expire = expire;
            this.cash = cash;
        }

    public String getCashvouchercode() {
        return cashvouchercode;
    }

    public void setCashvouchercode(String cashvouchercode) {
        this.cashvouchercode = cashvouchercode;
    }

    public Date getExpire() {
        return expire;
    }

    public void setExpire(Date expire) {
        this.expire = expire;
    }

    public float getCash() {
        return cash;
    }

    public void setCash(float cash) {
        this.cash = cash;
    }
}


