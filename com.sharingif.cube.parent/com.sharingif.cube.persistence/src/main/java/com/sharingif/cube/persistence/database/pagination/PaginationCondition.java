package com.sharingif.cube.persistence.database.pagination;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @Description:  [分页查询条件]
 * @Author:       [Joly_Huang]
 * @CreateDate:   [2014年5月7日 上午11:25:08]
 * @UpdateUser:   [Joly_Huang]
 * @UpdateDate:   [2014年5月7日 上午11:25:08]
 * @UpdateRemark: [说明本次修改内容]
 * @Version:      [v1.0]
 *
 */

public class PaginationCondition<T extends Object> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8880915750336543554L;
	
	public static final int DEFAULT_CURRENT_PAGE = 1;
	public static final int DEFAULT_PAGE_SIZE = 20;
	public static final boolean DEFAULT_ISQUERY_COUNT = true;
	
	private int pageSize = DEFAULT_PAGE_SIZE;
	private int currentPage = DEFAULT_CURRENT_PAGE;
	private T condition;
	private List<String> sort;
	private RowBounds rowBounds;
	private boolean isQueryCount = DEFAULT_ISQUERY_COUNT;
	private boolean closeMaxDefaultPageSize = false;
	
	public PaginationCondition(){
		
	}
	public PaginationCondition(int pageSize, int currentPage, List<String> sort, T condition, boolean isQueryCount){
		this.pageSize = pageSize;
		this.currentPage = currentPage;
		this.sort = sort;
		this.condition = condition;
		this.isQueryCount = isQueryCount;
	}
	
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public int getCurrentPage() {
		return currentPage;
	}
	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}
	public T getCondition() {
		return condition;
	}
	public void setCondition(T condition) {
		this.condition = condition;
	}
	public List<String> getSort() {
		return sort;
	}
	public void setSort(List<String> sort) {
		this.sort = sort;
	}
	public RowBounds getRowBounds() {
		return rowBounds;
	}
	public void setRowBounds(RowBounds rowBounds) {
		this.rowBounds = rowBounds;
	}
	public boolean isQueryCount() {
		return isQueryCount;
	}
	public void setQueryCount(boolean isQueryCount) {
		this.isQueryCount = isQueryCount;
	}

	public boolean isCloseMaxDefaultPageSize() {
		return closeMaxDefaultPageSize;
	}

	public void setCloseMaxDefaultPageSize(boolean closeMaxDefaultPageSize) {
		this.closeMaxDefaultPageSize = closeMaxDefaultPageSize;
	}
}
