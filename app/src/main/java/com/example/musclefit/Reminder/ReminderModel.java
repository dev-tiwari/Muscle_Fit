package com.example.musclefit.Reminder;

public class ReminderModel {
    String reminderId;

    public ReminderModel() {
    }

    public ReminderModel(String reminderId) {
        this.reminderId = reminderId;
    }

    public String getReminderId() {
        return reminderId;
    }

    public void setReminderId(String reminderId) {
        this.reminderId = reminderId;
    }
}
