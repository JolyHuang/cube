package com.sharingif.cube.security.web.authentication;

import com.sharingif.cube.communication.http.HttpRequest;
import com.sharingif.cube.communication.http.HttpResponse;

/**   
 *  
 * @Description:  [保存SecurityContext到session]   
 * @Author:       [Joly_Huang]   
 * @CreateDate:   [2014年3月26日 下午6:58:30]   
 * @UpdateUser:   [Joly_Huang]   
 * @UpdateDate:   [2014年3月26日 下午6:58:30]   
 * @UpdateRemark: [说明本次修改内容]  
 * @Version:      [v1.0] 
 *    
 */
public interface ISecurityContextPersistenceHandler {
	
	void handleSecurityContextPersistence(HttpRequest request, HttpResponse response);

}
