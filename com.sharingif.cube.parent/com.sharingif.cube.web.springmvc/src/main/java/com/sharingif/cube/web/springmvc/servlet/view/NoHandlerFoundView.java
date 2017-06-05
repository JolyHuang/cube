package com.sharingif.cube.web.springmvc.servlet.view;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.View;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

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
        return MediaType.TEXT_HTML_VALUE;
    }

    @Override
    public void render(Map<String, ?> model, HttpServletRequest request, HttpServletResponse response) throws Exception {
        response.setStatus(HttpStatus.NOT_FOUND.value());
    }
}
