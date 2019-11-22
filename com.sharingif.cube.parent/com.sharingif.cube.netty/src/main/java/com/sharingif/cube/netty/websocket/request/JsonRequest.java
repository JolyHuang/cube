package com.sharingif.cube.netty.websocket.request;

public class JsonRequest<T> {

    private String lookupPath;
    private T _data;

    public String getLookupPath() {
        return lookupPath;
    }

    public void setLookupPath(String lookupPath) {
        this.lookupPath = lookupPath;
    }

    public T get_data() {
        return _data;
    }

    public void set_data(T _data) {
        this._data = _data;
    }
}
