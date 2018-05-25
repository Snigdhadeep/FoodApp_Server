package com.example.diku.foodserver.Model;

/**
 * Created by Diku on 23-05-2018.
 */

public class User {

    private String name,phone,password,isstaff;

    public User() {
    }

    public User(String name, String phone, String password, String isstaff) {
        this.name = name;
        this.phone = phone;
        this.password = password;
        this.isstaff = isstaff;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getIsstaff() {
        return isstaff;
    }

    public void setIsstaff(String isstaff) {
        this.isstaff = isstaff;
    }
}
