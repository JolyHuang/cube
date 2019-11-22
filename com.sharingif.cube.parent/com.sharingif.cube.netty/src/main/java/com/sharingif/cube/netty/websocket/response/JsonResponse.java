package com.sharingif.cube.netty.websocket.response;

import com.sharingif.cube.core.view.JsonModel;

public class JsonResponse<T> extends JsonModel<T> {

    private String lookupPath;

    public String getLookupPath() {
        return lookupPath;
    }

    public void setLookupPath(String lookupPath) {
        this.lookupPath = lookupPath;
    }
}
