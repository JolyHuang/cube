package com.sharingif.cube.api;

import com.sharingif.cube.api.model.ApiClass;

/**
 * api 类处理器
 *
 * @author Joly
 * @version v1.0
 * @since v1.0
 * 2017/11/2 上午11:14
 */
public class ApiClassHandler {

    private ApiMethodHandler apiMethodHandler;
    private ApiParameterHandler apiParameterHandler;

    public ApiClassHandler () {
        apiMethodHandler = new ApiMethodHandler();
        apiParameterHandler = new ApiParameterHandler();
    }

    public ApiClass handleApiClass(Class cla) {
        cla.get
    }

}
