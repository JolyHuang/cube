package com.sharingif.cube.persistence.database.pagination;


/**
 *
 * @Description:  [分页处理器]
 * @Author:       [Joly_Huang]
 * @CreateDate:   [2014年5月6日 下午11:26:33]
 * @UpdateUser:   [Joly_Huang]
 * @UpdateDate:   [2014年5月6日 下午11:26:33]
 * @UpdateRemark: [说明本次修改内容]
 * @Version:      [v1.0]
 *
 */

public interface IPaginationHandler {
	
	/**
	 * 根据当前页和每页大小获取分页参数
	 * @param currentPage
	 * @param pageSize
	 * @return
	 */
	RowBounds handleRowBounds(int currentPage, int pageSize);
	
}
