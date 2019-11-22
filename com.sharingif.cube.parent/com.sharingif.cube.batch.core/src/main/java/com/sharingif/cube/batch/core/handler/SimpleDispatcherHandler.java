package com.sharingif.cube.batch.core.handler;

import com.sharingif.cube.batch.core.request.JobRequest;
import com.sharingif.cube.communication.handler.AbstractDispatcherHandler;
import com.sharingif.cube.core.handler.chain.HandlerMethodContent;
import com.sharingif.cube.core.request.RequestContext;

/**
 * 只能处理本地请求
 *
 * @author Joly
 * @version v1.0
 * @since v1.0
 * 2017/11/21 下午3:26
 */
public class SimpleDispatcherHandler extends AbstractDispatcherHandler<JobRequest> {

    @Override
    protected HandlerMethodContent getHandlerMethodContent(JobRequest request) {
        return new HandlerMethodContent(
                null,
                null,
                null,
                null,
                new RequestContext<JobRequest>(null,request.getLookupPath(),null,"POST",request));
    }

}
