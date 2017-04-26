package com.sharingif.cube.security.authentication.authority;

import com.sharingif.cube.core.user.ICoreUser;
import com.sharingif.cube.security.exception.validation.authentication.AuthenticationCubeException;

/**   
 *  
 * @Description:  [处理权限]   
 * @Author:       [Joly_Huang]   
 * @CreateDate:   [2014年3月17日 下午4:02:03]   
 * @UpdateUser:   [Joly_Huang]   
 * @UpdateDate:   [2014年3月17日 下午4:02:03]   
 * @UpdateRemark: [说明本次修改内容]  
 * @Version:      [v1.0] 
 *    
 */
public interface IAuthorityAuthenticationHandler<T extends ICoreUser> {
	
	void handleAuthorities(T coreUser) throws AuthenticationCubeException;

}
