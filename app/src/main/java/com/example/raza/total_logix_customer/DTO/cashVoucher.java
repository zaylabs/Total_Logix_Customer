package com.example.raza.total_logix_customer.DTO;


import java.util.Date;

public class cashVoucher {




        private Date expiredate;
        private float cash;



    public cashVoucher(){

        }

        public cashVoucher(Date expiredate, float cash){



            this.expiredate = expiredate;
            this.cash = cash;
        }

    public Date getExpiredate() {
        return expiredate;
    }

    public void setExpiredate(Date expiredate) {
        this.expiredate = expiredate;
    }

    public float getCash() {
        return cash;
    }

    public void setCash(float cash) {
        this.cash = cash;
    }
}


