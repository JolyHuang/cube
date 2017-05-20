package com.sharingif.cube.web.springmvc.servlet;

import java.util.Locale;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.support.RequestContextUtils;
import org.springframework.web.util.UrlPathHelper;

import com.sharingif.cube.communication.http.handler.HttpHandlerMethodContent;
import com.sharingif.cube.core.handler.HandlerMethodContent;
import com.sharingif.cube.core.handler.chain.HandlerMethodChain;
import com.sharingif.cube.core.request.RequestInfo;
import com.sharingif.cube.web.springmvc.http.SpringMVCHttpRequest;
import com.sharingif.cube.web.springmvc.http.SpringMVCHttpResponse;

/**
 * 扩展DispatcherServlet，重写父类doService方法,添加initContextHolders、resetContextHolders方法。
 * 2015年8月6日 下午11:04:09
 * @author Joly
 * @version v1.0
 * @since v1.0
 */
public class ExtendedDispatcherServlet extends DispatcherServlet {
	
	private static final long serialVersionUID = 6246966252667371968L;
	
	private UrlPathHelper urlPathHelper = new UrlPathHelper();
	
	private static final String WEB_HANDLERMETHODCHAIN = "webHandlerMethodChain";
	
	private HandlerMethodChain<HandlerMethodContent> handlerMethodChain;
	private boolean handlerMethodChainIsNotEmpty;
	
	@SuppressWarnings("unchecked")
	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		
		handlerMethodChain = (HandlerMethodChain<HandlerMethodContent>) super.getWebApplicationContext().getBean(WEB_HANDLERMETHODCHAIN);
		
		handlerMethodChainIsNotEmpty = (null != handlerMethodChain);
	}

	@Override
	protected void doService(HttpServletRequest request, HttpServletResponse response) throws Exception {

		HandlerMethodContent webHandlerMethodContent = null;
		try {
			if(handlerMethodChainIsNotEmpty) {
				webHandlerMethodContent = getHandlerMethodContent(request,response);
				handlerMethodChain.before(webHandlerMethodContent);
			}

			super.doService(request, response);
		} catch (Exception exception){
			if(handlerMethodChainIsNotEmpty) {
				try {
					handlerMethodChain.exception(webHandlerMethodContent, exception);
				} catch (Exception e) {
					logger.error("do service error", e);
					throw e;
				}
			}
			throw exception;
		}
		
		if(handlerMethodChainIsNotEmpty) {
			try {
				handlerMethodChain.after(webHandlerMethodContent);
			} catch (Exception e) {
				logger.error("handler method after chain error", e);
			}
		}
		
	}

	protected HandlerMethodContent getHandlerMethodContent(HttpServletRequest request, HttpServletResponse response) {
		HttpHandlerMethodContent webHandlerMethodContent = new HttpHandlerMethodContent(
				null
				,null
				,null
				,null
				,null
				,null, getRequestInfo(request)
				,new SpringMVCHttpRequest(request)
				,new SpringMVCHttpResponse(response)
		);
		return webHandlerMethodContent;
	}
	
	protected RequestInfo<HttpServletRequest> getRequestInfo(HttpServletRequest request) {

		String lookupPath = urlPathHelper.getLookupPathForRequest(request);
		String method = request.getMethod().toUpperCase(Locale.ENGLISH);

		RequestInfo<HttpServletRequest> requestInfo = new RequestInfo<HttpServletRequest>(null, lookupPath, RequestContextUtils.getLocale(request), method, request);

		return requestInfo;
	}

}
