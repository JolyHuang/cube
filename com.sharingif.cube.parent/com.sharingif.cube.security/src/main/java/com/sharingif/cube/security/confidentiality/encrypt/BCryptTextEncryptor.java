package com.sharingif.cube.security.confidentiality.encrypt;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;



/**   
 *  
 * @Description:  [BCrypt加密]   
 * @Author:       [Joly_Huang]   
 * @CreateDate:   [2014年2月24日 下午4:26:00]   
 * @UpdateUser:   [Joly_Huang]   
 * @UpdateDate:   [2014年2月24日 下午4:26:00]   
 * @UpdateRemark: [说明本次修改内容]  
 * @Version:      [v1.0] 
 *    
 */
public class BCryptTextEncryptor implements TextEncryptor {
	
	private BCryptPasswordEncoder bcryptPasswordEncoder;
	
	public BCryptPasswordEncoder getBcryptPasswordEncoder() {
		return bcryptPasswordEncoder;
	}

	public void setBcryptPasswordEncoder(BCryptPasswordEncoder bcryptPasswordEncoder) {
		this.bcryptPasswordEncoder = bcryptPasswordEncoder;
	}

	public String encrypt(String text) {
		return bcryptPasswordEncoder.encode(text);
	}

	public boolean supportsDecrypt() {
		return false;
	}

	public String decrypt(String encryptedText) {
		return encryptedText;
	}

	@Override
	public boolean matches(String originalText, String encryptedText) {
		return bcryptPasswordEncoder.matches((CharSequence) originalText, encryptedText);
	}

}
