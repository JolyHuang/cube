package com.sharingif.cube.core.chain.command;

import com.sharingif.cube.core.exception.CubeException;

/**   
 *  
 * @Description:  [Execute]   
 * @Author:       [Joly]   
 * @CreateDate:   [2013年12月16日 下午1:33:02]   
 * @UpdateUser:   [Joly]   
 * @UpdateDate:   [2013年12月16日 下午1:33:02]   
 * @UpdateRemark: [说明本次修改内容]  
 * @Version:      [v1.0] 
 *    
 */
public interface Execute<T extends Object> {
	
	void execute(T content) throws CubeException;

}
