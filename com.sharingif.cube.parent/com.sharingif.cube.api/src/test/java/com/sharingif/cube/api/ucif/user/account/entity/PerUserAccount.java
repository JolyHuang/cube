package com.sharingif.cube.api.ucif.user.account.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

/**
 * 账户明细条目
 * 2017/6/1 下午3:36
 *
 * @version v1.0
 * @since v1.0
 */
public class PerUserAccount {

    @JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss")
    private Date changeTime;
    private String tradeNo;
    private String tradeType;
    private String tradeAmount;
    private String acctBalance;
    private String fundFlow;
    private String tradeAbstract;

    public Date getChangeTime() {
        return changeTime;
    }

    public void setChangeTime(Date changeTime) {
        this.changeTime = changeTime;
    }

    public String getTradeNo() {
        return tradeNo;
    }

    public void setTradeNo(String tradeNo) {
        this.tradeNo = tradeNo;
    }

    public String getTradeType() {
        return tradeType;
    }

    public void setTradeType(String tradeType) {
        this.tradeType = tradeType;
    }

    public String getTradeAmount() {
        return tradeAmount;
    }

    public void setTradeAmount(String tradeAmount) {
        this.tradeAmount = tradeAmount;
    }

    public String getAcctBalance() {
        return acctBalance;
    }

    public void setAcctBalance(String acctBalance) {
        this.acctBalance = acctBalance;
    }

    public String getFundFlow() {
        return fundFlow;
    }

    public void setFundFlow(String fundFlow) {
        this.fundFlow = fundFlow;
    }

    public String getTradeAbstract() {
        return tradeAbstract;
    }

    public void setTradeAbstract(String tradeAbstract) {
        this.tradeAbstract = tradeAbstract;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("AcctPersonDetail{");
        sb.append("changeTime=").append(changeTime);
        sb.append(", tradeNo='").append(tradeNo).append('\'');
        sb.append(", tradeType='").append(tradeType).append('\'');
        sb.append(", tradeAmount=").append(tradeAmount);
        sb.append(", acctBalance=").append(acctBalance);
        sb.append(", fundFlow='").append(fundFlow).append('\'');
        sb.append(", tradeAbstract='").append(tradeAbstract).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
