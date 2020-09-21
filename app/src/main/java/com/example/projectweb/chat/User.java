package com.example.projectweb.chat;

import com.google.gson.annotations.SerializedName;

public class User {
    public User(String username, String password){
        this.gender=" ";
        this.password=password;
        this.username=username;
        this.firstName=" ";
        this.lastName=" ";
        this.age=" ";
    }

    public User(String username, String password, String age, String gender){
        this.gender=gender;
        this.password=password;
        this.username=username;
        this.firstName=" ";
        this.lastName=" ";
        this.age=age;
    }

    public User(String username, String password, String age, String gender, String firstName,  String lastName){
        this.gender=gender;
        this.password=password;
        this.username=username;
        this.firstName=firstName;
        this.lastName=lastName;
        this.age=age;
    }

    @SerializedName("age")
    String age;

    @SerializedName("gender")
    String gender;

    @SerializedName("password")
    String password;

    @SerializedName("name")
    String username;

    @SerializedName("first_name")
    String firstName;

    @SerializedName("last_name")
    String lastName;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setPassword(String password){
        this.password = password;
    }

}
