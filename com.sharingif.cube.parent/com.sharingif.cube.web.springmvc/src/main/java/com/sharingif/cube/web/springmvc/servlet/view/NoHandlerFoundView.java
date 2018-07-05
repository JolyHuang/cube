package com.sharingif.cube.web.springmvc.servlet.view;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.View;

/**
 * 返回404状态码
 * 2017/6/5 下午4:05
 * @author Joly
 * @version v1.0
 * @since v1.0
 */
public class NoHandlerFoundView implements View {


    @Override
    public String getContentType() {
        return MediaType.ALL_VALUE;
    }

    @Override
    public void render(Map<String, ?> model, HttpServletRequest request, HttpServletResponse response) throws Exception {
        response.setStatus(HttpStatus.NOT_FOUND.value());
    }

}
