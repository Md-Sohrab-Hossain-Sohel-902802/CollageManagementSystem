package com.example.collagemanagementsystem.Admin.Classes;

public class ClassList {

    String name,key,group,department,session,shift;

    public ClassList(){

    }

    public ClassList(String name, String key, String group, String department, String session, String shift) {
        this.name = name;
        this.key = key;
        this.group = group;
        this.department = department;
        this.session = session;
        this.shift = shift;
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

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getSession() {
        return session;
    }

    public void setSession(String session) {
        this.session = session;
    }

    public String getShift() {
        return shift;
    }

    public void setShift(String shift) {
        this.shift = shift;
    }
}
