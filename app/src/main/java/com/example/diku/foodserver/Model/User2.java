package com.example.diku.foodserver.Model;

/**
 * Created by Diku on 15-06-2018.
 */

public class User2 {
    private String name,phone,password,isstaff,email;

    public User2() {
    }

    public User2(String name, String phone, String password, String isstaff, String email) {
        this.name = name;
        this.phone = phone;
        this.password = password;
        this.isstaff = isstaff;
        this.email = email;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
