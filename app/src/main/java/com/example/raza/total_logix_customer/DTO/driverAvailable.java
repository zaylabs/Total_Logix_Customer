package com.example.raza.total_logix_customer.DTO;

import com.google.firebase.firestore.GeoPoint;

public class driverAvailable {

    private GeoPoint driverLocation;

    public driverAvailable(){

    }

    public driverAvailable(GeoPoint driverLocation){

        this.driverLocation = driverLocation;
    }

    public void setDriverLocation(GeoPoint driverLocation) {
        this.driverLocation = driverLocation;
    }


    public GeoPoint getDriverLocation() {
        return driverLocation;
    }


}
