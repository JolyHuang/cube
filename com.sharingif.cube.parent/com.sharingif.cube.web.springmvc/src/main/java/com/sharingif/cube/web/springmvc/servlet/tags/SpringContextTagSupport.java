package com.sharingif.cube.web.springmvc.servlet.tags;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.sharingif.cube.web.servlet.tags.AbstractHtmlElementTag;

/**
 *
 * @Description:  [SpringContextTagSupport]
 * @Author:       [Joly]
 * @CreateDate:   [2014年7月1日 下午5:31:51]
 * @UpdateUser:   [Joly]
 * @UpdateDate:   [2014年7月1日 下午5:31:51]
 * @UpdateRemark: [说明本次修改内容]
 * @Version:      [v1.0]
 *
 */
public class SpringContextTagSupport extends AbstractHtmlElementTag {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4301729820876938851L;
	
	
	protected WebApplicationContext getWebApplicationContext() {
		return WebApplicationContextUtils.getWebApplicationContext(super.pageContext.getServletContext());
	}

}
