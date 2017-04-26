package com.sharingif.cube.web.springmvc.servlet.tags;

import java.io.IOException;

import javax.servlet.jsp.JspException;

import com.sharingif.cube.components.token.IToken;
import com.sharingif.cube.web.components.token.WebTokenManager;
import com.sharingif.cube.web.springmvc.http.SpringMVCHttpRequest;

/**   
 *  
 * @Description:  [生成防重复提交token]   
 * @Author:       [Joly_Huang]   
 * @CreateDate:   [2014年1月30日 下午10:53:01]   
 * @UpdateUser:   [Joly_Huang]   
 * @UpdateDate:   [2014年1月30日 下午10:53:01]   
 * @UpdateRemark: [说明本次修改内容]  
 * @Version:      [v1.0] 
 *    
 */
public class TokenTag extends SpringContextTagSupport {
	
	private static final long serialVersionUID = -7179723217019044298L;
	
	public static final String RESUBMIT_TOKEN_ID = "resubmitTokenManager";
	
	@Override
	public int doStartTag() throws JspException {
		
		WebTokenManager resubmitTokenManager = super.getWebApplicationContext().getBean(RESUBMIT_TOKEN_ID, WebTokenManager.class);
		IToken token = resubmitTokenManager.generateToken(new SpringMVCHttpRequest(getHttpServletRequest()));
		
		String parameterTokenName = resubmitTokenManager.getParameterTokenName();
		
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("<input type=\"hidden\" id=\"").append(parameterTokenName)
		.append("\" name=\"").append(parameterTokenName)
		.append("\" value=\"").append(token.getUniqueId()).append("\" />");
		
		try {
			pageContext.getOut().write(stringBuilder.toString());
		} catch (IOException e) {
			throw new JspException(e.getMessage());
		}
		
		return super.doStartTag();
	}

}
