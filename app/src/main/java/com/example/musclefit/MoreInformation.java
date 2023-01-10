package com.example.musclefit;

public class MoreInformation {

    private String height;
    private String weight;
    private String age;
    private String gender;
    private String BMI;

    public MoreInformation(String height, String weight, String age, String gender, String BMI) {
        this.height = height;
        this.weight = weight;
        this.age = age;
        this.gender = gender;
        this.BMI = BMI;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getBMI() {
        return BMI;
    }

    public void setBMI(String BMI) {
        this.BMI = BMI;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public MoreInformation() {
    }
}
