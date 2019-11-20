package com.sharingif.cube.netty.websocket.request;

public class JsonRequest<T> {

    private String lookupPath;
    private T data;

    public String getLookupPath() {
        return lookupPath;
    }

    public void setLookupPath(String lookupPath) {
        this.lookupPath = lookupPath;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "JsonRequest{" +
                "lookupPath='" + lookupPath + '\'' +
                ", data=" + data +
                '}';
    }
}
