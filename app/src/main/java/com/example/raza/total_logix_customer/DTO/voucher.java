package com.example.raza.total_logix_customer.DTO;

import java.util.Date;

public class voucher {


    String vouchercode;
    Date expire;

    float percentage;


    public voucher(){

    }

    public voucher(String vouchercode,Date expire, float percentage){

        this.vouchercode = vouchercode;
        this.expire = expire;
        this.percentage = percentage;
    }

    public String getVouchercode() {
        return vouchercode;
    }

    public void setVouchercode(String vouchercode) {
        this.vouchercode = vouchercode;
    }

    public Date getExpire() {
        return expire;
    }

    public void setExpire(Date expire) {
        this.expire = expire;
    }


    public float getPercentage() {
        return percentage;
    }

    public void setPercentage(float percentage) {
        this.percentage = percentage;
    }
}
