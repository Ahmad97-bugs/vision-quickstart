/*
 * Copyright 2020 Google LLC. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.mlkit.vision.demo.java.posedetector.classification;

import com.google.mlkit.vision.demo.java.posedetector.PoseData;
import com.google.mlkit.vision.pose.Pose;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Counts reps for the give class.
 */
public class RepetitionCounter{
    // These thresholds can be tuned in conjunction with the Top K values in {@link PoseClassifier}.
    // The default Top K value is 10 so the range here is [0-10].
    private static final float DEFAULT_ENTER_THRESHOLD = 6f;
    private static final float DEFAULT_EXIT_THRESHOLD = 4f;

    private final String className;
    private final float enterThreshold;
    private final float exitThreshold;

    private int numRepeats;
    private boolean poseEntered;
    private List<PoseData> pdList;
    private boolean isRecording;


    public RepetitionCounter(String className){
        this(className, DEFAULT_ENTER_THRESHOLD, DEFAULT_EXIT_THRESHOLD);
    }

    public RepetitionCounter(String className, float enterThreshold, float exitThreshold){
        pdList = new ArrayList<PoseData>();
        this.className = className;
        this.enterThreshold = enterThreshold;
        this.exitThreshold = exitThreshold;
        this.isRecording = false;
        numRepeats = 0;
        poseEntered = false;
    }

    /**
     * Adds a new Pose classification result and updates reps for given class.
     *
     * @param classificationResult {link ClassificationResult} of class to confidence values.
     * @return number of reps.
     */
    public int addClassificationResult(ClassificationResult classificationResult, Pose pose){
        float poseConfidence = classificationResult.getClassConfidence(className);
        if(poseConfidence > 6.0 && isRecording)
            pdList.add(new PoseData(new Date().getTime(), pose));
        if(! poseEntered){
            poseEntered = poseConfidence > enterThreshold;
            return numRepeats;
        }

        if(poseConfidence < exitThreshold){
            numRepeats++;
            poseEntered = false;
        }

        return numRepeats;
    }

    public String getClassName(){
        return className;
    }

    public int getNumRepeats(){
        return numRepeats;
    }

    public void setPdList(List<PoseData> pdList){
        this.pdList = pdList;
    }

    public List<PoseData> getPdList(){
        return pdList;
    }

    public boolean isRecording(){
        return isRecording;
    }

    public void setRecording(boolean recording){
        isRecording = recording;
    }
}
