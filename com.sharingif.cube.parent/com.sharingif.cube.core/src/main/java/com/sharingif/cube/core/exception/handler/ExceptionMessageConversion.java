package com.sharingif.cube.core.exception.handler;

import org.springframework.context.support.ApplicationObjectSupport;

import java.util.Locale;

/**
 * 错误消息码转换
 * 2017/6/6 下午3:01
 * @author Joly
 * @version v1.0
 * @since v1.0
 */
public class ExceptionMessageConversion extends ApplicationObjectSupport {

    public String convert(String message, Locale locale){
        return this.getApplicationContext().getMessage(message,null,locale);
    }

}
