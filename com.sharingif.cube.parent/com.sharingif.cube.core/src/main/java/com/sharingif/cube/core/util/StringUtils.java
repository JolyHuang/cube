package com.sharingif.cube.core.util;



/**   
 *  
 * @Description:  字符串工具箱
 * @Author:       Joly
 * @CreateDate:   2013年12月26日 下午5:02:42
 * @UpdateUser:   Joly
 * @UpdateDate:   2013年12月26日 下午5:02:42
 * @UpdateRemark: 说明本次修改内容
 * @Version:      v1.0
 *    
 */
public class StringUtils extends org.springframework.util.StringUtils {

    /**
     * 是否为空，null、""、包含空格的""都为空
     * @param str : 字符串
     * @return
     */
    public static boolean isTrimEmpty(String str) {
        return isEmpty(str) || "".equals(str.trim());
    }
	
	
}
