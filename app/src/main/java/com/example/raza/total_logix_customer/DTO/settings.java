package com.example.raza.total_logix_customer.DTO;

import java.util.Date;

public class settings {
    private int suzukirate;
    private int riksharate;
    private int suzukibase;
    private int rikshabase;
    private int driverloadingRate;
    private int totallogixsharepercent;
    private int dropstoprates;
    private int waitingrate;
    private Date dateupdated;


    public settings(){}

    public settings(int suzukirate, int riksharate, int suzukibase, int rikshabase, int driverloadingRate, int totallogixsharepercent, int dropstoprates, int waitingrate, Date dateupdated){


        this.suzukirate = suzukirate;
        this.riksharate = riksharate;
        this.suzukibase = suzukibase;
        this.rikshabase = rikshabase;
        this.driverloadingRate = driverloadingRate;
        this.totallogixsharepercent = totallogixsharepercent;
        this.dropstoprates = dropstoprates;
        this.waitingrate = waitingrate;
        this.dateupdated = dateupdated;
    }

    public int getSuzukirate() {
        return suzukirate;
    }

    public void setSuzukirate(int suzukirate) {
        this.suzukirate = suzukirate;
    }

    public int getRiksharate() {
        return riksharate;
    }

    public void setRiksharate(int riksharate) {
        this.riksharate = riksharate;
    }

    public int getSuzukibase() {
        return suzukibase;
    }

    public void setSuzukibase(int suzukibase) {
        this.suzukibase = suzukibase;
    }

    public int getRikshabase() {
        return rikshabase;
    }

    public void setRikshabase(int rikshabase) {
        this.rikshabase = rikshabase;
    }

    public int getDriverloadingRate() {
        return driverloadingRate;
    }

    public void setDriverloadingRate(int driverloadingRate) {
        this.driverloadingRate = driverloadingRate;
    }

    public int getTotallogixsharepercent() {
        return totallogixsharepercent;
    }

    public void setTotallogixsharepercent(int totallogixsharepercent) {
        this.totallogixsharepercent = totallogixsharepercent;
    }

    public int getWaitingrate() {
        return waitingrate;
    }

    public void setWaitingrate(int waitingrate) {
        this.waitingrate = waitingrate;
    }

    public Date getDateupdated() {
        return dateupdated;
    }

    public void setDateupdated(Date dateupdated) {
        this.dateupdated = dateupdated;
    }

    public int getDropstoprates() {
        return dropstoprates;
    }

    public void setDropstoprates(int dropstoprates) {
        this.dropstoprates = dropstoprates;
    }
}


