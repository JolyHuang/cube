package com.sharingif.cube.api.ucif.user.account.entity;

/**
 * 资金账户创建返回
 * 2017/6/15 下午4:03
 *
 * @version v1.0
 * @since v1.0
 */
public class PerUserAccountCreateRsp {

    /**
     * @aliasName 用户核心账号
     * @required Y
     * @desc 核心唯一编号，长度小于20
     */
    private String userNo;

    public String getUserNo() {
        return userNo;
    }

    public void setUserNo(String userNo) {
        this.userNo = userNo;
    }

    @Override
    public String toString() {
        return "AccountCreateRsp{" +
                "userNo='" + userNo + '\'' +
                '}';
    }
}
