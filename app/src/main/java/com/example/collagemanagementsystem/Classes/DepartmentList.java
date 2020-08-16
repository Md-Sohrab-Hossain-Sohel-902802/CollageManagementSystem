package com.example.collagemanagementsystem.Classes;

public class DepartmentList {
    String id;
    String department;


    public  DepartmentList(){

    }

    public DepartmentList(String id, String department) {
        this.id = id;
        this.department = department;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }
}
