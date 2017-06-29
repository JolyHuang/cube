package com.sharingif.cube.core.handler.bind.annotation;

import java.lang.annotation.*;

/**
 * 文件上传验证配置
 *
 * @author Joly
 * @version v1.0
 * @since v1.0
 * 2017/6/29 下午5:42
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface FileSettings {

    long maxSize();

    String[] fileTypes();

}
