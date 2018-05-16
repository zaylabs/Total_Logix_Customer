package com.example.raza.total_logix_customer.DTO;

import android.support.annotation.NonNull;

import java.util.Comparator;

public class dropLocationDTO {

    private String address;
    private String addressorder;
    private float distance;
    private String stringdistance;

    public dropLocationDTO(){

    }
    public dropLocationDTO(String address,String addressorder, float distance, String stringdistance){


        this.address = address;
        this.addressorder = addressorder;
        this.distance = distance;
        this.stringdistance = stringdistance;
    }

   public  Comparator<dropLocationDTO>BY_Distance = new Comparator<dropLocationDTO>() {
       @Override
       public int compare(dropLocationDTO o1, dropLocationDTO o2) {



//           return o1.stringdistance.compareTo(o2.stringdistance);
           if (o1.getDistance() < o2.getDistance()) {
               return -1;
           } else if (o1.getDistance() > o2.getDistance()) {
               return 1;
           } else {
               return 0;
           }
       }
   };

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddressorder() {
        return addressorder;
    }

    public void setAddressorder(String addressorder) {
        this.addressorder = addressorder;
    }

    public float getDistance() {
        return distance;
    }

    public void setDistance(float distance) {
        this.distance = distance;
    }

    public String getStringdistance() {
        return stringdistance;
    }

    public void setStringdistance(String stringdistance) {
        this.stringdistance = stringdistance;
    }
}
