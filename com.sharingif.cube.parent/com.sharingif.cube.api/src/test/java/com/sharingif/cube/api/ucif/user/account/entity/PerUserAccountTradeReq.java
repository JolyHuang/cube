package com.sharingif.cube.api.ucif.user.account.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;


/**
 * 个人资金账户明细查询 req
 * 2017/6/1 下午1:53
 *
 * @version v1.0
 * @since v1.0
 */
public class PerUserAccountTradeReq {

    /**
     * 用户核心账号
     * 核心的唯一ID
     */
    @JsonIgnore
    private String userNo;

    /**
     * 币种
     */
    private String currency;

    /**
     * 开始时间 格式"yyyy/MM/dd HH:mm:ss
     */
    private String startTime;

    /**
     * 结束时间 格式"yyyy/MM/dd HH:mm:ss
     */
    private String endTime;

    public String getUserNo() {
        return userNo;
    }

    public void setUserNo(String userNo) {
        this.userNo = userNo;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("AcctPersonDetailListQueryReq{");
        sb.append("userNo='").append(userNo).append('\'');
        sb.append(", currency='").append(currency).append('\'');
        sb.append(", startTime='").append(startTime).append('\'');
        sb.append(", endTime='").append(endTime).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
