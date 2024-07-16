package com.rest.pojo.collection;

public class Info {
    public String _postman_id;
    public String name;
    public String schema;
    public String _exporter_id;

    public Info() {}

    public Info(String _postman_id, String name, String schema, String _exporter_id) {
        this._postman_id = _postman_id;
        this.name = name;
        this.schema = schema;
        this._exporter_id = _exporter_id;
    }

    public String get_postman_id() {
        return _postman_id;
    }

    public void set_postman_id(String _postman_id) {
        this._postman_id = _postman_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSchema() {
        return schema;
    }

    public void setSchema(String schema) {
        this.schema = schema;
    }

    public String get_exporter_id() {
        return _exporter_id;
    }

    public void set_exporter_id(String _exporter_id) {
        this._exporter_id = _exporter_id;
    }
}
