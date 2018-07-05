package com.sharingif.cube.web.exception.handler.validation;

import java.util.HashMap;
import java.util.Map;

import com.sharingif.cube.communication.http.HttpRequest;
import com.sharingif.cube.communication.http.HttpResponse;
import com.sharingif.cube.communication.http.request.HttpRequestContext;
import com.sharingif.cube.core.exception.ICubeException;
import com.sharingif.cube.core.exception.handler.ExceptionContent;
import com.sharingif.cube.core.exception.validation.TokenValidationCubeException;
import com.sharingif.cube.core.handler.HandlerMethod;
import com.sharingif.cube.web.exception.handler.WebCubeExceptionHandler;

/**   
 *  
 * @Description:  [处理Token类型异常]   
 * @Author:       [Joly_Huang]   
 * @CreateDate:   [2014年2月21日 下午4:01:16]   
 * @UpdateUser:   [Joly_Huang]   
 * @UpdateDate:   [2014年2月21日 下午4:01:16]   
 * @UpdateRemark: [说明本次修改内容]  
 * @Version:      [v1.0] 
 *    
 */
public class TokenValidationCubeExceptionHandler extends WebCubeExceptionHandler {
	private String defaultErrorView;
	private String queryStateUrl="_queryStateUrl";
	private Map<String,String> exceptionCodeMappings;

	public String getDefaultErrorView() {
		return defaultErrorView;
	}
	public void setDefaultErrorView(String defaultErrorView) {
		this.defaultErrorView = defaultErrorView;
	}
	public String getQueryStateUrl() {
		return queryStateUrl;
	}
	public void setQueryStateUrl(String queryStateUrl) {
		this.queryStateUrl = queryStateUrl;
	}
	public Map<String, String> getExceptionCodeMappings() {
		return exceptionCodeMappings;
	}
	public void setExceptionCodeMappings(Map<String, String> exceptionCodeMappings) {
		this.exceptionCodeMappings = exceptionCodeMappings;
	}

	@Override
	public boolean supports(Exception exception) {
		return exception instanceof TokenValidationCubeException;
	}
	@Override
	public ExceptionContent handlerException(HttpRequestContext<HttpRequest, HttpResponse> requestContext,
											 HandlerMethod handlerMethod,
											 ICubeException cubeException) {

		ExceptionContent out = new ExceptionContent();
		Map<String,Object> model = new HashMap<String,Object>(1);
		
		if(null != exceptionCodeMappings){
			model.put(queryStateUrl, exceptionCodeMappings.get(cubeException.getMessage()));
		}
		
		out.setViewName(defaultErrorView);
		
		out.setModel(model);
		
		
		
		return out;
	}

	

}
