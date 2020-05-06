package com.example.lenovo.myapplication;

public class User {
    private String userName;
    private boolean userGender;
    private String currentTime;


    public User(String userName, boolean userGender, String currentTime) {
        this.userName = userName;
        this.userGender = userGender;
        this.currentTime = currentTime;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public boolean isUserGender() {
        return userGender;
    }

    public void setUserGender(boolean userGender) {
        this.userGender = userGender;
    }

    public String getCurrentTime() {
        return currentTime;
    }

    public void setCurrentTime(String currentTime) {
        this.currentTime = currentTime;
    }

}