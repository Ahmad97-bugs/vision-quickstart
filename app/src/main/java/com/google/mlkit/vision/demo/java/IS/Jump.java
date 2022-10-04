package com.google.mlkit.vision.demo.java.IS;

import java.util.Date;

public class Jump {
    private String jumpID;
    private String userID;
    private float Height;
    private Long date;

    public Jump(String jumpID, String userID, float height, Long date) {
        this.jumpID = jumpID;
        this.userID = userID;
        this.Height = height;
        this.date = date;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public float getHeight() {
        return Height;
    }

    public void setHeight(float height) {
        Height = height;
    }

    public Long getDate() {
        return date;
    }

    public void setDate(Long date) {
        this.date = date;
    }

    private Date castToDate(Long date){
        return new Date(date);
    }

    public String getJumpID(){
        return jumpID;
    }

    public void setJumpID(String jumpID){
        this.jumpID = jumpID;
    }

    @Override
    public String toString() {
        return "Jump{" +
                "userID=" + userID +
                ", Height=" + Height +
                ", date=" + date +
                '}';
    }
}
