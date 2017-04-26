package com.sharingif.cube.components.payments.chinapay.p2b.key;

import java.net.URISyntaxException;

import chinapay.PrivateKey;

import com.sharingif.cube.core.exception.CubeRuntimeException;


/**
*
* @Description:  [unionpay private key]
* @Author:       [Joly]
* @CreateDate:   [2014年8月7日 下午3:43:39]
* @UpdateUser:   [Joly]
* @UpdateDate:   [2014年8月7日 下午3:43:39]
* @UpdateRemark: [说明本次修改内容]
* @Version:      [v1.0]
*
*/
public class ChinaPayKey extends PrivateKey {
	
	public ChinaPayKey(String merId, int keyUsage, boolean relativePath, String keyFile) throws URISyntaxException {
		super();
		
		// 是否是相对路径
		if(relativePath)
			keyFile = this.getClass().getClassLoader().getResource(keyFile).toURI().getPath();
		
		boolean flag = super.buildKey(merId, keyUsage, keyFile);
		
		if(!flag)
			throw new CubeRuntimeException("build unionpay key failure");
	}
	
}

