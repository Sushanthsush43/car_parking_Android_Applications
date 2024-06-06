package com.example.car_parking;

public class CardItem {
    public String name;
    public String bname;
    public String cn;
    public String ex;
    public String email;

    public CardItem(String name, String bname, String cn, String ex, String email)
    {
        this.name = name;
        this.bname=bname;
        this.cn=cn;
        this.ex=ex;
        this.email=email;
    }

    public String getName() {
        return name;
    }
}