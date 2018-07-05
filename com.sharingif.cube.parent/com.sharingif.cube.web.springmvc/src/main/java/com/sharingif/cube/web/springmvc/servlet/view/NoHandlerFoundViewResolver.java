package com.sharingif.cube.web.springmvc.servlet.view;

import java.util.Locale;

import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;

/**
 * 解析404视图
 * 2017/6/5 下午4:13
 * @author Joly
 * @version v1.0
 * @since v1.0
 */
public class NoHandlerFoundViewResolver implements ViewResolver, Ordered {
    private int order = Ordered.LOWEST_PRECEDENCE - 1;

    private NoHandlerFoundView noHandlerFoundView = new NoHandlerFoundView();

    public void setOrder(int order) {
        this.order = order;
    }
    @Override
    public int getOrder() {
        return order;
    }

    @Override
    public View resolveViewName(String viewName, Locale locale) throws Exception {
        if (String.valueOf(HttpStatus.NOT_FOUND.value()).equals(viewName)) {
            return noHandlerFoundView;
        }
        return null;
    }


}
