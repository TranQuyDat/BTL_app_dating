package com.example.btl_app_dating;

import android.net.Uri;

public class User {
    private String img_uri;
    private String name;
    private  int age;
    private String birth;
    private String relationship;
    private String word;
    private String gender;


    private String idu;
    public User() {
    }

    public User(String img_uri, String name){
        this.img_uri=img_uri;
        this.name=name;
    }


    public User(String img_uri, String name, int age, String birth, String relationship, String word, String gender) {
        this.img_uri = img_uri;
        this.name = name;
        this.age = age;
        this.birth = birth;
        this.relationship = relationship;
        this.word = word;
        this.gender = gender;
    }

    public String getimg_uri() {
        return img_uri;
    }

    public void setimg_uri(String img_uri) {
        this.img_uri = img_uri;
    }

    public String getname() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getage() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getbirth() {
        return birth;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }

    public String getrelationship() {
        return relationship;
    }

    public void setRelationship(String relationship) {
        this.relationship = relationship;
    }

    public String getword() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getgender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }


    public String getidu() {
        return idu;
    }

    public void setIdu(String idu) {
        this.idu = idu;
    }
}