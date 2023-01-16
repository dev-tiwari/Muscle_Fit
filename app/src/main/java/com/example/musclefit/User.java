package com.example.musclefit;

public class User {
    private String name;
    private String email;
    private String phoneNumber;
//    private String imageUrl;
    private String personalize;
    private String height;
    private String weight;
    private String age;
    private String gender;
    private String BMI;
    private String weightCategory;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

//    public String getImageUrl() {
//        return imageUrl;
//    }

//    public void setImageUrl(String imageUrl) {
//        this.imageUrl = imageUrl;
//    }

    public String getPersonalize() {
        return personalize;
    }

    public void setPersonalize(String personalize) {
        this.personalize = personalize;
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

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getBMI() {
        return BMI;
    }

    public void setBMI(String BMI) {
        this.BMI = BMI;
    }

    public String getWeightCategory() {
        return weightCategory;
    }

    public void setWeightCategory(String weightCategory) {
        this.weightCategory = weightCategory;
    }

    public User(String name, String email, String phoneNumber, String personalize, String height, String weight, String age, String gender, String BMI, String weightCategory) {
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
//        this.imageUrl = imageUrl;
        this.personalize = personalize;
        this.height = height;
        this.weight = weight;
        this.age = age;
        this.gender = gender;
        this.BMI = BMI;
        this.weightCategory = weightCategory;
    }

    public User(String name, String email, String phoneNumber) {
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    public User(String name, String email, String phoneNumber, String personalize) {
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.personalize = personalize;
    }

    public User() {
    }
}
