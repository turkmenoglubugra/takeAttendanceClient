package com.example.lenovo.myapplication;

public class student {

    private String number;
    private String state;
    private String poolDate;

    public student(String number, String state, String poolDate){
        this.number = number;
        this.state = state;
        this.poolDate = poolDate;
    }
    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getPoolDate() {
        return poolDate;
    }

    public void setPoolDate(String poolDate) {
        this.poolDate = poolDate;
    }

}
