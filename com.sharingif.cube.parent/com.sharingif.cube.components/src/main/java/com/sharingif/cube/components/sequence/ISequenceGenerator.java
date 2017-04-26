package com.sharingif.cube.components.sequence;

/**
 *
 * @Description:  [序列生成器]
 * @Author:       [Joly_Huang]
 * @CreateDate:   [2014年5月12日 下午12:01:55]
 * @UpdateUser:   [Joly_Huang]
 * @UpdateDate:   [2014年5月12日 下午12:01:55]
 * @UpdateRemark: [说明本次修改内容]
 * @Version:      [v1.0]
 *
 */

public interface ISequenceGenerator<ID extends Object> {
	
	
	/**
	 * 生成序列
	 * @return
	 */
	ID  generateSequence();
	
}
