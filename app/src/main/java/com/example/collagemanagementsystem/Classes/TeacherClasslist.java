package com.example.collagemanagementsystem.Classes;

public class TeacherClasslist {

    String classname;
    String subjectname;


    public TeacherClasslist() {
    }


    public TeacherClasslist(String classname,String subjectname) {
        this.classname = classname;

        this.subjectname=subjectname;
    }

    public String getSubjectName() {
        return subjectname;
    }

    public void setSubjectName(String subjectName) {
        this.subjectname = subjectName;
    }

    public String getClassname() {
        return classname;
    }

    public void setClassname(String classname) {
        this.classname = classname;
    }
}
