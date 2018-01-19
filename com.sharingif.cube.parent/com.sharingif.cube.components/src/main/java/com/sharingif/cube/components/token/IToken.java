package com.sharingif.cube.components.token;

import java.io.Serializable;

/**   
 *  
 * @Description:  [IToken]   
 * @Author:       [Joly_Huang]   
 * @CreateDate:   [2014年1月30日 下午12:18:42]   
 * @UpdateUser:   [Joly_Huang]   
 * @UpdateDate:   [2014年1月30日 下午12:18:42]   
 * @UpdateRemark: [说明本次修改内容]  
 * @Version:      [v1.0] 
 *    
 */
public interface IToken extends Serializable {
	
	/**
	 * @return 生成时间
	 */
	long getCreateDate();

	/**
	 * @return token唯一id
	 */
	String getUniqueId();
	
	/**
	 * @return token是否有效
	 */
	boolean isUnavailable();
	
	/**
	 * @param unavailable 设置token有效状态
	 */
	void setUnavailable(boolean unavailable);
}
