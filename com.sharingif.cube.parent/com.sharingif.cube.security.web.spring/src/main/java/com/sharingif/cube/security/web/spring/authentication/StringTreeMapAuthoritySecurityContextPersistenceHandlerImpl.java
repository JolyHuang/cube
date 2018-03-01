package com.sharingif.cube.security.web.spring.authentication;


import com.sharingif.cube.communication.http.HttpRequest;
import com.sharingif.cube.communication.http.HttpResponse;
import com.sharingif.cube.components.password.IPassword;
import com.sharingif.cube.core.user.ICoreUser;
import com.sharingif.cube.security.authentication.authority.IAuthorityRepertory;
import com.sharingif.cube.security.web.authentication.ISecurityContextPersistenceHandler;
import com.sharingif.cube.web.springmvc.http.SpringMVCHttpRequest;
import com.sharingif.cube.web.springmvc.http.SpringMVCHttpResponse;
import com.sharingif.cube.web.user.IWebUserManage;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.web.context.HttpRequestResponseHolder;
import org.springframework.security.web.context.SecurityContextRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.TreeMap;


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
public class StringTreeMapAuthoritySecurityContextPersistenceHandlerImpl implements ISecurityContextPersistenceHandler {
	
	private SecurityContextRepository securityContextRepository;
	private IWebUserManage webUserManage;

	public SecurityContextRepository getSecurityContextRepository() {
		return securityContextRepository;
	}
	public void setSecurityContextRepository(SecurityContextRepository securityContextRepository) {
		this.securityContextRepository = securityContextRepository;
	}
	public IWebUserManage getWebUserManage() {
		return webUserManage;
	}
	public void setWebUserManage(IWebUserManage webUserManage) {
		this.webUserManage = webUserManage;
	}

	@Override
	public void handleSecurityContextPersistence(HttpRequest request, HttpResponse response) {

		SpringMVCHttpRequest httpRequest = (SpringMVCHttpRequest)request;
		SpringMVCHttpResponse httpResponse = (SpringMVCHttpResponse)response;
		
		ICoreUser coreUser = webUserManage.getUser(request);
		
		HttpRequestResponseHolder holder = new HttpRequestResponseHolder(httpRequest.getHttpServletRequest(), httpResponse.getHttpServletResponse());
		SecurityContext securityContext = securityContextRepository.loadContext(holder);
        securityContext.setAuthentication(new UsernamePasswordAuthenticationToken(coreUser, ((IPassword)coreUser).getPassword(), getAuthorities(coreUser)));
        
        securityContextRepository.saveContext(securityContext, holder.getRequest(), holder.getResponse());

	}
	
	@SuppressWarnings("unchecked")
	private Collection<? extends GrantedAuthority> getAuthorities(ICoreUser coreUser){
		
		Collection<SimpleGrantedAuthority> grantedAuthorities = new ArrayList<SimpleGrantedAuthority>();
		
		IAuthorityRepertory<TreeMap<String, String>> authorityRepertory = (IAuthorityRepertory<TreeMap<String, String>>)coreUser;
		TreeMap<String, String> authorities = authorityRepertory.getAuthorities();
		
		if(null == authorities)
			return grantedAuthorities;
		
		for (Map.Entry<String, String> entry : authorityRepertory.getAuthorities().entrySet()) {
			grantedAuthorities.add(new SimpleGrantedAuthority(entry.getKey()));
		}
		return grantedAuthorities;
	}

}
