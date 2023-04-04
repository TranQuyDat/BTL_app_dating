package com.example.btl_app_dating;

public class User {
    private int resourceID;
    private String name;
    private  int age;
    private String birth;
    private String relationship;
    private String word;
    private String gender;

    public User() {
    }

    public User(int resourceID, String name){
        this.resourceID=resourceID;
        this.name=name;
    }

    public User(int resourceID, String name, int age, String birth, String relationship, String word, String gender) {
        this.resourceID = resourceID;
        this.name = name;
        this.age = age;
        this.birth = birth;
        this.relationship = relationship;
        this.word = word;
        this.gender = gender;
    }

    public int getresourceID() {
        return resourceID;
    }

    public void setResourceID(int resourceID) {
        this.resourceID = resourceID;
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
}