package com.sharingif.cube.security.web.access;

import com.sharingif.cube.communication.http.HttpRequest;
import com.sharingif.cube.security.exception.validation.access.SessionExpireAccessDecisionCubeException;

/**
 *
 * @Description:  [处理用户失效]
 * @Author:       [Joly_Huang]
 * @CreateDate:   [2014年3月31日 下午4:25:55]
 * @UpdateUser:   [Joly_Huang]
 * @UpdateDate:   [2014年3月31日 下午4:25:55]
 * @UpdateRemark: [说明本次修改内容]
 * @Version:      [v1.0]
 *
 */
public interface ISessionExpireHandler {
	
	void handleUserExpire(HttpRequest request) throws SessionExpireAccessDecisionCubeException;

}
