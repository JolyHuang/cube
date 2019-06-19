package com.sharingif.cube.batch.core.request;

import com.sharingif.cube.core.request.RequestContext;
import com.sharingif.cube.core.request.RequestContextResolver;

/**
 * 解析JobRequest请求
 *
 * @author Joly
 * @version v1.0
 * @since v1.0
 * 2017/11/21 下午3:40
 */
public class JobRequestContextResolver<T> implements RequestContextResolver<JobRequest<T>, RequestContext<JobRequest<T>>> {

    @Override
    public RequestContext<JobRequest<T>> resolveRequest(JobRequest request) {

        RequestContext<JobRequest<T>> requestContext = new RequestContext<JobRequest<T>>("object", request.getLookupPath(), null, "POST",  request);

        return requestContext;
    }

}
