package com.sharingif.cube.web.servlet.tags;

import java.io.IOException;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;

/**   
 *  
 * @Description:  [html base标签]   
 * @Author:       [Joly_Huang]   
 * @CreateDate:   [2014年1月28日 下午8:50:07]   
 * @UpdateUser:   [Joly_Huang]   
 * @UpdateDate:   [2014年1月28日 下午8:50:07]   
 * @UpdateRemark: [说明本次修改内容]  
 * @Version:      [v1.0] 
 *    
 */
public class BasePathTag extends AbstractHtmlElementTag {

	private static final long serialVersionUID = -2105081272755554492L;
	
	private boolean showBase;
	
	public boolean getShowBase() {
		return showBase;
	}

	public void setShowBase(boolean showBase) {
		this.showBase = showBase;
	}

	@Override
	public int doStartTag() throws JspException {
		
		ServletRequest servletRequest = super.pageContext.getRequest();
		
		StringBuilder stringBuilder = new StringBuilder();
		
		if(showBase)
			stringBuilder.append("<base href=\"");
			
		stringBuilder.append(servletRequest.getScheme()).append("://");
		stringBuilder.append(servletRequest.getServerName()).append(":").append(servletRequest.getServerPort());
		stringBuilder.append(((HttpServletRequest)servletRequest).getContextPath()).append("/");
		
		if(showBase)
			stringBuilder.append("\" />");
		try {
			super.pageContext.getOut().write(stringBuilder.toString());
		} catch (IOException e) {
			throw new JspException(e.getMessage());
		}
		
		return super.doStartTag();
	}
	
	


}
