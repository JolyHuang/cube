package com.sharingif.cube.web.exception.handler.validation;

import com.sharingif.cube.core.exception.ICubeException;
import com.sharingif.cube.core.exception.validation.ValidationCubeException;
import com.sharingif.cube.core.handler.HandlerMethod;
import com.sharingif.cube.core.request.RequestInfo;
import com.sharingif.cube.core.util.StringUtils;
import com.sharingif.cube.web.exception.handler.WebCubeExceptionHandler;
import com.sharingif.cube.web.exception.handler.WebExceptionContent;
import com.sharingif.cube.web.exception.handler.WebRequestInfo;

/**   
 *  
 * @Description:  [处理IValidationCube类型异常]   
 * @Author:       [Joly_Huang]   
 * @CreateDate:   [2014年2月20日 下午5:06:13]   
 * @UpdateUser:   [Joly_Huang]   
 * @UpdateDate:   [2014年2月20日 下午5:06:13]   
 * @UpdateRemark: [说明本次修改内容]  
 * @Version:      [v1.0] 
 *    
 */
public class ValidationCubeExceptionHandler extends WebCubeExceptionHandler {
	
	private String refererAttribute = "_referer";
	
	public String getRefererAttribute() {
		return refererAttribute;
	}

	public void setRefererAttribute(String refererAttribute) {
		this.refererAttribute = refererAttribute;
	}

	@Override
	public boolean supports(Exception exception) {
		return exception instanceof ValidationCubeException;
	}

	@Override
	public WebExceptionContent handlerException(RequestInfo<WebRequestInfo> requestInfo,
			HandlerMethod handlerMethod,
			ICubeException cubeException) {
		
		WebExceptionContent out = new WebExceptionContent();
		
		String referer = (String) requestInfo.getRequest().getRequest().getParameter(refererAttribute);

		if (!StringUtils.isEmpty(referer))
			out.setViewName(referer);
		
		return out;
	}

}
