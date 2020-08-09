package com.example.collagemanagementsystem.Admin.Classes;

public class ClassList {

    String name,key;

    public ClassList(){

    }

    public ClassList(String name, String key) {
        this.name = name;
        this.key = key;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
