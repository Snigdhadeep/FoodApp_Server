package com.example.diku.foodserver.Model;

/**
 * Created by Diku on 24-05-2018.
 */

public class Category {

  private String name;
  private String img;

    public Category() {
    }

    public Category(String name, String img) {
        this.name = name;
        this.img = img;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
}
