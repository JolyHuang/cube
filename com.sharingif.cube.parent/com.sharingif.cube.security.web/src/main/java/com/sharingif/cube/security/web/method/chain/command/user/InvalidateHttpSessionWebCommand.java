package com.sharingif.cube.security.web.method.chain.command.user;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sharingif.cube.communication.http.HttpSession;
import com.sharingif.cube.communication.http.method.HttpHandlerMethodContent;
import com.sharingif.cube.core.exception.CubeException;
import com.sharingif.cube.security.web.access.NoUserHandlerImpl;
import com.sharingif.cube.web.method.chain.command.AbstractWebHandlerMethodCommand;

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
public class InvalidateHttpSessionWebCommand extends AbstractWebHandlerMethodCommand {
	
	private List<String> copyAttributeNames;
	
	public List<String> getCopyAttributeNames() {
		return copyAttributeNames;
	}
	public void setCopyAttributeNames(List<String> copyAttributeNames) {
		this.copyAttributeNames = copyAttributeNames;
		addDefaultAttributeNames(copyAttributeNames);
	}

	public InvalidateHttpSessionWebCommand(){
		copyAttributeNames = new ArrayList<String>();
		addDefaultAttributeNames(copyAttributeNames);
	}
	
	protected void addDefaultAttributeNames(List<String> copyAttributeNames){
		copyAttributeNames.add(NoUserHandlerImpl.REFERER);
	}

	@Override
	public void execute(HttpHandlerMethodContent content) throws CubeException {
		HttpSession oldSession = content.getRequest().getSession(false);
		
		Map<String,Object> attributeMap = new HashMap<String,Object>(copyAttributeNames.size());
		if(null != oldSession){
			for(String attributeName : copyAttributeNames){
				attributeMap.put(attributeName, oldSession.getAttribute(attributeName));
			}
		}
		
		if(null != oldSession)
			oldSession.invalidate();
		
		HttpSession newSession = content.getRequest().getSession(true);
		for(String attributeName : copyAttributeNames){
			newSession.setAttribute(attributeName, attributeMap.get(attributeName));
		}
		
	}


}
