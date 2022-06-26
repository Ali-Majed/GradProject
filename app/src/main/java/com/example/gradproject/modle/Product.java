package com.example.gradproject.modle;

import android.net.Uri;

public class Product {

    private String id_product,name,size;
    private String quantity;
    private String image_product;
    private String user_id;

    public Product(String name, String size, String quantity, String image_product, String user_id) {
        this.name = name;
        this.size = size;
        this.quantity = quantity;
        this.image_product = image_product;
        this.user_id = user_id;
    }

    public Product() {
    }

    public String getId_product() {
        return id_product;
    }

    public void setId_product(String id_product) {
        this.id_product = id_product;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage_product() {
        return image_product;
    }

    public void setImage_product(String image_product) {
        this.image_product = image_product;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }
}
