package com.rest.pojo.collection;

import java.util.ArrayList;

public class Url {

    public String raw;
    public String protocol;
    public ArrayList<String> host;
    public ArrayList<String> path;

    public Url() {}

    public Url(String raw, String protocol, ArrayList<String> host, ArrayList<String> path) {
        this.raw = raw;
        this.protocol = protocol;
        this.host = host;
        this.path = path;
    }

    public String getRaw() {
        return raw;
    }

    public void setRaw(String raw) {
        this.raw = raw;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public ArrayList<String> getHost() {
        return host;
    }

    public void setHost(ArrayList<String> host) {
        this.host = host;
    }

    public ArrayList<String> getPath() {
        return path;
    }

    public void setPath(ArrayList<String> path) {
        this.path = path;
    }
}
