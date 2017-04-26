package com.sharingif.cube.security.authentication.password;

import com.sharingif.cube.components.password.IPassword;
import com.sharingif.cube.core.exception.validation.ValidationCubeException;
import com.sharingif.cube.security.confidentiality.encrypt.TextEncryptor;
import com.sharingif.cube.security.exception.validation.authentication.AuthenticationCubeException;


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
public class TextEncryptorPasswordAuthentication implements IPasswordAuthentication {
	
	private TextEncryptor textEncryptor;
	
	public TextEncryptor getTextEncryptor() {
		return textEncryptor;
	}
	public void setTextEncryptor(TextEncryptor textEncryptor) {
		this.textEncryptor = textEncryptor;
	}

	@Override
	public void passwordAuthentication(String originalPassword, IPassword password) throws ValidationCubeException {
		matchPassword(originalPassword, password.getPassword());
		clearPassword(password);
	}

	protected void matchPassword(String password, String originalPassword) {
		if(!(textEncryptor.matches(originalPassword, password)))
			throw new AuthenticationCubeException("Incorrect password, Please Try again");
	}
	
	/**
	 * 确认密码验证成功后删除,防止看到明文密码
	 * @param confirmPassword
	 */
	protected void clearPassword(IPassword password) {
		password.setPassword(null);
	}
	
}
