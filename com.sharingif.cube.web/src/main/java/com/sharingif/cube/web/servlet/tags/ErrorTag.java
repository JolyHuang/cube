package com.sharingif.cube.web.servlet.tags;

import java.io.IOException;

import javax.servlet.jsp.JspException;

import com.sharingif.cube.core.exception.ICubeException;
import com.sharingif.cube.core.exception.UnknownCubeException;

/**   
 *  
 * @Description:  [处理错误消息]   
 * @Author:       [Joly]   
 * @CreateDate:   [2013年12月30日 上午9:34:44]   
 * @UpdateUser:   [Joly]   
 * @UpdateDate:   [2013年12月30日 上午9:34:44]   
 * @UpdateRemark: [说明本次修改内容]  
 * @Version:      [v1.0] 
 *    
 */
public class ErrorTag extends AbstractHtmlElementTag{
	
	private static final long serialVersionUID = -7309202849526604409L;
	
	public ErrorTag(){
		setErrorAttribute("_exception");
	}
	
	private String errorAttribute;

	public String getErrorAttribute() {
		return errorAttribute;
	}
	public void setErrorAttribute(String errorAttribute) {
		this.errorAttribute = errorAttribute;
	}


	@Override
	public int doStartTag() throws JspException {
		Object exception = super.pageContext.getRequest().getAttribute(getErrorAttribute());
		
		if(null == exception)
			return super.doStartTag();
		
		if (!(exception instanceof ICubeException)){
			exception = new UnknownCubeException();
		}
		
		ICubeException CubeException = (ICubeException)exception;
		
		try {
			super.pageContext.getOut().print(CubeException.getLocalizedMessage());
		} catch (IOException e) {
			throw new JspException(e.getMessage());
		}
		
		return super.doStartTag();
	}

	

}
