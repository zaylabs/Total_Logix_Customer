package com.example.raza.total_logix_customer.DTO;

public class ratingUpdate {

    private float totalrides;
    private float stars;
public ratingUpdate(){}
public ratingUpdate(float totalrides, float stars){


    this.totalrides = totalrides;
    this.stars = stars;
}

    public float getTotalrides() {
        return totalrides;
    }

    public void setTotalrides(float totalrides) {
        this.totalrides = totalrides;
    }

    public float getStars() {
        return stars;
    }

    public void setStars(float stars) {
        this.stars = stars;
    }
}
