package com.sharingif.cube.web.servlet.tags;

import java.io.IOException;

import javax.servlet.jsp.JspException;

/**   
 *  
 * @Description:  [BackRefererTag]   
 * @Author:       [Joly]   
 * @CreateDate:   [2013年12月27日 下午3:25:02]   
 * @UpdateUser:   [Joly]   
 * @UpdateDate:   [2013年12月27日 下午3:25:02]   
 * @UpdateRemark: [说明本次修改内容]  
 * @Version:      [v1.0] 
 *    
 */
public class BackRefererTag extends AbstractHtmlElementTag {

private static final long serialVersionUID = 6280772666360275093L;

private static final String VIEW_REFERER_NAME = "_referer";
	
	public BackRefererTag(){
		super.setId(VIEW_REFERER_NAME);
		super.setName(VIEW_REFERER_NAME);
		super.setType("hidden");
		setPrefix("_referer:");
	}
	
	private String referer;
	private String prefix;
	
	public String getReferer() {
		return referer;
	}
	public void setReferer(String referer) {
		this.referer = referer;
	}
	public String getPrefix() {
		return prefix;
	}
	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}
	
	@Override
	public int doStartTag() throws JspException {
		
		if(referer == null)
			referer = (String) getHttpServletRequest().getAttribute(VIEW_REFERER_NAME);
		
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("<input type=\"").append(super.getType()).append("\" id=\"").append(super.getId()).append("\" name=\"").append(super.getName()).append("\" value=\"").append(prefix).append(referer).append("\" />");
		try {
			pageContext.getOut().write(stringBuilder.toString());
		} catch (IOException e) {
			throw new JspException(e.getMessage());
		}
		return super.doStartTag();
	}
	
	
	
	
}
