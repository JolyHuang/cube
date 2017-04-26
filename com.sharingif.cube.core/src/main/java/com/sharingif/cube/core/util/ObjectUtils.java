package com.sharingif.cube.core.util;

import java.util.List;
import java.util.Set;

/**   
 *  
 * @Description:  对象工具箱 
 * @Author:       Joly 
 * @CreateDate:   2013年12月26日 下午5:04:41 
 * @UpdateUser:   Joly
 * @UpdateDate:   2013年12月26日 下午5:04:41 
 * @UpdateRemark: 说明本次修改内容
 * @Version:      v1.0
 *    
 */
public class ObjectUtils extends org.springframework.util.ObjectUtils {
	
	/**
	 * 是否是空List
	 * @param list
	 * @return
	 */
	public static boolean isEmpty(List<?> list) {
		return (list == null || list.isEmpty());
	}
	
	/**
	 * 是否是空Set
	 * @param set
	 * @return
	 */
	public static boolean isEmpty(Set<?> set) {
		return (set == null || set.isEmpty());
	}

}
