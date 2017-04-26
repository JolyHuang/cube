package com.sharingif.cube.web.springmvc.servlet.view.referer;

import java.util.Locale;

import org.springframework.core.Ordered;
import org.springframework.web.context.support.WebApplicationObjectSupport;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;

import com.sharingif.cube.core.util.StringUtils;

/**
 * 
 * @Description: [返回请求视图页解析器]
 * @Author: [Joly]
 * @CreateDate: [2013年12月26日 下午8:37:03]
 * @UpdateUser: [Joly]
 * @UpdateDate: [2013年12月26日 下午8:37:03]
 * @UpdateRemark: [说明本次修改内容]
 * @Version: [v1.0]
 * 
 */
public class RefererViewResolver extends WebApplicationObjectSupport implements ViewResolver, Ordered {
	private int order = Ordered.LOWEST_PRECEDENCE - 1;
	private String refererView = "_referer:";

	public void setOrder(int order) {
		this.order = order;
	}
	@Override
	public int getOrder() {
		return order;
	}
	public String getRefererView() {
		return refererView;
	}
	public void setRefererView(String refererView) {
		this.refererView = refererView;
	}

	@Override
	public View resolveViewName(String viewName, Locale locale) throws Exception {
		if (!viewName.startsWith(refererView))
			return null;
		
		return new RefererView(StringUtils.replace(viewName, refererView, ""));
	}

}
