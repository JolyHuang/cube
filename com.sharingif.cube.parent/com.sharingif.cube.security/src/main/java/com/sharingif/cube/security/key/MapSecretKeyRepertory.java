package com.sharingif.cube.security.key;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @Description:  [密钥储藏器]
 * @Author:       [Joly]
 * @CreateDate:   [2014年8月7日 下午1:17:33]
 * @UpdateUser:   [Joly]
 * @UpdateDate:   [2014年8月7日 下午1:17:33]
 * @UpdateRemark: [说明本次修改内容]
 * @Version:      [v1.0]
 *
 */
public class MapSecretKeyRepertory implements ISecretKeyRepertory{
	
	private Map<String, Object> secretKeyRepertory = new HashMap<String, Object>();
	
	public void setSecretKeyRepertory(Map<String, Object> secretKeyRepertory) {
		this.secretKeyRepertory = secretKeyRepertory;
	}

	@Override
	public void addSecretKey(String code, Object secretKey) {
		secretKeyRepertory.put(code, secretKey);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T getSecretKey(String code, Class<T> cla) {
		return (T) secretKeyRepertory.get(code);
	}
	
	

}
