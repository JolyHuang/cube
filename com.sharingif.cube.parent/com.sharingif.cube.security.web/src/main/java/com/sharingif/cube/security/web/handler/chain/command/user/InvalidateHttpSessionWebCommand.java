package com.sharingif.cube.security.web.handler.chain.command.user;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sharingif.cube.communication.http.HttpRequest;
import com.sharingif.cube.communication.http.HttpResponse;
import com.sharingif.cube.communication.http.HttpSession;
import com.sharingif.cube.communication.http.request.HttpRequestContext;
import com.sharingif.cube.core.exception.CubeException;
import com.sharingif.cube.core.handler.chain.HandlerMethodContent;
import com.sharingif.cube.core.handler.chain.command.AbstractHandlerMethodCommand;
import com.sharingif.cube.security.web.access.NoUserHandlerImpl;

/**   
 *  
 * @Description:  [指明一个会话并将所有绑定其上的对象解绑]   
 * @Author:       [Joly_Huang]   
 * @CreateDate:   [2014年3月21日 下午4:13:27]   
 * @UpdateUser:   [Joly_Huang]   
 * @UpdateDate:   [2014年3月21日 下午4:13:27]   
 * @UpdateRemark: [说明本次修改内容]  
 * @Version:      [v1.0] 
 *    
 */
public class InvalidateHttpSessionWebCommand extends AbstractHandlerMethodCommand {
	
	private List<String> copyAttributeNames;
	
	public List<String> getCopyAttributeNames() {
		return copyAttributeNames;
	}
	public void setCopyAttributeNames(List<String> copyAttributeNames) {
		this.copyAttributeNames = copyAttributeNames;
		addDefaultAttributeNames();
	}

	public InvalidateHttpSessionWebCommand(){
		copyAttributeNames = new ArrayList<String>();
		addDefaultAttributeNames();
	}
	
	protected void addDefaultAttributeNames(){
		this.copyAttributeNames.add(NoUserHandlerImpl.REFERER);
	}

	@Override
	public void execute(HandlerMethodContent content) throws CubeException {
		HttpRequestContext<HttpRequest,HttpResponse> httpRequestContext = content.getRequestContext();
		
		HttpSession oldSession = httpRequestContext.getRequest().getSession(false);
		
		Map<String,Object> attributeMap = new HashMap<String,Object>(copyAttributeNames.size());
		if(null != oldSession){
			for(String attributeName : copyAttributeNames){
				attributeMap.put(attributeName, oldSession.getAttribute(attributeName));
			}
		}
		
		if(null != oldSession)
			oldSession.invalidate();
		
		HttpSession newSession = httpRequestContext.getRequest().getSession(true);
		for(String attributeName : copyAttributeNames){
			newSession.setAttribute(attributeName, attributeMap.get(attributeName));
		}
		
	}


}
