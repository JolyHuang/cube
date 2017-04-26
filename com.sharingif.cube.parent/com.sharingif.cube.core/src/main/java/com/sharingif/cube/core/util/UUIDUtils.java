package com.sharingif.cube.core.util;

import java.util.UUID;

/**
 *
 * @Description:  [UUID工具箱]
 * @Author:       [Joly_Huang]
 * @CreateDate:   [2014年5月12日 上午11:30:32]
 * @UpdateUser:   [Joly_Huang]
 * @UpdateDate:   [2014年5月12日 上午11:30:32]
 * @UpdateRemark: [说明本次修改内容]
 * @Version:      [v1.0]
 *
 */

public class UUIDUtils {
	
	/**
	 * 获得32位的UUID
	 * @return
	 */
	public static final String generateUUID(){
		return UUID.randomUUID().toString().replace("-", "").toUpperCase();
	}

}
