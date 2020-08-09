package com.example.collagemanagementsystem.Classes;

public class Present {

    String date;
    String key;
    String roll;
    String time;
    String presentance;
    String subjectName;


    public  Present(){

    }




    public Present(String date, String key, String roll, String time, String presentance, String subjectName) {
        this.date = date;
        this.key = key;
        this.roll = roll;
        this.time = time;
        this.presentance = presentance;
        this.subjectName = subjectName;
    }


    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getRoll() {
        return roll;
    }

    public void setRoll(String roll) {
        this.roll = roll;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getPresentance() {
        return presentance;
    }

    public void setPresentance(String presentance) {
        this.presentance = presentance;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }
}
