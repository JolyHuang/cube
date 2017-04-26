package com.sharingif.cube.web.springmvc.servlet.view.referer;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.view.InternalResourceView;

import com.sharingif.cube.web.servlet.view.ViewCacheControl;


/**   
 *  
 * @Description:  [返回请求视图页]   
 * @Author:       [Joly]   
 * @CreateDate:   [2013年12月26日 下午9:17:54]   
 * @UpdateUser:   [Joly]   
 * @UpdateDate:   [2013年12月26日 下午9:17:54]   
 * @UpdateRemark: [说明本次修改内容]  
 * @Version:      [v1.0] 
 *    
 */
public class RefererView extends InternalResourceView{
	
	public RefererView(String url){
		super(url);
	}

	
	@Override
	protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpServletRequest wrapper = new HttpMethodRequestWrapper(request, RequestMethod.GET.toString());
		
		super.renderMergedOutputModel(model, wrapper, response);
	}
	
	@Override
	protected void prepareResponse(HttpServletRequest request, HttpServletResponse response) {
		
		ViewCacheControl viewCacheControl = (ViewCacheControl) request.getAttribute(ViewCacheControl.VIEW_CACHE_CONTROL_TYPE);
		
		if(null == viewCacheControl){
			super.prepareResponse(request, response);
			return;
		}
		
		if(ViewCacheControl.NO_STORE.getValue().equals(viewCacheControl.getValue())){
			response.setHeader("Cache-Control", "no-store");
			response.setHeader("Pragma", "no-cache");
			response.setDateHeader("Expires", 0);
			return;
		}
		
		super.prepareResponse(request, response);
	}
	
	/**
	 * 当使用restful时回跳页面只能是get方式请求
	 * Simple {@link HttpServletRequest} wrapper that returns the supplied method for
	 * {@link HttpServletRequest#getMethod()}.
	 */
	private static class HttpMethodRequestWrapper extends HttpServletRequestWrapper {

		private final String method;

		public HttpMethodRequestWrapper(HttpServletRequest request, String method) {
			super(request);
			this.method = method;
		}

		@Override
		public String getMethod() {
			return this.method;
		}
	}

	
}
