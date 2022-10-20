package com.google.mlkit.vision.demo.java.IS;

import java.util.Date;

/**
 * basic jump class
 * junpID id for jump
 * userID id for user that jump belongs to
 * height measure the height of the jump
 * date the date jump was recorded
 */
public class Jump {
    private String jumpID;
    private String userID;
    private float height;
    private Long date;

    public Jump(String jumpID, String userID, float height, Long date) {
        this.jumpID = jumpID;
        this.userID = userID;
        this.height = height;
        this.date = date;
    }

    public Jump(String uid, double jumpPopup, long date){
        this.userID = uid;
        this.height = (float) jumpPopup;
        this.date = date;
    }

    public String getUserID(){
        return userID;
    }

    public void setUserID(String userID){
        this.userID = userID;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
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
                ", Height=" + height +
                ", date=" + date +
                '}';
    }
}
