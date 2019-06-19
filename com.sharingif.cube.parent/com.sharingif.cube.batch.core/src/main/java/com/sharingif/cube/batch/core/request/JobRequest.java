package com.sharingif.cube.batch.core.request;

import java.util.Date;
import java.util.Objects;

/**
 * job请求信息
 *
 * @author Joly
 * @version v1.0
 * @since v1.0
 * 2017/11/21 下午3:28
 */
public class JobRequest<T> {

    /**
     * 父级job id
     */
    private String parentJobId;
    /**
     * 主键id
     */
    private String id;
    /**
     * 交易名
     */
    private String lookupPath;
    /**
     * 计划执行时间
     */
    private Date planExecuteTime;
    /**
     * 执行次数
     */
    private Integer executeCount;
    /**
     * 创建时间
     */
    private Date createtime;
    /**
     * 数据
     */
    private T data;

    public String getParentJobId() {
        return parentJobId;
    }

    public void setParentJobId(String parentJobId) {
        this.parentJobId = parentJobId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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

    public Integer getExecuteCount() {
        return executeCount;
    }

    public void setExecuteCount(Integer executeCount) {
        this.executeCount = executeCount;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        JobRequest<?> that = (JobRequest<?>) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("JobRequest{");
        sb.append("parentJobId='").append(parentJobId).append('\'');
        sb.append(", id='").append(id).append('\'');
        sb.append(", lookupPath='").append(lookupPath).append('\'');
        sb.append(", planExecuteTime=").append(planExecuteTime);
        sb.append(", executeCount=").append(executeCount);
        sb.append(", createtime=").append(createtime);
        sb.append(", data=").append(data);
        sb.append('}');
        return sb.toString();
    }
}
