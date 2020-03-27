package com.sharingif.cube.components.pagination;

import javax.validation.Valid;

/**
 * HttpPaginationCondition
 *
 * @author Joly
 * @version v1.0
 * @since v1.0
 * 2018/4/15 下午5:04
 */
public class PaginationCondition<T extends Object> {

    private int pageSize;
    private int currentPage;
    @Valid
    private T condition;

    public PaginationCondition() {

    }

    public PaginationCondition(int pageSize, int currentPage, T condition){
        this.pageSize = pageSize;
        this.currentPage = currentPage;
        this.condition = condition;
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

}
