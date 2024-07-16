package com.rest.pojo.collection;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.ArrayList;

public class Collection {

    public String id;

    public String name;
    public String uid;
    public Info info;
    public ArrayList<Folder> item;


    public Collection() {}

    public Collection(Info info, ArrayList<Folder> item) {
        this.info = info;
        this.item = item;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public Info getInfo() {
        return info;
    }

    public void setInfo(Info info) {
        this.info = info;
    }

    public ArrayList<Folder> getItem() {
        return item;
    }

    public void setItem(ArrayList<Folder> item) {
        this.item = item;
    }
}
