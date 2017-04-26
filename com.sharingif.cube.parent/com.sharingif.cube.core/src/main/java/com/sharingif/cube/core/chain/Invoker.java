package com.sharingif.cube.core.chain;

import com.sharingif.cube.core.exception.CubeException;


/**   
 *  
 * @Description:  [Invoker]   
 * @Author:       [Joly]   
 * @CreateDate:   [2013年12月16日 下午1:33:02]   
 * @UpdateUser:   [Joly]   
 * @UpdateDate:   [2013年12月16日 下午1:33:02]   
 * @UpdateRemark: [说明本次修改内容]  
 * @Version:      [v1.0] 
 *    
 */
public interface Invoker<T extends Object> {

	void invoker(T content) throws CubeException;
	
}
