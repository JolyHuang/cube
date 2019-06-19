package com.sharingif.cube.batch.core;

public class JobConfig {

    public JobConfig(String preLookupPath, String lookupPath, String nextLookupPath) {
        this.preLookupPath = preLookupPath;
        this.lookupPath = lookupPath;
        this.nextLookupPath = nextLookupPath;
        this.maxExecuteCount = 1;
    }

    /**
     * 上一步交易名
     */
    private String preLookupPath;
    /**
     * 下一个交易
     */
    private String nextLookupPath;
    /**
     * 交易名
     */
    private String lookupPath;
    /**
     * 最大执行次数
     */
    private Integer maxExecuteCount;
    /**
     * 执行间隔时间
     */
    private Integer intervalPlanExecuteTime;

    public String getPreLookupPath() {
        return preLookupPath;
    }

    public void setPreLookupPath(String preLookupPath) {
        this.preLookupPath = preLookupPath;
    }

    public String getNextLookupPath() {
        return nextLookupPath;
    }

    public void setNextLookupPath(String nextLookupPath) {
        this.nextLookupPath = nextLookupPath;
    }

    public String getLookupPath() {
        return lookupPath;
    }

    public void setLookupPath(String lookupPath) {
        this.lookupPath = lookupPath;
    }

    public Integer getMaxExecuteCount() {
        return maxExecuteCount;
    }

    public void setMaxExecuteCount(Integer maxExecuteCount) {
        this.maxExecuteCount = maxExecuteCount;
    }

    public Integer getIntervalPlanExecuteTime() {
        return intervalPlanExecuteTime;
    }

    public void setIntervalPlanExecuteTime(Integer intervalPlanExecuteTime) {
        this.intervalPlanExecuteTime = intervalPlanExecuteTime;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("JobConfig{");
        sb.append("preLookupPath='").append(preLookupPath).append('\'');
        sb.append(", nextLookupPath='").append(nextLookupPath).append('\'');
        sb.append(", lookupPath='").append(lookupPath).append('\'');
        sb.append(", maxExecuteCount=").append(maxExecuteCount);
        sb.append(", intervalPlanExecuteTime=").append(intervalPlanExecuteTime);
        sb.append('}');
        return sb.toString();
    }
}
