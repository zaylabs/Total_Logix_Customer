package com.example.raza.total_logix_customer.DTO;

import android.support.annotation.NonNull;

import com.google.android.gms.maps.model.LatLng;

import java.util.Comparator;

public class dropLocationDTO {

    private String address;
    private String cid;
    private String uniqueid;
    private Float distance;
    private String stringdistance;
    private LatLng droplocation;
    public dropLocationDTO(){

    }
    public dropLocationDTO(String address,String cid,String uniqueid, Float distance, String stringdistance,LatLng droplocation){


        this.address = address;
        this.cid = cid;
        this.uniqueid = uniqueid;

        this.distance = distance;
        this.stringdistance = stringdistance;
        this.droplocation = droplocation;
    }

   public static final   Comparator<dropLocationDTO>BY_Distance = new Comparator<dropLocationDTO>() {
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

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getUniqueid() {
        return uniqueid;
    }

    public void setUniqueid(String uniqueid) {
        this.uniqueid = uniqueid;
    }

    public Float getDistance() {
        return distance;
    }

    public void setDistance(Float distance) {
        this.distance = distance;
    }

    public String getStringdistance() {
        return stringdistance;
    }

    public void setStringdistance(String stringdistance) {
        this.stringdistance = stringdistance;
    }

    public LatLng getDroplocation() {
        return droplocation;
    }

    public void setDroplocation(LatLng droplocation) {
        this.droplocation = droplocation;
    }
}
