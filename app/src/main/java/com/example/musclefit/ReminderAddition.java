package com.example.musclefit;

public class ReminderAddition {
    String time;
    String days;

    public ReminderAddition(String time, String days) {
        this.time = time;
        this.days = days;
    }

    public ReminderAddition() {
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDays() {
        return days;
    }

    public void setDays(String days) {
        this.days = days;
    }
}
