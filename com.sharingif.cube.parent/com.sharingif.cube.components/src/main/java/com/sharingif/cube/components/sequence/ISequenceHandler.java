package com.sharingif.cube.components.sequence;

/**
 *
 * @Description:  [处理Sequence]
 * @Author:       [Joly_Huang]
 * @CreateDate:   [2014年5月12日 下午12:55:19]
 * @UpdateUser:   [Joly_Huang]
 * @UpdateDate:   [2014年5月12日 下午12:55:19]
 * @UpdateRemark: [说明本次修改内容]
 * @Version:      [v1.0]
 *
 */

public interface ISequenceHandler {
	
	<T> T handleSequence(T obj);

}
