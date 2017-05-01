package com.sharingif.cube.core.handler.bind.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 方法配置参数
 * 2016年4月3日 下午12:36:10
 * @author Joly
 * @version v1.0
 * @since v1.0
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Settings {

	Setting[] setting();
	
}
