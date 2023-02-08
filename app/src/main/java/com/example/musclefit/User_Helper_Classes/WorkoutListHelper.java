package com.example.musclefit.User_Helper_Classes;

public class WorkoutListHelper {

    String id;
    String exId;
    String exerciseType;

    public WorkoutListHelper(String id, String exId, String exerciseType) {
        this.id = id;
        this.exId = exId;
        this.exerciseType = exerciseType;
    }

    public WorkoutListHelper(String exId) {
        this.exId = exId;
    }

    public WorkoutListHelper() {
    }

    public String getExerciseType() {
        return exerciseType;
    }

    public void setExerciseType(String exerciseType) {
        this.exerciseType = exerciseType;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getExId() {
        return exId;
    }

    public void setExId(String exId) {
        this.exId = exId;
    }
}
