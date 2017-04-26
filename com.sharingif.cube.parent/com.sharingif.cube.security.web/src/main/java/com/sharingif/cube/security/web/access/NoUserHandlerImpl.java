package com.sharingif.cube.security.web.access;


import com.sharingif.cube.communication.http.HttpRequest;
import com.sharingif.cube.core.user.ICoreUser;
import com.sharingif.cube.security.exception.validation.access.NoUserAccessDecisionCubeException;
import com.sharingif.cube.web.user.IWebUserManage;

/**
 *
 * @Description:  [处理空用户]
 * @Author:       [Joly_Huang]
 * @CreateDate:   [2014年3月31日 下午4:48:31]
 * @UpdateUser:   [Joly_Huang]
 * @UpdateDate:   [2014年3月31日 下午4:48:31]
 * @UpdateRemark: [说明本次修改内容]
 * @Version:      [v1.0]
 *
 */
public class NoUserHandlerImpl implements INoUserHandler {
	
	public static final String REFERER = ICoreUser.class.getName()+ "._referer";
	
	private IWebUserManage webUserManage;
	
	public IWebUserManage getWebUserManage() {
		return webUserManage;
	}
	public void setWebUserManage(IWebUserManage webUserManage) {
		this.webUserManage = webUserManage;
	}


	/**
	 * 处理用户未登录
	 * @param request
	 * @throws NoUserAccessDecisionCubeException
	 */
	@Override
	public void handleNoUser(HttpRequest request) throws NoUserAccessDecisionCubeException {
		ICoreUser coreUser = webUserManage.getUser(request);
		
		if(null != coreUser)
			return;
		
		// 如果未登录，登录成功后返回原页面
		String referer = request.getHeader("Referer");
		
		if(null == referer)
			referer = (null == request.getQueryString() ? request.getRequestURL().toString() : (request.getRequestURL().append("?").append(request.getQueryString()).toString()));
			
		referer = new StringBuilder("redirect:").append(referer).toString();
		request.getSession().setAttribute(REFERER, referer);
		
		throw new NoUserAccessDecisionCubeException();
			
	}
	
	/**
	 * 处理用户未登录回跳url
	 * @param request
	 */
	@Override
	public String handleReferer(HttpRequest request) {
		String referer = (String)request.getSession().getAttribute(REFERER);
		request.getSession().removeAttribute(REFERER);
		return referer;
	}

}
