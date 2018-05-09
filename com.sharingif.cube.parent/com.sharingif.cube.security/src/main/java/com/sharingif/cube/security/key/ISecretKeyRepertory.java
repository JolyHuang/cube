package com.sharingif.cube.security.key;

/**
 *
 * @Description:  [密钥储藏器]
 * @Author:       [Joly]
 * @CreateDate:   [2014年8月7日 下午1:21:33]
 * @UpdateUser:   [Joly]
 * @UpdateDate:   [2014年8月7日 下午1:21:33]
 * @UpdateRemark: [说明本次修改内容]
 * @Version:      [v1.0]
 *
 */
public interface ISecretKeyRepertory {

	/**
	 * 添加密钥
	 * @param code : 密钥代码
	 * @param secretKeyPath : 密钥
	 */
	void addSecretKey(String code, String secretKeyPath);

	/**
	 * 添加密钥
	 * @param code : 密钥代码
	 * @param secretKey : 密钥
	 */
	void addSecretKey(String code, Object secretKey);
	
	/**
	 * 获取密钥
	 * @param code : 密钥代码
	 * @param cla : 密钥类型
	 * @return
	 */
	<T> T getSecretKey(String code, Class<T> cla);

}
