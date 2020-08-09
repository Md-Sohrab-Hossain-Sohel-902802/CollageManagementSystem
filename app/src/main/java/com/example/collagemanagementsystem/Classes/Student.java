package com.example.collagemanagementsystem.Classes;

public class Student {

    String name;
    String roll;
    String key;

    public Student(){

    }

    public Student(String name, String roll, String key) {
        this.name = name;
        this.roll = roll;
        this.key = key;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRoll() {
        return roll;
    }

    public void setRoll(String roll) {
        this.roll = roll;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
