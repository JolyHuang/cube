package com.sharingif.cube.security.web.spring.taglibs.authz;

import org.springframework.security.core.context.SecurityContext;

import com.sharingif.cube.security.web.spring.context.ExtendedHttpSessionSecurityContextRepository;
import com.sharingif.cube.web.servlet.tags.AbstractHtmlElementTag;




/**   
 *  
 * @Description:  [获取spring安全上下文]   
 * @Author:       [Joly_Huang]   
 * @CreateDate:   [2014年3月28日 上午10:34:41]   
 * @UpdateUser:   [Joly_Huang]   
 * @UpdateDate:   [2014年3月28日 上午10:34:41]   
 * @UpdateRemark: [说明本次修改内容]  
 * @Version:      [v1.0] 
 *    
 */
public class SpringSecurityContextTagSupport extends AbstractHtmlElementTag {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5381653247213553235L;
	
	protected SecurityContext getSecurityContext() {
		return (SecurityContext)super.getHttpServletRequest().getSession().getAttribute(ExtendedHttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY);
	}

}
