package com.sharingif.cube.persistence.database.pagination;

/**
 *
 * @Description:  [数据库分页]
 * @Author:       [Joly_Huang]
 * @CreateDate:   [2014年5月6日 下午11:28:24]
 * @UpdateUser:   [Joly_Huang]
 * @UpdateDate:   [2014年5月6日 下午11:28:24]
 * @UpdateRemark: [说明本次修改内容]
 * @Version:      [v1.0]
 *
 */

public class RowBounds {
	
	private int offset;
    private int limit;
    
	public int getOffset() {
		return offset;
	}
	public void setOffset(int offset) {
		this.offset = offset;
	}
	public int getLimit() {
		return limit;
	}
	public void setLimit(int limit) {
		this.limit = limit;
	}

	
}
