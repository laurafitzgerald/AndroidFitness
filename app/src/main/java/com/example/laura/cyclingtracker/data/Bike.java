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


    public String getSerial_number() {
        return serial_number;
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
