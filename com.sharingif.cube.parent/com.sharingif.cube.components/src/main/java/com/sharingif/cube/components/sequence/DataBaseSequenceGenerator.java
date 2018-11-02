package com.sharingif.cube.components.sequence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sharingif.cube.core.exception.CubeRuntimeException;

/**
 * 数据库方式生成序列
 * 2017/5/29 下午12:44
 * @author Joly
 * @version v1.0
 * @since v1.0
 */
public class DataBaseSequenceGenerator extends AbstractSequenceGenerator<String> {

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    private long currentId;
    private long maxCacheId;
    private long maxId;

    private long startId;
    private int step;
    private String type;
    private int length;

    private String selectSql;
    private String updateSql;
    private DataSource dataSource;

    public DataBaseSequenceGenerator(long startId,int step,String type,int length,String selectSql,String updateSql,DataSource dataSource) {
        this.startId = startId;
        this.step = step;
        this.type = type;
        this.length = length;
        this.maxId = ((long)Math.pow(10, this.length));

        this.selectSql = selectSql;
        this.updateSql = updateSql;
        this.dataSource = dataSource;

        this.getNextIdFromDb();
    }

    @Override
    public synchronized String generateSequence() {
        currentId++;

        if (currentId >= maxCacheId) {
            getNextIdFromDb();
        }

        String str = Long.toString(currentId);

        StringBuffer strbuf = new StringBuffer();
        for (int i = 0; i < length - str.length(); i++) {
            strbuf.append("0");
        }
        strbuf.append(str);

        return strbuf.toString();
    }

	protected void getNextIdFromDb() {
        Connection conn = null;
        PreparedStatement preparedstatement = null;

        try {
            conn = dataSource.getConnection();
            preparedstatement = conn.prepareStatement(selectSql);
            preparedstatement.clearBatch();
            conn.setAutoCommit(false);
            preparedstatement.setString(1, type);

            ResultSet rs = preparedstatement.executeQuery();
            if (!rs.next()) {
                logger.error("IdFactory type error [type={}]",type);
                throw new CubeRuntimeException("IdFactory type error");
            } else {

                currentId = rs.getLong(1);
                maxCacheId = currentId + step;

                //超过最大值，重置currentId = 1;maxCacheId=currentId + step;
                if(maxCacheId >= maxId){
                    currentId = startId;
                    maxCacheId = currentId + step;
                }

                preparedstatement = conn.prepareStatement(updateSql);
                preparedstatement.clearBatch();
                preparedstatement.setLong(1, maxCacheId);
                preparedstatement.setTimestamp(2, new Timestamp(System.currentTimeMillis()));
                preparedstatement.setString(3, type);
                preparedstatement.executeUpdate();
            }

            conn.commit();

        } catch (Exception e) {
            logger.error("generate unique id error [type={}]",type, e);

            try {
                conn.rollback();
            } catch (Exception ee) {
                logger.error("connection rollback error",ee);
            }

            throw new CubeRuntimeException("generate unique id error",e);
        } finally {
            if (preparedstatement != null)
                try {
                    preparedstatement.close();
                } catch (Exception e) {
                    logger.error("close preparedstatement error", e);
                }
            if (conn != null)
                try {
                    conn.setAutoCommit(true);
                    conn.close();
                } catch (Exception e) {
                    logger.error("close connection error",e);
                }

        }
    }

}
