package com.example.raza.total_logix_customer.DTO;

import com.google.android.gms.maps.model.LatLng;

public class homeinfoPass {

    String pickup;
    String drop;
    LatLng pickuplatlng;
    LatLng droplatlng;
    String distance;
  //  String cargodiscription;
    String vt;
   // String weight;
   // String boxes;
    String driverloading;
    String estimateFare;

public homeinfoPass(){

}

public homeinfoPass(String pickup,String drop, LatLng pickuplatlng,LatLng droplatlng,String distance,/*String cargodiscription, String weight,*/ String vt,/*String boxes,*/ String driverloading, String estimateFare){

    this.pickup = pickup;
    this.drop = drop;
    this.pickuplatlng = pickuplatlng;
    this.droplatlng = droplatlng;
    this.distance = distance;
   /* this.cargodiscription=cargodiscription;
    this.weight = weight;
   */ this.vt = vt;
   // this.boxes = boxes;
    this.driverloading=driverloading;
    this.estimateFare=estimateFare;

}

    public String getPickup() {
        return pickup;
    }

    public void setPickup(String pickup) {
        this.pickup = pickup;
    }

    public String getDrop() {
        return drop;
    }

    public void setDrop(String drop) {
        this.drop = drop;
    }

    public LatLng getPickuplatlng() {
        return pickuplatlng;
    }

    public void setPickuplatlng(LatLng pickuplatlng) {
        this.pickuplatlng = pickuplatlng;
    }

    public LatLng getDroplatlng() {
        return droplatlng;
    }

    public void setDroplatlng(LatLng droplatlng) {
        this.droplatlng = droplatlng;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    /*public String getCargodiscription() {
        return cargodiscription;
    }

    public void setCargodiscription(String cargodiscription) {
        this.cargodiscription = cargodiscription;
    }*/

    public String getVt() {
        return vt;
    }

    public void setVt(String vt) {
        this.vt = vt;
    }

  /*  public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getBoxes() {
        return boxes;
    }

    public void setBoxes(String boxes) {
        this.boxes = boxes;
    }
*/
    public String getDriverloading() {
        return driverloading;
    }

    public void setDriverloading(String driverloading) {
        this.driverloading = driverloading;
    }

    public String getEstimateFare() {
        return estimateFare;
    }

    public void setEstimateFare(String estimateFare) {
        this.estimateFare = estimateFare;
    }
}