package com.sharingif.cube.web.springmvc.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.NoHandlerFoundException;

import com.sharingif.cube.communication.http.HttpRequest;
import com.sharingif.cube.communication.http.HttpResponse;
import com.sharingif.cube.communication.http.request.HttpRequestContext;
import com.sharingif.cube.core.exception.ICubeException;
import com.sharingif.cube.core.exception.handler.ExceptionContent;
import com.sharingif.cube.core.handler.HandlerMethod;
import com.sharingif.cube.core.handler.exception.NoHandlerMappingFoundException;
import com.sharingif.cube.web.exception.handler.validation.ValidationCubeExceptionHandler;

/**
 * 请求没有对应的处理类，默认返回404视图
 * 2017/6/5 下午3:47
 * @author Joly
 * @version v1.0
 * @since v1.0
 */
public class NoHandlerFoundExceptionHandler extends ValidationCubeExceptionHandler {

    @Override
    public boolean supports(Exception exception) {
        return exception instanceof NoHandlerFoundException;
    }

    @Override
    protected ICubeException convertExceptionInternal(Exception exception) {
        return new NoHandlerMappingFoundException();
    }

    @Override
    public ExceptionContent handlerException(HttpRequestContext<HttpRequest, HttpResponse> requestContext, HandlerMethod handlerMethod, ICubeException cubeException) {
        ExceptionContent out = new ExceptionContent();
        out.setViewName(String.valueOf(HttpStatus.NOT_FOUND.value()));

        return out;
    }
}
