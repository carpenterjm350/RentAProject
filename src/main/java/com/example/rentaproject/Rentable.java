package com.example.rentaproject;

public class Rentable {


    String name, price, time, id;


    public Rentable(String name, String price, String time)
    {
        id = null;
        this.name = name;
        this.price = price;
        this.time = time;
    }

    public Rentable(String id, String name, String price, String time)
    {
        this.id = id;
        this.name = name;
        this.price = price;
        this.time = time;
    }


    public String getId() { return id;}

    public String getName() {
        return name;
    }

    public String getPrice() {
        return price;
    }

    public String getTime() {
        return time;
    }


}
