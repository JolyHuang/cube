package com.sharingif.cube.web.springmvc.servlet.view;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.view.RedirectView;

import com.sharingif.cube.web.servlet.view.ViewCacheControl;

/**
 * 扩展RedirectView
 * 1.扩展缓存机制
 * 2015年11月22日 下午1:38:33
 * @author Joly
 * @version v1.0
 * @since v1.0
 */
public class ExtendedRedirectView extends RedirectView {
	
	public ExtendedRedirectView() {
		super();
	}

	public ExtendedRedirectView(String url) {
		super(url);
	}

	public ExtendedRedirectView(String url, boolean contextRelative) {
		super(url, contextRelative);
	}

	public ExtendedRedirectView(String url, boolean contextRelative, boolean http10Compatible) {
		super(url, contextRelative, http10Compatible);
	}

	public ExtendedRedirectView(String url, boolean contextRelative, boolean http10Compatible, boolean exposeModelAttributes) {
		super(url, contextRelative, http10Compatible, exposeModelAttributes);
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
	
}
