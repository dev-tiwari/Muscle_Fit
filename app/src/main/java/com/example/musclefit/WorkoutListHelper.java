package com.example.musclefit;

public class WorkoutListHelper {

    String id;
    String exId;

    public WorkoutListHelper(String id, String exId) {
        this.id = id;
        this.exId = exId;
    }

    public WorkoutListHelper(String exId) {
        this.exId = exId;
    }

    public WorkoutListHelper() {
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
