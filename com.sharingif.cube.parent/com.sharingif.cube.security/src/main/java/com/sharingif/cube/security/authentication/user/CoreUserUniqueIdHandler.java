package com.sharingif.cube.security.authentication.user;

import com.sharingif.cube.core.user.ICoreUser;
import com.sharingif.cube.security.exception.validation.authentication.AuthenticationCubeException;

/**   
 *  
 * @Description:  [设置用户UniqueId]   
 * @Author:       [Joly_Huang]   
 * @CreateDate:   [2014年3月23日 下午1:19:39]   
 * @UpdateUser:   [Joly_Huang]   
 * @UpdateDate:   [2014年3月23日 下午1:19:39]   
 * @UpdateRemark: [说明本次修改内容]  
 * @Version:      [v1.0] 
 *    
 */
public class CoreUserUniqueIdHandler {

	public void handleCoreUserUniqueId(ICoreUser coreUser) throws AuthenticationCubeException {
		coreUser.setUniqueId(coreUser.getUsername());
	}

}
