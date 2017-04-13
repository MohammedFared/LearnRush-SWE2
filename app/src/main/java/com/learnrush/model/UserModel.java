package com.learnrush.model;

/**
 * LearnRush Created by Mohammed Fareed on 4/10/2017.
 */

//"users" : {
//        "user_id" : {
//            "age" : 12,
//            "gender" : "male",
//            "is_student" : true,
//            "mail" : "email.com",
//            "name" : "name"
//            "phone" : 112156603
//        }
//}

public class UserModel {
    private String name, mail;
    private int age;
    private int phone;
    private boolean isTeacher;

    public UserModel() {
    }

    public UserModel(String mail, String name, int phone, int age, boolean isTeacher) {
        this.name = name;
        this.mail = mail;
        this.age = age;
        this.phone = phone;
        this.isTeacher = isTeacher;
    }

    public boolean getIsTeacher() {
        return isTeacher;
    }

    public void setIsTeacher(boolean isTeacher) {
        this.isTeacher = isTeacher;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getPhone() {
        return phone;
    }

    public void setPhone(int phone) {
        this.phone = phone;
    }
}
