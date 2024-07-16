package com.rest.pojo.collection;

import java.util.ArrayList;

public class Folder {

    public Folder() {}

    public Folder(String name, ArrayList<Item> item) {
        this.name = name;
        this.item = item;
    }

    public String name;

    public ArrayList<Item> item;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Item> getItem() {
        return item;
    }

    public void setItem(ArrayList<Item> item) {
        this.item = item;
    }


}
