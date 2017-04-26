package com.sharingif.cube.components.sequence;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 *
 * @Description:  [序列注解]
 * @Author:       [Joly_Huang]
 * @CreateDate:   [2014年5月12日 上午11:58:55]
 * @UpdateUser:   [Joly_Huang]
 * @UpdateDate:   [2014年5月12日 上午11:58:55]
 * @UpdateRemark: [说明本次修改内容]
 * @Version:      [v1.0]
 *
 */

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Sequence {

	String ref();
	
}
