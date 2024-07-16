package com.rest.pojo.collection;

import java.util.ArrayList;

public class Item {

    public String name;
    public Request request;
    public ArrayList<Object> response;

    public Item() {}

    public Item(String name, Request request, ArrayList<Object> response) {
        this.name = name;
        this.request = request;
        this.response = response;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Request getRequest() {
        return request;
    }

    public void setRequest(Request request) {
        this.request = request;
    }

    public ArrayList<Object> getResponse() {
        return response;
    }

    public void setResponse(ArrayList<Object> response) {
        this.response = response;
    }
}
