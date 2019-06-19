package com.sharingif.cube.batch.core;

import java.util.Date;

/**
 * job返回信息
 *
 * @author Joly
 * @version v1.0
 * @since v1.0
 * 2017/11/28 下午6:10
 */
public class JobModel {

    /**
     * 交易名
     */
    private String lookupPath;
    /**
     * 计划执行时间
     */
    private Date planExecuteTime;
    /**
     * 数据id
     */
    private String dataId;

    public String getLookupPath() {
        return lookupPath;
    }

    public void setLookupPath(String lookupPath) {
        this.lookupPath = lookupPath;
    }

    public Date getPlanExecuteTime() {
        return planExecuteTime;
    }

    public void setPlanExecuteTime(Date planExecuteTime) {
        this.planExecuteTime = planExecuteTime;
    }

    public String getDataId() {
        return dataId;
    }

    public void setDataId(String dataId) {
        this.dataId = dataId;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("JobModel{");
        sb.append("lookupPath='").append(lookupPath).append('\'');
        sb.append(", planExecuteTime=").append(planExecuteTime);
        sb.append(", dataId='").append(dataId).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
