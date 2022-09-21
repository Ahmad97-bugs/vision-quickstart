package com.google.mlkit.vision.demo.java.posedetector;

import com.google.mlkit.vision.pose.Pose;



public class PoseData {
    private Long time;
    private Pose pose;
    private int instanceCounter;

    public PoseData(Long time, Pose pose, int instance){
        this.time = time;
        this.pose = pose;
        this.instanceCounter = instance;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public Pose getPose() {
        return pose;
    }

    public void setPose(Pose pose) {
        this.pose = pose;
    }

    public int getInstanceCounter() {
        return instanceCounter;
    }

    public void setInstanceCounter(int instanceCounter) {
        this.instanceCounter = instanceCounter;
    }

}
