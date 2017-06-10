package com.sharingif.cube.persistence.database.pagination;


/**
 *
 * @Description:  [DB2分页处理器]
 * @Author:       [Joly_Huang]
 * @CreateDate:   [2014年5月6日 下午11:57:59]
 * @UpdateUser:   [Joly_Huang]
 * @UpdateDate:   [2014年5月6日 下午11:57:59]
 * @UpdateRemark: [说明本次修改内容]
 * @Version:      [v1.0]
 *
 */

public class DB2PaginationHandler implements IPaginationHandler {

	@Override
	public RowBounds handleRowBounds(int currentPage, int pageSize) {
		RowBounds rowBounds = new RowBounds();
		rowBounds.setOffset(((currentPage-1)*pageSize)+1);
		rowBounds.setLimit((rowBounds.getOffset()+pageSize)-1);
		
		return rowBounds;
	}
	
	@Override
	public String createCountSql(String sql) {
		return null;
	}

	@Override
	public String createPaginationSql(String sql, RowBounds rowBounds) {
		return null;
	}

}
