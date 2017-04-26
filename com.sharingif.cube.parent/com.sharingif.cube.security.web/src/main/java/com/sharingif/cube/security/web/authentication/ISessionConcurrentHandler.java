package com.sharingif.cube.security.web.authentication;

import com.sharingif.cube.communication.http.HttpRequest;
import com.sharingif.cube.communication.http.HttpResponse;
import com.sharingif.cube.core.user.ICoreUser;
import com.sharingif.cube.security.exception.validation.authentication.AuthenticationCubeException;

/**
 *
 * @Description:  [控制session并发]
 * @Author:       [Joly_Huang]
 * @CreateDate:   [2014年4月4日 上午11:20:43]
 * @UpdateUser:   [Joly_Huang]
 * @UpdateDate:   [2014年4月4日 上午11:20:43]
 * @UpdateRemark: [说明本次修改内容]
 * @Version:      [v1.0]
 *
 */
public interface ISessionConcurrentHandler {
	
	void handleSessionConcurrent(ICoreUser coreUser, HttpRequest request, HttpResponse response) throws AuthenticationCubeException;

}
