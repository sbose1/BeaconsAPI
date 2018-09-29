package com.example.preranasingh.inclass04;

public class Product {
    String id,productName, region,imgPath;
    double discount,price;

    public Product(String id,String productName, String region, String imgPath, double discount, double price) {
        this.id=id;
        this.productName = productName;
        this.region = region;
        this.imgPath = imgPath;
        this.discount = discount;
        this.price = price;
    }

    public Product(){

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
