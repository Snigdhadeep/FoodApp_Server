package com.example.diku.foodserver.Model;

/**
 * Created by Diku on 25-05-2018.
 */

public class FoodList {
    String description,discount,image,menuid,name,price;

    public FoodList() {
    }

    public FoodList(String description, String discount, String image, String menuid, String name, String price) {
        this.description = description;
        this.discount = discount;
        this.image = image;
        this.menuid = menuid;
        this.name = name;
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getMenuid() {
        return menuid;
    }

    public void setMenuid(String menuid) {
        this.menuid = menuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
