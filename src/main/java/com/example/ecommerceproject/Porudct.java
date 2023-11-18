package com.example.ecommerceproject;

import java.util.Date;

public class Porudct{
    private int id;
    private int categorie_id;
    private String name;
    private  String image;
    private  double prix;
    private  String details;
    private String created_at;
    public  Porudct(int id,int categorie_id,String name,String image,double prix,String details,String date){
        this.id=id;
        this.categorie_id=categorie_id;
        this.name=name;
        this.image=image;
        this.prix=prix;
        this.created_at=date;
        this.details=details;
    }

    public int getCategorie_id() {
        return categorie_id;
    }
    public String getName() {
        return name;
    }

    public String getCreated_at() {
        return created_at;
    }

    public double getPrix() {
        return prix;
    }

    public String getImage() {
        return image;
    }
    public String getDetails() {
        return details;
    }

    public int getId() {
        return id;
    }
}
