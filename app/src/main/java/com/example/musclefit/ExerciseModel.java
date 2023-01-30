package com.example.musclefit;

public class ExerciseModel {

    private String exerciseId, exerciseName, exerciseImage, timeTaken, state, exerciseType;

    public ExerciseModel(String exerciseId, String exerciseName, String exerciseImage, String timeTaken, String state, String exerciseType) {
        this.exerciseId = exerciseId;
        this.exerciseName = exerciseName;
        this.exerciseImage = exerciseImage;
        this.timeTaken = timeTaken;
        this.state = state;
        this.exerciseType = exerciseType;
    }

    public ExerciseModel() {
    }

    public String getExerciseType() {
        return exerciseType;
    }

    public void setExerciseType(String exerciseType) {
        this.exerciseType = exerciseType;
    }

    public String getExerciseId() {
        return exerciseId;
    }

    public void setExerciseId(String exerciseId) {
        this.exerciseId = exerciseId;
    }

    public String getExerciseName() {
        return exerciseName;
    }

    public void setExerciseName(String exerciseName) {
        this.exerciseName = exerciseName;
    }

    public String getExerciseImage() {
        return exerciseImage;
    }

    public void setExerciseImage(String exerciseImage) {
        this.exerciseImage = exerciseImage;
    }

    public String getTimeTaken() {
        return timeTaken;
    }

    public void setTimeTaken(String timeTaken) {
        this.timeTaken = timeTaken;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
