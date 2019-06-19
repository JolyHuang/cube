package com.sharingif.cube.batch.core.handler.adapter;

import com.sharingif.cube.batch.core.request.JobRequest;
import com.sharingif.cube.core.exception.CubeException;
import com.sharingif.cube.core.handler.adapter.HandlerMethodArgumentResolver;
import com.sharingif.cube.core.handler.bind.support.DataBinderFactory;
import com.sharingif.cube.core.request.RequestContext;
import org.springframework.core.MethodParameter;

/**
 * JobRequestHandlerMethodArgumentResolver
 *
 * @author Joly
 * @version v1.0
 * @since v1.0
 * 2017/11/21 下午4:19
 */
public class JobRequestHandlerMethodArgumentResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter parameter, RequestContext<?> requestContext) {
        return JobRequest.class.isInstance(requestContext.getRequest());
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, RequestContext<?> requestContext, DataBinderFactory dataBinderFactory) throws CubeException {
        return requestContext.getRequest();
    }

}
