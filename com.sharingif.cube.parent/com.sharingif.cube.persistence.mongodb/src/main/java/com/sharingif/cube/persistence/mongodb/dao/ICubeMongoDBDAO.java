package com.sharingif.cube.persistence.mongodb.dao;

import com.sharingif.cube.persistence.database.dao.IBaseDAO;

import java.io.Serializable;
import java.util.List;

/**
 * MongoDB 基础接口类，提供基本的、增、删、改、查
 *
 * @author Joly
 * @version v1.0
 * @since v1.0
 * 2017/8/1 下午7:53
 */
public interface ICubeMongoDBDAO<T, ID extends Serializable> extends IBaseDAO<T, ID> {

    int insertList(List<T> objList);

}