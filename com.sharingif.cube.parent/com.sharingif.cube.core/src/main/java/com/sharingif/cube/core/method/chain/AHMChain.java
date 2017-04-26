package com.sharingif.cube.core.method.chain;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**   
 *  
 * @Description:  [方法调用后责任链]   
 * @Author:       [Joly_Huang]   
 * @CreateDate:   [2014年1月24日 上午9:35:54]   
 * @UpdateUser:   [Joly_Huang]   
 * @UpdateDate:   [2014年1月24日 上午9:35:54]   
 * @UpdateRemark: [说明本次修改内容]  
 * @Version:      [v1.0] 
 *    
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface AHMChain {
	String ref();
}
