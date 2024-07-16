package com.rest.pojo.collection;

import java.util.ArrayList;

public class Request {

    public String method;
    public ArrayList<Header> header;
    public Url url;

    public Request() {}

    public Request(String method, ArrayList<Header> header, Url url) {
        this.method = method;
        this.header = header;
        this.url = url;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public ArrayList<Header> getHeader() {
        return header;
    }

    public void setHeader(ArrayList<Header> header) {
        this.header = header;
    }

    public Url getUrl() {
        return url;
    }

    public void setUrl(Url url) {
        this.url = url;
    }
}
