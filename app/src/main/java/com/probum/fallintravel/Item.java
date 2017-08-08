package com.probum.fallintravel;


public class Item {

    String title;
    String firstimage;
    String time;

    public Item(String title, String firstimage,String time) {
        this.title = title;
        this.firstimage = firstimage;
        this.time=time;
    }

    public Item(String title, String firstimage) {
        this.title = title;
        this.firstimage = firstimage;
    }
}
