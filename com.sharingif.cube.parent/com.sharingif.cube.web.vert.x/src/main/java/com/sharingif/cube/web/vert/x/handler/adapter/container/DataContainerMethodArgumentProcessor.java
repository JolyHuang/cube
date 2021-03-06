package com.sharingif.cube.web.vert.x.handler.adapter.container;

import org.springframework.core.MethodParameter;

import com.sharingif.cube.core.exception.CubeException;
import com.sharingif.cube.core.handler.bind.support.DataBinderFactory;
import com.sharingif.cube.core.request.RequestContext;

/**
 * 处理@DataContainer 过滤过的参数
 *
 * @author Joly
 * @version v1.0
 * @since v1.0
 * 2017/10/31 下午4:50
 */
public interface DataContainerMethodArgumentProcessor {

    boolean supportsParameter(Class<?> parameterType);

    Object resolveArgument(MethodParameter parameter, RequestContext<?> requestContext, DataBinderFactory dataBinderFactory) throws CubeException;

}
