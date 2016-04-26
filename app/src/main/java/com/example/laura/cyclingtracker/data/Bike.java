package com.example.laura.cyclingtracker.data;

/**
 * Created by laura on 06/04/16.
 */



public class Bike {



        protected String username;
        protected String nickname;
        protected String make;
        protected String model;
        protected double frame_size;
        protected String type;
        protected String color;
        protected String serial_number;


    public Bike(){


    }
    public Bike(String serial_number,String color, double frame_size, String make, String model, String nickname, String type, String username){
        this.serial_number = serial_number;
        this.color = color;
        this.frame_size = frame_size;
        this.make = make;
        this.model = model;
        this.nickname = nickname;
        this.type = type;
        this.username = username;
    }

    public String getSerial_number() {
        return serial_number;
    }
    public void setSerial_number(String serial_number) {
        this.serial_number = serial_number;
    }

    public String getColor() {
        return color;
    }

    public String getType() {
        return type;
    }

    public double getFrame_size() {
        return frame_size;
    }

    public String getModel() {
        return model;
    }

    public String getMake() {
        return make;
    }

    public String getNickname() {
        return nickname;
    }

    public String getUsername() {
        return username;
    }
    @Override
    public String toString() {
        return "Bike{" +
                "username='" + username + '\'' +
                ", nickname='" + nickname + '\'' +
                ", make='" + make + '\'' +
                ", model=" + model +
                ", frame_size=" + frame_size +
                ", type='" + type + '\'' +
                ", color='" + color + '\'' +
                ", serial_number='" + serial_number + '\'' +
                '}';
    }
}
