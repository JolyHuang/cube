package com.sharingif.cube.support.service.base;

import java.io.Serializable;
import java.util.List;

import com.sharingif.cube.persistence.database.dao.IBaseDAO;
import com.sharingif.cube.persistence.database.pagination.PaginationCondition;
import com.sharingif.cube.persistence.database.pagination.PaginationRepertory;


/**
 *
 * @Description:  [Base Action]
 * @Author:       [Joly_Huang]
 * @CreateDate:   [2014年5月13日 上午12:33:56]
 * @UpdateUser:   [Joly_Huang]
 * @UpdateDate:   [2014年5月13日 上午12:33:56]
 * @UpdateRemark: [说明本次修改内容]
 * @Version:      [v1.0]
 *
 */
public interface IBaseService<T, ID extends Serializable> {
	
	void setBaseDAO(IBaseDAO<T, ID> baseDAO);
	
	int add(T obj);
	
	int add(List<T> list);
	
	int deleteById(ID id);
	
	int updateById(T obj);
	
	T getById(ID id);
	
	List<T> getAll();
	
	PaginationRepertory<T> getPagination(PaginationCondition<T> paginationCondition);
	
}
