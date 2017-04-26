package com.sharingif.cube.security.web.access;

import com.sharingif.cube.communication.http.HttpRequest;
import com.sharingif.cube.security.exception.validation.access.NoUserAccessDecisionCubeException;


/**
 *
 * @Description:  [处理空用户]
 * @Author:       [Joly_Huang]
 * @CreateDate:   [2014年3月31日 下午4:46:38]
 * @UpdateUser:   [Joly_Huang]
 * @UpdateDate:   [2014年3月31日 下午4:46:38]
 * @UpdateRemark: [说明本次修改内容]
 * @Version:      [v1.0]
 *
 */
public interface INoUserHandler {

	/**
	 * 处理用户未登录
	 * @param request
	 * @throws NoUserAccessDecisionCubeException
	 */
	void handleNoUser(HttpRequest request) throws NoUserAccessDecisionCubeException;
	
	/**
	 * 处理用户未登录回跳url
	 * @param request
	 */
	String handleReferer(HttpRequest request);
	
}
