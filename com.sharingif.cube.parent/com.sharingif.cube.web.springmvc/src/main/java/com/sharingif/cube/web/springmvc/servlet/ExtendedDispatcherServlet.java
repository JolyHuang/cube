package com.sharingif.cube.web.springmvc.servlet;

import java.util.Locale;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.support.RequestContextUtils;
import org.springframework.web.util.UrlPathHelper;

import com.sharingif.cube.core.handler.chain.HandlerMethodChain;
import com.sharingif.cube.core.handler.chain.HandlerMethodContent;
import com.sharingif.cube.web.springmvc.http.SpringMVCHttpRequest;
import com.sharingif.cube.web.springmvc.http.SpringMVCHttpResponse;
import com.sharingif.cube.web.springmvc.request.SpringMVCHttpRequestInfo;

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
	
	private HandlerMethodChain handlerMethodChain;
	private boolean handlerMethodChainIsNotEmpty;
	
	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		
		handlerMethodChain = (HandlerMethodChain) super.getWebApplicationContext().getBean(WEB_HANDLERMETHODCHAIN);
		
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
		
		HandlerMethodContent webHandlerMethodContent = new HandlerMethodContent(
				null
				,null
				,null
				,null
				,getRequestInfo(request, response)
		);
		return webHandlerMethodContent;
	}
	
	protected SpringMVCHttpRequestInfo getRequestInfo(HttpServletRequest request, HttpServletResponse response) {

		String lookupPath = urlPathHelper.getLookupPathForRequest(request);
		String method = request.getMethod().toUpperCase(Locale.ENGLISH);

		
		 SpringMVCHttpRequestInfo requestInfo = new SpringMVCHttpRequestInfo(
				 null
				 ,lookupPath
				 ,RequestContextUtils.getLocale(request)
				 ,method
				 ,new SpringMVCHttpRequest(request)
				 ,new SpringMVCHttpResponse(response));

		return requestInfo;
	}

}
