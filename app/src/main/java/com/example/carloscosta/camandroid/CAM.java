package com.example.carloscosta.camandroid;

public class CAM{

    private int stationId;
    private int timestamp;
    private double latitude;
    private double longitude;
    private int heading;
    private double speed;
    private int acceleration;

    public int getStationId(){
        return stationId;
    }
    public void setStationId(int input){
        this.stationId = input;
    }
    public int getTimestamp(){
        return timestamp;
    }
    public void setTimestamp(int input){
        this.timestamp = input;
    }
    public double getLatitude(){
        return latitude;
    }
    public void setLatitude(double input){
        this.latitude = input;
    }
    public double getLongitude(){
        return longitude;
    }
    public void setLongitude(double input){
        this.longitude = input;
    }
    public int getHeading(){
        return heading;
    }
    public void setHeading(int input){
        this.heading = input;
    }
    public double getSpeed(){
        return speed;
    }
    public void setSpeed(double input){
        this.speed = input;
    }
    public int getAcceleration(){
        return acceleration;
    }
    public void setAcceleration(int input){
        this.acceleration = input;
    }

    public String toString(){
        StringBuilder sb= new StringBuilder();
        sb.append("Lat: " + this.latitude + "\n");
        sb.append("Long: " + this.longitude + "\n");
        sb.append("id: " + this.stationId +"\n");

        return sb.toString();

    }
}