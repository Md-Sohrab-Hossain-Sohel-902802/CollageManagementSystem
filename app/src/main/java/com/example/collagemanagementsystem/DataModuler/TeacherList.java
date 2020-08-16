package com.example.collagemanagementsystem.DataModuler;

public class TeacherList {


    String department;
    String id;
    String image;
    String name;
    String tc;
    String title;





    public TeacherList(){

    }


    public TeacherList(String department, String id, String image, String name, String tc, String title) {
        this.department = department;
        this.id = id;
        this.image = image;
        this.name = name;
        this.tc = tc;
        this.title = title;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTc() {
        return tc;
    }

    public void setTc(String tc) {
        this.tc = tc;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
