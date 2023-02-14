package com.example.musclefit.User_Helper_Classes;

public class FavouriteHelper {

    private String id;
    private String exId;
    private String type;

    public FavouriteHelper(String id, String exId, String type) {
        this.id = id;
        this.exId = exId;
        this.type = type;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public FavouriteHelper() {
    }
}
