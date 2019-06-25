package com.sharingif.cube.communication.http.request;

public class DynamicUrlRequest<T> {

    private String url;

    private T body;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public T getBody() {
        return body;
    }

    public void setBody(T body) {
        this.body = body;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("UrlRequest{");
        sb.append("url='").append(url).append('\'');
        sb.append(", body=").append(body);
        sb.append('}');
        return sb.toString();
    }
}
