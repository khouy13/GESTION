package com.example.ecommerceproject;

public class Detail {
    private  int id;
    private int quantite;
    private String product_name;

    private double price;

    public Detail(int id,int quantite,String product_name,double price){
        this.product_name=product_name;
        this.quantite=quantite;
        this.price=price;
        this.id=id;
    }

    public int getId() {
        return id;
    }

    public double getPrice() {
        return price;
    }

    public int getQuantite() {
        return quantite;
    }
    public String getProduct_name() {
        return product_name;
    }
}
