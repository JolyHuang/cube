package com.sharingif.cube.security.web.authentication;

import com.sharingif.cube.communication.http.HttpRequest;
import com.sharingif.cube.communication.http.HttpResponse;

/**
 *
 * @Description:  [用户安全退出]
 * @Author:       [Joly]
 * @CreateDate:   [2014年6月4日 下午11:50:41]
 * @UpdateUser:   [Joly]
 * @UpdateDate:   [2014年6月4日 下午11:50:41]
 * @UpdateRemark: [说明本次修改内容]
 * @Version:      [v1.0]
 *
 */
public interface ISignOutHandler {

	/**
	 * 处理用户登出
	 * @param request
	 * @param response
	 */
	void handleUserLogout(HttpRequest request, HttpResponse response);
	
}
