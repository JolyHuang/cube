package com.sharingif.cube.api.ucif.user.account.service;

import com.sharingif.cube.api.ucif.user.account.entity.*;
import com.sharingif.cube.core.handler.bind.annotation.RequestMapping;
import com.sharingif.cube.core.handler.bind.annotation.RequestMethod;

/**
 * @aliasName 资金账户服务
 * @desc 资金账户服务描述
 * 2017/6/15 下午12:11
 *
 * @author Huan.zhang
 * @version v1.0
 * @since v1.0
 */
@RequestMapping(value = "/", method = RequestMethod.POST)
public interface UserAccountService {

    /**
     * 用户资金账户创建
     * @param req
     * @return
     */
    @RequestMapping(value = "ucif/user/account/create.json", method = RequestMethod.POST)
    PerUserAccountCreateRsp userAcctCreate(PerUserAccountCreateReq req);

//    /**
//     * 用户资金账户基本信息查询
//     * @param req
//     * @return
//     */
//    @RequestMapping(value = "acct/person/query.json", method = RequestMethod.POST)
//    PerUserAccountInfoRsp userAcctInfo(PerUserAccountInfoReq req);

}
