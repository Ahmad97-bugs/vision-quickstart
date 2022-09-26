package com.google.mlkit.vision.demo.java.IS;

import java.util.Date;

public class Jump {
    private int userID;
    private float Height;
    private Long date;

    public Jump(int userID, float height, Long date) {
        this.userID = userID;
        Height = height;
        this.date = date;
    }

    public Jump() {

    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
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

    @Override
    public String toString() {
        return "Jump{" +
                "userID=" + userID +
                ", Height=" + Height +
                ", date=" + date +
                '}';
    }
}