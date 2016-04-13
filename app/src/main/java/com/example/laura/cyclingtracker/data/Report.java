package com.example.laura.cyclingtracker.data;

/**
 * Created by laura on 11/04/16.
 */
public class Report {




    private String bikename;
    private Double location_lat;
    private Double location_lng;
    private String location_name;
    private String pulse_number;
    private String serial_number;
    private String status;
    private String username;

    public Report(){


    }




    public String getBikename() {
        return bikename;
    }

    public void setBikename(String bikename) {
        this.bikename = bikename;
    }

    public Double getLocation_lat() {
        return location_lat;
    }

    public void setLocation_lat(Double location_lat) {
        this.location_lat = location_lat;
    }

    public Double getLocation_lng() {
        return location_lng;
    }

    public void setLocation_lng(Double location_lng) {
        this.location_lng = location_lng;
    }

    public String getLocation_name() {
        return location_name;
    }

    public void setLocation_name(String location_name) {
        this.location_name = location_name;
    }

    public String getPulse_number() {
        return pulse_number;
    }

    public void setPulse_number(String pulse_number) {
        this.pulse_number = pulse_number;
    }

    public String getSerial_number() {
        return serial_number;
    }

    public void setSerial_number(String serial_number) {
        this.serial_number = serial_number;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }



}
