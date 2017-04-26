package com.sharingif.cube.security.authentication.password;

import com.sharingif.cube.components.password.IOriginalPassword;
import com.sharingif.cube.core.exception.validation.ValidationCubeException;


/**   
 *  
 * @Description:  [验证原始密码]   
 * @Author:       [Joly]   
 * @CreateDate:   [2014年12月28日 下午3:56:27]   
 * @UpdateUser:   [Joly]   
 * @UpdateDate:   [2014年12月28日 下午3:56:27]   
 * @UpdateRemark: [说明本次修改内容]  
 * @Version:      [v1.0] 
 *    
 */
public abstract class AbstractTextEncryptorOriginalPasswordHandler extends TextEncryptorOriginalPasswordAuthentication implements IOriginalPasswordHandler  {
	
	abstract protected String getOriginalPassword(String username);
	
	@Override
	public void handleOriginalPassword(String username, IOriginalPassword originalPassword) throws ValidationCubeException {
		super.originalPasswordAuthentication(getOriginalPassword(username), originalPassword);
	}

}
