package com.example.collagemanagementsystem.Classes;

public class AllUser {

    String email;
    String fName;
    String gender;
    String image;
    String lName;
    String mobile;
    String password;
    String relationship;
    String teacherat;
    String uid;
    String worksat;


    public AllUser(){

    }

    public AllUser(String email, String fName, String gender, String image, String lName, String mobile, String password, String relationship, String teacherat, String uid, String worksat) {
        this.email = email;
        this.fName = fName;
        this.gender = gender;
        this.image = image;
        this.lName = lName;
        this.mobile = mobile;
        this.password = password;
        this.relationship = relationship;
        this.teacherat = teacherat;
        this.uid = uid;
        this.worksat = worksat;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getfName() {
        return fName;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getlName() {
        return lName;
    }

    public void setlName(String lName) {
        this.lName = lName;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRelationship() {
        return relationship;
    }

    public void setRelationship(String relationship) {
        this.relationship = relationship;
    }

    public String getTeacherat() {
        return teacherat;
    }

    public void setTeacherat(String teacherat) {
        this.teacherat = teacherat;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getWorksat() {
        return worksat;
    }

    public void setWorksat(String worksat) {
        this.worksat = worksat;
    }
}
