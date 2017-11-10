package com.sharingif.cube.api.ucif.user.account.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Date;

/**
 * 个人资金账户余额查询服务 rsp
 * 2017/6/1 下午1:53
 *
 * @version v1.0
 * @since v1.0
 */
public class PerUserAccountInfoRsp {

    public static final String ACCT_STATUS_NOMAL = "0";
    public static final String ACCT_STATUS_FROZEN = "1";

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
     * 账户总金额
     */
    private String totalAmount;


    /**
     * 账户可用金额
     */
    private String canUseBalance;

    /**
     * 账户冻结金额
     */
    private String frozenAmount;

    /**
     * 账户状态
     * 0-正常；1-冻结
     */
    private String acctStatus;

    /**
     * 开户日期
     */
    @JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss")
    private Date createTime;

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

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getCanUseBalance() {
        return canUseBalance;
    }

    public void setCanUseBalance(String canUseBalance) {
        this.canUseBalance = canUseBalance;
    }

    public String getFrozenAmount() {
        return frozenAmount;
    }

    public void setFrozenAmount(String frozenAmount) {
        this.frozenAmount = frozenAmount;
    }

    public String getAcctStatus() {
        return acctStatus;
    }

    public void setAcctStatus(String acctStatus) {
        this.acctStatus = acctStatus;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "PerUserAccountInfoRsp{" +
                "userNo='" + userNo + '\'' +
                ", currency='" + currency + '\'' +
                ", totalAmount='" + totalAmount + '\'' +
                ", canUseBalance='" + canUseBalance + '\'' +
                ", frozenAmount='" + frozenAmount + '\'' +
                ", acctStatus='" + acctStatus + '\'' +
                ", createTime='" + createTime + '\'' +
                '}';
    }
}
