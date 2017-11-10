package com.sharingif.cube.api.ucif.user.account.entity;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * 个人资金账户余额查询服务 req
 * 2017/6/1 下午1:53
 *
 * @version v1.0
 * @since v1.0
 */
public class PerUserAccountInfoReq {

    /**
     * 用户核心账号
     * 核心的唯一ID
     */
    private String userNo;

    /**
     * 币种
     */
    @NotEmpty
    private String currency;

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

    @Override
    public String toString() {
        return "AcctPersonBalanceQueryReq{" +
                "userNo='" + userNo + '\'' +
                ", currency='" + currency + '\'' +
                '}';
    }
}
