package com.sharingif.cube.security.authentication.password;

import com.sharingif.cube.components.password.IOriginalPassword;
import com.sharingif.cube.core.exception.validation.ValidationCubeException;
import com.sharingif.cube.security.confidentiality.encrypt.TextEncryptor;
import com.sharingif.cube.security.exception.validation.authentication.AuthenticationCubeException;


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
public abstract class TextEncryptorOriginalPasswordAuthentication implements IOriginalPasswordAuthentication {
	
	private TextEncryptor textEncryptor;
	
	public TextEncryptor getTextEncryptor() {
		return textEncryptor;
	}
	public void setTextEncryptor(TextEncryptor textEncryptor) {
		this.textEncryptor = textEncryptor;
	}
	
	@Override
	public void originalPasswordAuthentication(String password, IOriginalPassword originalPassword) throws ValidationCubeException {
		matchOriginalPassword(password, originalPassword.getOriginalPassword());
		clearConfirmPassword(originalPassword);
	}
	
	protected void matchOriginalPassword(String password, String originalPassword) {
		if(!(textEncryptor.matches(originalPassword, password)))
			throw new AuthenticationCubeException("Incorrect old password, Please Try again");
	}
	
	/**
	 * 确认密码验证成功后删除,防止看到明文密码
	 * @param confirmPassword
	 */
	protected void clearConfirmPassword(IOriginalPassword originalPassword) {
		originalPassword.setOriginalPassword(null);
	}

}
