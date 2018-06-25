package com.example.carloscosta.camandroid;

public class CAM{

    private int stationId;
    private double timestamp;
    private double latitude;
    private double longitude;
    private double heading;
    private double speed;
    private int acceleration;
    private int yaw_rate;
    private int alert;

    public int getStationId(){
        return stationId;
    }
    public void setStationId(int input){
        this.stationId = input;
    }
    public double getTimestamp(){
        return timestamp;
    }
    public void setTimestamp(double input){
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
    public double getHeading(){
        return heading;
    }
    public void setHeading(double input){
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

    public int getYaw_rate(){
        return yaw_rate;
    }
    public void setYaw_rate(int input){
        this.yaw_rate = input;
    }

    public int getAlert(){
        return alert;
    }
    public void setAlert(int input){
        this.alert = input;
    }

    public String toString(){
        StringBuilder sb= new StringBuilder();
        sb.append("Lat: " + this.latitude + "\n");
        sb.append("Long: " + this.longitude + "\n");
        sb.append("id: " + this.stationId +"\n");

        return sb.toString();

    }
}