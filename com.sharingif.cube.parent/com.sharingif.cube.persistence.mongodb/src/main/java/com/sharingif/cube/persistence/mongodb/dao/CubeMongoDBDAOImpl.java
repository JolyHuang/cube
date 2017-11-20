package com.sharingif.cube.persistence.mongodb.dao;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.sharingif.cube.persistence.mongodb.transport.transform.BsonTransform;
import org.bson.Document;

import com.sharingif.cube.core.transport.exception.MarshallerException;
import com.sharingif.cube.core.util.CubeExceptionUtil;
import com.sharingif.cube.persistence.database.dao.AbstractBaseDAO;
import com.sharingif.cube.persistence.database.pagination.PaginationCondition;
import com.sharingif.cube.persistence.database.pagination.PaginationRepertory;
import com.sharingif.cube.persistence.mongodb.transport.transform.DocumentTransform;
import org.bson.conversions.Bson;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
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

    @SuppressWarnings("unchecked")
	private String collectionName = ((Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0]).getSimpleName();

    private MongoDatabase mongoDatabase;
    private DocumentTransform<T> documentTransform;
    private BsonTransform<T> bsonTransform;

    public void setMongoDatabase(MongoDatabase mongoDatabase) {
        this.mongoDatabase = mongoDatabase;
    }

    public void setDocumentTransform(DocumentTransform<T> documentTransform) {
        this.documentTransform = documentTransform;
    }

    public void setBsonTransform(BsonTransform<T> bsonTransform) {
        this.bsonTransform = bsonTransform;
    }

    protected MongoCollection<Document> getCollection() {
        return mongoDatabase.getCollection(collectionName);
    }

    protected Document convertToDocument(T obj) {
        Document document = null;
        try {
            document = documentTransform.marshaller(obj);
        } catch (MarshallerException e) {
            CubeExceptionUtil.throwCubeRuntimeException(e);
        }
        return document;
    }

    protected T convertToObject(Document document) {
        T obj = null;
        try {
            obj = documentTransform.unmarshaller(document);
        } catch (MarshallerException e) {
            CubeExceptionUtil.throwCubeRuntimeException(e);
        }
        return obj;
    }

    protected Bson convertToBson(T obj) {
        Bson bson = null;

        try {
            bson = bsonTransform.marshaller(obj);
        } catch (MarshallerException e) {
            CubeExceptionUtil.throwCubeRuntimeException(e);
        }
        return bson;
    }

    @Override
    public int insert(T obj) {
        Document document = convertToDocument(obj);

        getCollection().insertOne(document);

        return 1;
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
        Document document = getCollection().find(convertToBson(obj)).first();

        return convertToObject(document);
    }

    @Override
    public List<T> queryList(T obj) {
        MongoCursor<Document> cursor = getCollection().find(convertToBson(obj)).iterator();

        List<T> objList = new ArrayList<T>();
        try {
            while (cursor.hasNext()) {
                objList.add(convertToObject(cursor.next()));
            }
        } finally {
            cursor.close();
        }

        return objList;
    }

    @Override
    public List<T> queryAll() {
        MongoCursor<Document> cursor = getCollection().find().iterator();

        List<T> objList = new ArrayList<T>();
        try {
            while (cursor.hasNext()) {
                objList.add(convertToObject(cursor.next()));
            }
        } finally {
            cursor.close();
        }

        return objList;
    }

    @Override
    public int queryPaginationCount(PaginationCondition<?> paginationCondition) {
        return 0;
    }

    @Override
    public PaginationRepertory<T> queryPagination(PaginationCondition<?> paginationCondition) {
        return null;
    }

    @Override
    public int insertList(List<T> objList) {
        List<Document> documents = new ArrayList<Document>(objList.size());

        for(T obj : objList) {
            documents.add(convertToDocument(obj));
        }

        getCollection().insertMany(documents);

        return objList.size();
    }
}