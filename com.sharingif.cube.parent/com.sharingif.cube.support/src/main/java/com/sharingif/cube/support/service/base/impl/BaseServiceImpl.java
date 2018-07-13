package com.sharingif.cube.support.service.base.impl;

import java.io.Serializable;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sharingif.cube.persistence.database.dao.IBaseDAO;
import com.sharingif.cube.persistence.database.pagination.PaginationCondition;
import com.sharingif.cube.persistence.database.pagination.PaginationRepertory;
import com.sharingif.cube.support.service.base.IBaseService;

/**
 *
 * @Description:  [Base Action]
 * @Author:       [Joly_Huang]
 * @CreateDate:   [2014年5月13日 上午12:35:40]
 * @UpdateUser:   [Joly_Huang]
 * @UpdateDate:   [2014年5月13日 上午12:35:40]
 * @UpdateRemark: [说明本次修改内容]
 * @Version:      [v1.0]
 *
 */
public class BaseServiceImpl<T, ID extends Serializable> implements IBaseService<T, ID> {
	
	private IBaseDAO<T, ID> baseDAO;

	protected IBaseDAO<T, ID> getBaseDAO() {
		return baseDAO;
	}
	public void setBaseDAO(IBaseDAO<T, ID> baseDAO) {
		this.baseDAO = baseDAO;
	}
	
	@Override
	public int add(T obj) {
		return baseDAO.insert(obj);
	}
	
	@Override
	public int add(List<T> list) {
		int insertTotalNum=0;
		
		for(T obj : list){
			int i = add(obj);
			insertTotalNum = insertTotalNum+i;
		}
		
		return insertTotalNum;
	}
	
	@Override
	public int deleteById(ID id) {
		return baseDAO.deleteById(id);
	}
	
	@Override
	public int updateById(T obj) {
		return baseDAO.updateById(obj);
	}
	
	@Override
	public T getById(ID id) {
		return baseDAO.queryById(id);
	}
	
	@Override
	public List<T> getAll() {
		return baseDAO.queryAll();
	}
	
	@Override
	public PaginationRepertory<T> getPagination(PaginationCondition<T> paginationCondition) {
		return baseDAO.queryPagination(paginationCondition);
	}
	
}
