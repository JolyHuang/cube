package com.sharingif.cube.components.monitor;

import java.util.Date;

/**
 * 对象时间操作历史
 * 2017/6/9 上午11:28
 * @author Joly
 * @version v1.0
 * @since v1.0
 */
public interface IObjectDateOperationHistory {

    void setCreateTime(Date value);

    Date getCreateTime();

    void setModifyTime(Date value);

    Date getModifyTime();

}
