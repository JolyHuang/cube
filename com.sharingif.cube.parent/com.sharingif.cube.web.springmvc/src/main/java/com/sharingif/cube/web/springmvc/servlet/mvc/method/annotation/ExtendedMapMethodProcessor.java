package com.sharingif.cube.web.springmvc.servlet.mvc.method.annotation;

import org.springframework.core.MethodParameter;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.annotation.MapMethodProcessor;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.util.Map;

/**
 * 扩展MapMethodProcessor，给map返回值添加一个标示方便json视图返回处理
 *
 * @author Joly
 * @version v1.0
 * @since v1.0
 * 2018/5/29 下午1:53
 */
public class ExtendedMapMethodProcessor extends MapMethodProcessor {

    public static final String RETURN_VALUE_TYPE_MAP = "_ReturnValueTypeMap";

    @Override
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public void handleReturnValue(Object returnValue, MethodParameter returnType,
                                  ModelAndViewContainer mavContainer, NativeWebRequest webRequest) throws Exception {

        if (returnValue == null) {
            return;
        }
        else if (returnValue instanceof Map){
            Map returnMap = (Map) returnValue;
            returnMap.put(RETURN_VALUE_TYPE_MAP, null);
            mavContainer.addAllAttributes(returnMap);
        }
        else {
            // should not happen
            throw new UnsupportedOperationException("Unexpected return type: " +
                    returnType.getParameterType().getName() + " in method: " + returnType.getMethod());
        }
    }

}
