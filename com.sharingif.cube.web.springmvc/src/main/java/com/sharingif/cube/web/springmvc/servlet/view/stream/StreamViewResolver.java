package com.sharingif.cube.web.springmvc.servlet.view.stream;

import java.util.Locale;
import java.util.Map;

import org.springframework.core.Ordered;
import org.springframework.web.context.support.WebApplicationObjectSupport;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;

/**
 *
 * @Description:  [Stream解析器]
 * @Author:       [Joly]
 * @CreateDate:   [2014年5月30日 上午10:53:37]
 * @UpdateUser:   [Joly]
 * @UpdateDate:   [2014年5月30日 上午10:53:37]
 * @UpdateRemark: [说明本次修改内容]
 * @Version:      [v1.0]
 *
 */
public class StreamViewResolver extends WebApplicationObjectSupport implements ViewResolver, Ordered{
	
	private Map<String, ? extends StreamView> streamViews;
	private int order = Ordered.LOWEST_PRECEDENCE - 2;

	public void setOrder(int order) {
		this.order = order;
	}
	@Override
	public int getOrder() {
		return order;
	}
	public Map<String, ? extends StreamView> getStreamViews() {
		return streamViews;
	}
	public void setStreamViews(Map<String, ? extends StreamView> streamViews) {
		this.streamViews = streamViews;
	}
	
	@Override
	public View resolveViewName(String viewName, Locale locale) throws Exception {
		
		return streamViews.get(viewName);
	}

}
