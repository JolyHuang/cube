package com.sharingif.cube.components.monitor;

/**
 * 对象用户操作历史
 * 2017/6/9 上午11:29
 * @author Joly
 * @version v1.0
 * @since v1.0
 */
public interface IObjectUserOperationHistory {

    void setCreateUser(String value);

    String getCreateUser();

    void setModifyUser(String value);

    String getModifyUser();

}
