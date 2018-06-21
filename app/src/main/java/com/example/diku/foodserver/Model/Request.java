package com.example.diku.foodserver.Model;

import android.widget.Toast;

import java.util.List;

/**
 * Created by Diku on 27-05-2018.
 */

public class Request {
    private String phone;
    private String name;
    private String address;
    private String total;
    private String status;
    List<Order> orders;

    public Request() {

    }

    public Request(String phone, String name, String address, String total, String status, List<Order> orders) {
        this.phone = phone;
        this.name = name;
        this.address = address;
        this.total = total;
        this.status = status;
        this.orders = orders;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }
}
