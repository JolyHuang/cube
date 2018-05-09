package com.sharingif.cube.security.key;

import com.sharingif.cube.core.exception.CubeRuntimeException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
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
	public void addSecretKey(String code, String secretKeyPath) {
		byte[] secretKey;
		try {
			File file = new File(secretKeyPath);
			FileInputStream fileInputStream = new FileInputStream(file);
			int length = (int) file.length();
			secretKey = new byte[length];
			fileInputStream.read(secretKey);
		} catch (Exception e) {
			throw new CubeRuntimeException("file not found", e);
		}
		secretKeyRepertory.put(code, secretKey);
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
