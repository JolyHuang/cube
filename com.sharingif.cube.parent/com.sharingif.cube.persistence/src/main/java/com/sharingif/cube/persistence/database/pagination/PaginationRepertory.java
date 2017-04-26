package com.sharingif.cube.persistence.database.pagination;

import java.io.Serializable;
import java.util.List;


/**
* 
* @Description: [分页仓库]
* @Author: [Joly]
* @CreateDate: [2014年1月15日 下午3:14:26]
* @UpdateUser: [Joly]
* @UpdateDate: [2014年1月15日 下午3:14:26]
* @UpdateRemark: [说明本次修改内容]
* @Version: [v1.0]
* 
*/
public class PaginationRepertory<T extends Object> implements Serializable {
	
	public static final String PAGINATION_REPERTORY = "paginationRepertory";
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4182627523452838269L;
	
	private int currentIndex = 0;
	private int totalCount = 0;
	private int totalPage;
	private List<T> pageItems;
	private PaginationCondition<? extends Object> paginationCondition;
	
	public PaginationRepertory(int totalCount, List<T> pageItems, PaginationCondition<? extends Object> paginationCondition){
		this.totalCount = totalCount;
		this.pageItems = pageItems;
		this.paginationCondition = paginationCondition;
		
		computeCurrentIndex();
		computeTotalPage();
	}

	public void computeCurrentIndex(){
		currentIndex = ((paginationCondition.getCurrentPage() - 1)*paginationCondition.getPageSize())+1;
	}

	public void computeTotalPage() {
		totalPage = (totalCount == 0) ? 1 : (((this.totalCount - 1)/ paginationCondition.getPageSize()) + 1);
	}

	public int getCurrentIndex() {
		return currentIndex;
	}

	public void setCurrentIndex(int currentIndex) {
		this.currentIndex = currentIndex;
	}

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	public int getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

	public List<T> getPageItems() {
		return pageItems;
	}

	public void setPageItems(List<T> pageItems) {
		this.pageItems = pageItems;
	}

	public PaginationCondition<? extends Object> getPaginationCondition() {
		return paginationCondition;
	}

	public void setPaginationCondition(
			PaginationCondition<? extends Object> paginationCondition) {
		this.paginationCondition = paginationCondition;
	}
	

	

}
