package in.techware.lataxidriverapp.model;


import android.support.annotation.NonNull;

import com.google.android.gms.maps.model.LatLng;

public class PlaceBeanBak extends BaseBean implements Comparable {

//    10.015861  76.341867  10.107570  76.345662

    private int id;
    private String address;
    private String latitude;
    private String longitude;
    private String name;
    //    private Place place;
    private LatLng latLng;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

//    public Place getPlace() {
//        return place;
//    }

//    public void setPlace(Place place) {
//        this.place = place;
//    }

    public LatLng getLatLng() {
        return latLng;
    }

    public void setLatLng(LatLng latLng) {
        this.latLng = latLng;
    }

    public double getDLatitude() {
        try {
            return Double.parseDouble(latitude);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return 0.0;
        }
    }


    public double getDLongitude() {
        try {
            return Double.parseDouble(longitude);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return 0.0;
        }
    }


    @Override
    public int compareTo(@NonNull Object obj) {
        PlaceBeanBak bean = (PlaceBeanBak) obj;
        if (id == bean.getId())
            return 0;
        else if (id > bean.getId())
            return 1;
        else
            return -1;
    }
}
