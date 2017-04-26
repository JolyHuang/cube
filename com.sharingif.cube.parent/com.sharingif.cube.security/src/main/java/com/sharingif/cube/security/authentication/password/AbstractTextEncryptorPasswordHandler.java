package com.sharingif.cube.security.authentication.password;

import com.sharingif.cube.components.password.IPassword;
import com.sharingif.cube.core.exception.validation.ValidationCubeException;


/**   
 *  
 * @Description:  [验证密码]   
 * @Author:       [Joly]   
 * @CreateDate:   [2015年1月4日 下午8:50:35]   
 * @UpdateUser:   [Joly]   
 * @UpdateDate:   [2015年1月4日 下午8:50:35]   
 * @UpdateRemark: [说明本次修改内容]  
 * @Version:      [v1.0] 
 *    
 */
public abstract class AbstractTextEncryptorPasswordHandler extends TextEncryptorPasswordAuthentication implements IPasswordHandler {
	
	abstract protected IPassword getPassword(String username);
	
	@Override
	public void handlerPassword(String username, IPassword password) throws ValidationCubeException {
		super.passwordAuthentication(getPassword(username).getPassword(), password);
	}
	
}
