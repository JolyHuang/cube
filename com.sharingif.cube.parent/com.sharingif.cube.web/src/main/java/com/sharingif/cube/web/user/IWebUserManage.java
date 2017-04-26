package com.sharingif.cube.web.user;

import com.sharingif.cube.communication.http.HttpRequest;
import com.sharingif.cube.core.user.ICoreUser;

/**
 *
 * @Description:  [管理web user]
 * @Author:       [Joly_Huang]
 * @CreateDate:   [2014年3月28日 下午5:19:14]
 * @UpdateUser:   [Joly_Huang]
 * @UpdateDate:   [2014年3月28日 下午5:19:14]
 * @UpdateRemark: [说明本次修改内容]
 * @Version:      [v1.0]
 *
 */
public interface IWebUserManage {
	
	void persistenceUser(HttpRequest request, ICoreUser user);
	
	<T extends ICoreUser> T getUser(HttpRequest request);

}
