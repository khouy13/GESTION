package com.example.ecommerceproject;

public class User {
    private String name;
    private String phone;
    private  String address;

    public User(String name,String phone,String address){
          this.name=name;
          this.phone=phone;
          this.address=address;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getPhone() {
        return phone;
    }

}
