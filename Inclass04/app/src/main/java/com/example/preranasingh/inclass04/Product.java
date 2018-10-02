package com.example.preranasingh.inclass04;

public class Product {
    String id,name, region,photo;
    double discount,price;


    public Product(String id,String productName, String region, String photo, double discount, double price) {
        this.id=id;
        this.name = productName;
        this.region = region;
        this.photo = photo;
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

    public String getname() {
        return name;
    }

    public void setname(String name) {
        this.name = name;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {

        this.region = region;
    }

    public String getphoto() {
        return photo;
    }

    public void setphoto(String photo) {
        if (!photo.isEmpty()) {
            this.photo = MainActivity.remoteIP+"/images/"+ photo;
        }
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
