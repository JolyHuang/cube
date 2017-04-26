package com.sharingif.cube.persistence.database.dao;

import java.io.Serializable;
import java.util.List;

import com.sharingif.cube.persistence.database.pagination.PaginationCondition;
import com.sharingif.cube.persistence.database.pagination.PaginationRepertory;

/**
 *
 * @Description:  [Base DAO]
 * @Author:       [Joly_Huang]
 * @CreateDate:   [2014年5月13日 下午2:24:42]
 * @UpdateUser:   [Joly_Huang]
 * @UpdateDate:   [2014年5月13日 下午2:24:42]
 * @UpdateRemark: [说明本次修改内容]
 * @Version:      [v1.0]
 *
 */

public interface IBaseDAO <T, ID extends Serializable>{
	
	int insert(T obj);
	
	int deleteById(ID id);
	
	int deleteByCondition(T obj);
	
	int updateById(T obj);
	
	int updateByCondition(T update, T condition);
	
	T queryById(ID id);
	
	T query(T obj);
	
	List<T> queryList(T obj);
	
	List<T> queryAll();
	
	/**
	 * 分页总条数
	 * @param paginationCondition
	 * @return
	 */
	int queryPaginationCount(PaginationCondition<? extends Object> paginationCondition);
	/**
	 * 分页查询
	 * @param paginationCondition : 查询条件
	 * @return
	 */
	PaginationRepertory<T> queryPagination(PaginationCondition<? extends Object> paginationCondition);
	/**
	 * 分页查询
	 * @param paginationCondition : 查询条件
	 * @param isQueryCount : 是否查询总条数
	 * @return
	 */
	PaginationRepertory<T> queryPagination(PaginationCondition<? extends Object> paginationCondition, boolean isQueryCount);

}
