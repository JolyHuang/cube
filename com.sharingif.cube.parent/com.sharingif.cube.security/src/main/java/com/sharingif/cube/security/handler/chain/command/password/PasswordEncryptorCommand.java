package com.sharingif.cube.security.handler.chain.command.password;


import com.sharingif.cube.components.password.IPassword;
import com.sharingif.cube.core.exception.CubeException;
import com.sharingif.cube.core.handler.HandlerMethodContent;
import com.sharingif.cube.core.handler.chain.command.AbstractHandlerMethodCommand;
import com.sharingif.cube.security.confidentiality.encrypt.TextEncryptor;


/**   
 *  
 * @Description:  [密码字段加密]   
 * @Author:       [Joly_Huang]   
 * @CreateDate:   [2014年2月24日 下午4:30:01]   
 * @UpdateUser:   [Joly_Huang]   
 * @UpdateDate:   [2014年2月24日 下午4:30:01]   
 * @UpdateRemark: [说明本次修改内容]  
 * @Version:      [v1.0] 
 *    
 */
public class PasswordEncryptorCommand extends AbstractHandlerMethodCommand {
	
	private TextEncryptor textEncryptor;
	
	public TextEncryptor getTextEncryptor() {
		return textEncryptor;
	}
	
	public void setTextEncryptor(TextEncryptor textEncryptor) {
		this.textEncryptor = textEncryptor;
	}


	public void execute(HandlerMethodContent content) throws CubeException {
		IPassword password = content.getObject(IPassword.class);
		password.setPassword(textEncryptor.encrypt(password.getPassword()));
	}

}
