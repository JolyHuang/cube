package com.sharingif.cube.persistence.database.pagination;


/**
 *
 * @Description:  [MySql分页处理器]
 * @Author:       [Joly_Huang]
 * @CreateDate:   [2014年5月6日 下午11:57:59]
 * @UpdateUser:   [Joly_Huang]
 * @UpdateDate:   [2014年5月6日 下午11:57:59]
 * @UpdateRemark: [说明本次修改内容]
 * @Version:      [v1.0]
 *
 */

public class MySqlPaginationHandler implements IPaginationHandler {

	@Override
	public RowBounds handleRowBounds(int currentPage, int pageSize) {
		RowBounds rowBounds = new RowBounds();
		rowBounds.setOffset(((currentPage-1)*pageSize));
		rowBounds.setLimit(pageSize);
		
		return rowBounds;
	}


}
