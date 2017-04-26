package com.sharingif.cube.components.captcha;

import com.sharingif.cube.components.token.IToken;

/**   
 *  
 * @Description:  [Captcha Token]   
 * @Author:       [Joly]   
 * @CreateDate:   [2014年12月21日 下午3:28:43]   
 * @UpdateUser:   [Joly]   
 * @UpdateDate:   [2014年12月21日 下午3:28:43]   
 * @UpdateRemark: [说明本次修改内容]  
 * @Version:      [v1.0] 
 *    
 */
public class CaptchaToken {
	
	public CaptchaToken(IToken token, byte[] uniqueId){
		this.token = token;
		this.uniqueId = uniqueId;
	}
	
	private IToken token;
	private byte[] uniqueId;
	
	public IToken getToken() {
		return token;
	}
	public byte[] getUniqueId() {
		return uniqueId;
	}
	
}
