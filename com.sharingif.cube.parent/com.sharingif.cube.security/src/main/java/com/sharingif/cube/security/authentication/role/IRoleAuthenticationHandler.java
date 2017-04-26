package com.sharingif.cube.security.authentication.role;

import com.sharingif.cube.core.exception.CubeException;
import com.sharingif.cube.core.user.ICoreUser;


/**   
 *  
 * @Description:  [处理用户角色]   
 * @Author:       [Joly_Huang]   
 * @CreateDate:   [2014年3月17日 下午9:40:23]   
 * @UpdateUser:   [Joly_Huang]   
 * @UpdateDate:   [2014年3月17日 下午9:40:23]   
 * @UpdateRemark: [说明本次修改内容]  
 * @Version:      [v1.0] 
 *    
 */
public interface IRoleAuthenticationHandler<T extends ICoreUser> {
	
	void handleRole(T coreUser) throws CubeException;

}
