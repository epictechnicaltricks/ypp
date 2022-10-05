package com.cakiweb.easyscholar;

public class Day {
    String status;
    String date;
    String month;
    String year;
    String day;
    public Day(String status, String date, String month, String year, String day){
        this.status = status;
        this.date = date;
        this.month = month;
        this.year = year;
        this.day = day;
    }

    public String getMonth() {
        return month;
    }

    public String getDate() {
        return date;
    }

    public String getDay() {
        return day;
    }

    public String getStatus() {
        return status;
    }

    public String getYear() {
        return year;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setYear(String year) {
        this.year = year;
    }

}
