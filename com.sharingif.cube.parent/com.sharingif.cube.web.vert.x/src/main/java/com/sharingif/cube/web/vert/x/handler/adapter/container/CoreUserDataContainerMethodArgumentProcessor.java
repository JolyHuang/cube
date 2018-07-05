package com.sharingif.cube.web.vert.x.handler.adapter.container;

import org.springframework.core.MethodParameter;

import com.sharingif.cube.core.exception.CubeException;
import com.sharingif.cube.core.handler.bind.support.DataBinderFactory;
import com.sharingif.cube.core.request.RequestContext;
import com.sharingif.cube.core.user.CoreUserContextHolder;
import com.sharingif.cube.core.user.ICoreUser;

/**
 * 处理session
 *
 * @author Joly
 * @version v1.0
 * @since v1.0
 * 2017/10/31 下午4:54
 */
public class CoreUserDataContainerMethodArgumentProcessor implements DataContainerMethodArgumentProcessor {

    @Override
    public boolean supportsParameter(Class<?> parameterType) {
        return ICoreUser.class.isAssignableFrom(parameterType);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, RequestContext<?> requestContext, DataBinderFactory dataBinderFactory) throws CubeException {

        return CoreUserContextHolder.getContext();
    }
}
