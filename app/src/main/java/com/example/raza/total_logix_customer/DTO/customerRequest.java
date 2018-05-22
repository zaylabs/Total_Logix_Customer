package com.example.raza.total_logix_customer.DTO;

import com.google.firebase.firestore.GeoPoint;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class customerRequest {

    private String name;
    private String phone;
    private GeoPoint pickup;
    private GeoPoint drop;
    private Date date;
    private String CID;
    private String VT;
    private String weight;
    private String boxes;
    private String description;
    private String driverloading;
    private float ridedistance;
    private String pickupaddress;
    private String dropaddress;
    private String estFare;
    private String uniqueID;
    private float stars;
    private String gatepass;
    private Date arriveddate;
    private String droplocationUniqueID;
    private List<dropLocationDTO> droplist;
    customerRequest(){

    }

    public customerRequest(String name, GeoPoint pickup, GeoPoint drop, String phone, Date date, String CID, String VT, String weight, String boxes , String description, String driverloading, float ridedistance, String pickupaddress, String dropaddress, String estFare, String uniqueID, float stars, String gatepass, Date arriveddate,String droplocationUniqueID){

        this.name = name;
        this.pickup = pickup;
        this.drop = drop;
        this.phone = phone;
        this.date =date;
        this.CID = CID;
        this.VT = VT;
        this.weight=weight;
        this.boxes=boxes;
        this.description=description;
        this.driverloading=driverloading;
        this.ridedistance=ridedistance;
        this.pickupaddress=pickupaddress;
        this.dropaddress=dropaddress;
        this.estFare=estFare;
        this.uniqueID = uniqueID;
        this.stars=stars;
        this.gatepass=gatepass;
        this.arriveddate=arriveddate;
        this.droplocationUniqueID = droplocationUniqueID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public GeoPoint getPickup() {
        return pickup;
    }

    public void setPickup(GeoPoint pickup) {
        this.pickup = pickup;
    }

    public GeoPoint getDrop() {
        return drop;
    }

    public void setDrop(GeoPoint drop) {
        this.drop = drop;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getCID() {
        return CID;
    }

    public void setCID(String CID) {
        this.CID = CID;
    }

    public String getVT() {
        return VT;
    }

    public void setVT(String VT) {
        this.VT = VT;
    }

    public String getWeight() {
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDriverloading() {
        return driverloading;
    }

    public void setDriverloading(String driverloading) {
        this.driverloading = driverloading;
    }

    public float getRidedistance() {
        return ridedistance;
    }

    public void setRidedistance(float ridedistance) {
        this.ridedistance = ridedistance;
    }

    public String getPickupaddress() {
        return pickupaddress;
    }

    public void setPickupaddress(String pickupaddress) {
        this.pickupaddress = pickupaddress;
    }

    public String getDropaddress() {
        return dropaddress;
    }

    public void setDropaddress(String dropaddress) {
        this.dropaddress = dropaddress;
    }

    public String getEstFare() {
        return estFare;
    }

    public void setEstFare(String estFare) {
        this.estFare = estFare;
    }

    public String getUniqueID() {
        return uniqueID;
    }

    public void setUniqueID(String uniqueID) {
        this.uniqueID = uniqueID;
    }

    public float getStars() {
        return stars;
    }

    public void setStars(float stars) {
        this.stars = stars;
    }

    public String getGatepass() {
        return gatepass;
    }

    public void setGatepass(String gatepass) {
        this.gatepass = gatepass;
    }

    public Date getArriveddate() {
        return arriveddate;
    }

    public void setArriveddate(Date arriveddate) {
        this.arriveddate = arriveddate;
    }

    public String getDroplocationUniqueID() {
        return droplocationUniqueID;
    }

    public void setDroplocationUniqueID(String droplocationUniqueID) {
        this.droplocationUniqueID = droplocationUniqueID;
    }
}
