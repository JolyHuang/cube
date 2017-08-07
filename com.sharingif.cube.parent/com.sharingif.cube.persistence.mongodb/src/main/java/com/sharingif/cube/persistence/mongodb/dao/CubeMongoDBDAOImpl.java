package com.sharingif.cube.persistence.mongodb.dao;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import com.sharingif.cube.persistence.database.dao.AbstractBaseDAO;
import com.sharingif.cube.persistence.database.pagination.PaginationCondition;
import com.sharingif.cube.persistence.database.pagination.PaginationRepertory;

import java.beans.Introspector;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;

/**
 * MongoDB 基础接口类，提供基本的、增、删、改、查
 *
 * @author Joly
 * @version v1.0
 * @since v1.0
 * 2017/8/1 下午7:55
 */
public class CubeMongoDBDAOImpl<T, ID extends Serializable> extends AbstractBaseDAO<T, ID> implements ICubeMongoDBDAO<T, ID> {

    private String namespace = Introspector.decapitalize(((Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0]).getSimpleName());

    private MongoDatabase mongoDatabase;

    public void setMongoDatabase(MongoDatabase mongoDatabase) {
        this.mongoDatabase = mongoDatabase;
    }

    @Override
    public int insert(T obj) {
        return 0;
    }

    @Override
    public int deleteById(ID id) {
        return 0;
    }

    @Override
    public int deleteByCondition(T obj) {
        return 0;
    }

    @Override
    public int updateById(T obj) {
        return 0;
    }

    @Override
    public int updateByCondition(T update, T condition) {
        return 0;
    }

    @Override
    public T queryById(ID id) {
        return null;
    }

    @Override
    public T query(T obj) {
        return null;
    }

    @Override
    public List<T> queryList(T obj) {
        return null;
    }

    @Override
    public List<T> queryAll() {
        return null;
    }

    @Override
    public int queryPaginationCount(PaginationCondition<?> paginationCondition) {
        return 0;
    }

    @Override
    public PaginationRepertory<T> queryPagination(PaginationCondition<?> paginationCondition) {
        return null;
    }
}