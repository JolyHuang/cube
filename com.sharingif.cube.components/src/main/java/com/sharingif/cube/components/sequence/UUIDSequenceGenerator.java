package com.sharingif.cube.components.sequence;

import com.sharingif.cube.core.util.UUIDUtils;

/**
 *
 * @Description:  [uuid]
 * @Author:       [Joly_Huang]
 * @CreateDate:   [2014年5月12日 下午12:09:06]
 * @UpdateUser:   [Joly_Huang]
 * @UpdateDate:   [2014年5月12日 下午12:09:06]
 * @UpdateRemark: [说明本次修改内容]
 * @Version:      [v1.0]
 *
 */

public class UUIDSequenceGenerator extends AbstractSequenceGenerator<String> {

	@Override
	public String generateSequence() {
		return UUIDUtils.generateUUID();
	}


}
