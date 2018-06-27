package com.sharingif.cube.security.confidentiality.encrypt;
/**   
 *  
 * @Description:  [文本加密]   
 * @Author:       [Joly_Huang]   
 * @CreateDate:   [2014年2月24日 下午4:18:30]   
 * @UpdateUser:   [Joly_Huang]   
 * @UpdateDate:   [2014年2月24日 下午4:18:30]   
 * @UpdateRemark: [说明本次修改内容]  
 * @Version:      [v1.0] 
 *    
 */
public interface TextEncryptor {

    /**
     * 加密
     * @param bytes
     * @return
     */
    byte[] encrypt(byte[] bytes);

	/**
	 * 加密
	 * @param text
	 * @return
	 */
    String encrypt(String text);

    /**
     * 是否支持解密
     * @return
     */
    boolean supportsDecrypt();

    /**
     * 解密
     * @param encryptedText
     * @return
     */
    String decrypt(String encryptedText);
    
    /**
     * 比较密码是否匹配
     * @param originalText ： 原始信息
     * @param encryptedText : 加密信息
     * @return
     */
    boolean matches(String originalText, String encryptedText);

}
