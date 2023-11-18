package com.example.ecommerceproject;

public class Command {
    private String livraison;
    private String payment;
    private String statut;

    private double total;

    private  int id;

    private int user_id;

    private String created_at;
    public Command(String livraison,String statut,String payment,double total,int id,int user_id,String created_at){

        this.created_at=created_at;
        this.statut=statut;
        this.payment=payment;
        this.id=id;
        this.user_id=user_id;
        this.livraison=livraison;
        this.total=total;
    }

    public int getId() {
        return id;
    }

    public int getUser_id() {
        return user_id;
    }

    public double getTotal() {
        return total;
    }

    public String getCreated_at() {
        return created_at;
    }

    public String getLivraison() {
        return livraison;
    }

    public String getPayment() {
        return payment;
    }

    public String getStatut(){
        return statut;
    }
}
