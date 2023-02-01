package com.example.musclefit;

public class ShowingExerciseStepsModel {
    String steps, id;

    public ShowingExerciseStepsModel(String steps, String id) {
        this.steps = steps;
        this.id = id;
    }

    public ShowingExerciseStepsModel() {
    }

    public String getSteps() {
        return steps;
    }

    public void setSteps(String steps) {
        this.steps = steps;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
