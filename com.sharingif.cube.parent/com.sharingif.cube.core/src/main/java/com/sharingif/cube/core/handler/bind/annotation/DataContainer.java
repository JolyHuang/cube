package com.sharingif.cube.core.handler.bind.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**   
 *  
 * @Description:  [数据容器，处理方法入参对象]   
 * @Author:       [Joly]   
 * @CreateDate:   [2015年3月31日 下午10:35:29]   
 * @UpdateUser:   [Joly]   
 * @UpdateDate:   [2015年3月31日 下午10:35:29]   
 * @UpdateRemark: [说明本次修改内容]  
 * @Version:      [v1.0] 
 *    
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DataContainer {

    String value() default "";

}
