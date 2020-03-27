package com.sharingif.cube.components.pagination;

import java.util.List;

/**
 * HttpPaginationRepertory
 *
 * @author Joly
 * @version v1.0
 * @since v1.0
 * 2018/4/15 下午5:02
 */
public class PaginationRepertory<T extends Object> {

    private int currentIndex = 0;
    private int totalCount = 0;
    private int totalPage;
    private List<T> pageItems;

    public PaginationRepertory() {

    }

    public PaginationRepertory(int totalCount, List<T> pageItems, PaginationCondition<? extends Object> paginationCondition){
        this.totalCount = totalCount;
        this.pageItems = pageItems;

        currentIndex = ((paginationCondition.getCurrentPage() - 1)*paginationCondition.getPageSize())+1;
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

}
