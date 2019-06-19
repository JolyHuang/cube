package com.sharingif.cube.batch.core.exception;

import com.sharingif.cube.batch.core.request.JobRequest;
import com.sharingif.cube.core.exception.ICubeException;
import com.sharingif.cube.core.exception.UnknownCubeException;
import com.sharingif.cube.core.exception.handler.ExceptionContent;
import com.sharingif.cube.core.handler.HandlerMethod;
import com.sharingif.cube.core.handler.exception.handler.AbstractCubeHandlerMethodExceptionHandler;
import com.sharingif.cube.core.request.RequestContext;

/**
 * job 异常处理
 *
 * @author Joly
 * @version v1.0
 * @since v1.0
 * 2017/11/28 下午6:44
 */
public class JobExceptionHandler extends AbstractCubeHandlerMethodExceptionHandler<RequestContext<JobRequest>> {

    @Override
    public boolean supports(Exception exception) {
        return true;
    }

    @Override
    protected ICubeException convertExceptionInternal(Exception exception) {

        if(exception instanceof ICubeException)
            return (ICubeException)exception;

        return new UnknownCubeException(exception);

    }

    @Override
    public ExceptionContent handlerException(RequestContext<JobRequest> requestContext, HandlerMethod handler, ICubeException cubeException) {

        ExceptionContent exceptionContent = new ExceptionContent();
        exceptionContent.setCubeException(cubeException);

        return exceptionContent;
    }
}
