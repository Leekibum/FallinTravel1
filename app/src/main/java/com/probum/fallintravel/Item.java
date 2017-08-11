package com.probum.fallintravel;


public class Item {

    String title;
    String firstimage;
    String time;
    String contentid;

    public Item(String title, String firstimage,String time,String contentid) {
        this.title = title;
        this.firstimage = firstimage;
        this.time=time;
        this.contentid=contentid;
    }

    public Item(String title, String firstimage,String contentid) {
        this.title = title;
        this.firstimage = firstimage;
        this.contentid=contentid;
    }

}
