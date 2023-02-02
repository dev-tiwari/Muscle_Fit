package com.example.musclefit.User_Helper_Classes;

public class FavouriteHelper {

    private String id;
    private String exId;

    public FavouriteHelper(String id, String exId) {
        this.id = id;
        this.exId = exId;
    }

    public FavouriteHelper(String exId) {
        this.exId = exId;
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

    public FavouriteHelper() {
    }
}
