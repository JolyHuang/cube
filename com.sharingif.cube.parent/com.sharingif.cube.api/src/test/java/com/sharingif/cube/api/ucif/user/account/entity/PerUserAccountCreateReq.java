package com.sharingif.cube.api.ucif.user.account.entity;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * 资金账户创建请求
 * 2017/6/15 下午4:03
 *
 * @version v1.0
 * @since v1.0
 */
public class PerUserAccountCreateReq {

    /**
     * @aliasName 用户核心账号
     * @required Y
     * @desc 核心唯一编号，长度小于20
     */
    @NotEmpty
    private String userNo;

    /**
     * @aliasName 币种
     * @required Y
     * @desc USD:美元<br/>SGD:新加坡币
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
        return "AccountCreateReq{" +
                "userNo='" + userNo + '\'' +
                ", currency='" + currency + '\'' +
                '}';
    }
}
