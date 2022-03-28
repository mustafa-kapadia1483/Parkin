package com.parkinapp;

import com.google.android.gms.common.util.Strings;

import java.util.ArrayList;
import java.util.List;

public class Renter {
    public ParkingInfo getParkingInfo() {
        return parkingInfo;
    }

    public void setParkingInfo(ParkingInfo parkingInfo) {
        this.parkingInfo = parkingInfo;
    }

    public List<ParkingLots> getParkingLots() {
        return parkingLots;
    }

    public void setParkingLots(List<ParkingLots> parkingLots) {
        this.parkingLots = parkingLots;
    }

    ParkingInfo parkingInfo;
    List<ParkingLots> parkingLots=new ArrayList<>();

}

class ParkingInfo{
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    String name;
}

class ParkingLots{
    Float lat;

    public Float getLat() {
        return lat;
    }

    public void setLat(Float lat) {
        this.lat = lat;
    }

    public Float getLng() {
        return lng;
    }

    public void setLng(Float lng) {
        this.lng = lng;
    }

    Float lng;
}
