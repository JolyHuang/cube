package com.sharingif.cube.api.ucif.user.account.entity;

import java.util.List;

/**
 * 个人资金账户余额查询服务 req
 * 2017/6/1 下午1:53
 *
 * @version v1.0
 * @since v1.0
 */
public class PerUserAccountTradeRsp {

    /**
     * 用户核心账号
     * 核心的唯一ID
     */
    private String userNo;

    /**
     * 币种
     */
    private String currency;

    /**
     * 明细列表
     */
    private List<PerUserAccount> dataList;

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

    public List<PerUserAccount> getDataList() {
        return dataList;
    }

    public void setDataList(List<PerUserAccount> dataList) {
        this.dataList = dataList;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("AcctPersonDetailListQueryRsp{");
        sb.append("userNo='").append(userNo).append('\'');
        sb.append(", currency='").append(currency).append('\'');
        sb.append(", dataList=").append(dataList);
        sb.append('}');
        return sb.toString();
    }
}
