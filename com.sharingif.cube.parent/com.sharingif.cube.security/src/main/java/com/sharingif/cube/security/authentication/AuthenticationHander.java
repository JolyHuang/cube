package com.sharingif.cube.security.authentication;

import com.sharingif.cube.components.channel.IChannelContext;
import com.sharingif.cube.core.user.ICoreUser;
import com.sharingif.cube.security.exception.validation.authentication.AuthenticationCubeException;

/**
 *
 * @Description:  [处理验证]
 * @Author:       [Joly_Huang]
 * @CreateDate:   [2014年4月7日 下午10:30:28]
 * @UpdateUser:   [Joly_Huang]
 * @UpdateDate:   [2014年4月7日 下午10:30:28]
 * @UpdateRemark: [说明本次修改内容]
 * @Version:      [v1.0]
 *
 */
public interface AuthenticationHander<U extends ICoreUser, C extends IChannelContext> {

	void handerAuthentication(U user, C channelContext) throws AuthenticationCubeException;
	
}
