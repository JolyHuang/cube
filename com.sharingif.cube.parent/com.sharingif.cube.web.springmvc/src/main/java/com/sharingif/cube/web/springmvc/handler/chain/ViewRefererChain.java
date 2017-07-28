package com.sharingif.cube.web.springmvc.handler.chain;

import org.springframework.web.util.UrlPathHelper;

import com.sharingif.cube.core.exception.CubeException;
import com.sharingif.cube.core.handler.chain.AbstractHandlerMethodChain;
import com.sharingif.cube.core.handler.chain.HandlerMethodContent;
import com.sharingif.cube.web.springmvc.http.SpringMVCHttpRequest;
import com.sharingif.cube.web.springmvc.request.SpringMVCHttpRequestInfo;

/**
 * 交易成功后保存请求地址，当出现验证异常时回跳到此地址
 * 2015年8月16日 下午11:04:53
 * @author Joly
 * @version v1.0
 * @since v1.0
 */
public class ViewRefererChain extends AbstractHandlerMethodChain {
	
	private static final String VIEW_REFERER_NAME = "_referer";

	@Override
	public void before(HandlerMethodContent handlerMethodContent) throws CubeException {
		
	}

	@Override
	public void after(HandlerMethodContent content) throws CubeException {
		SpringMVCHttpRequestInfo httpRequestInfo = content.getRequestInfo();
		SpringMVCHttpRequest springMVCHttpRequest = httpRequestInfo.getRequest();

		springMVCHttpRequest.setAttribute(VIEW_REFERER_NAME, new UrlPathHelper().getLookupPathForRequest(springMVCHttpRequest.getHttpServletRequest()));
	}

}
