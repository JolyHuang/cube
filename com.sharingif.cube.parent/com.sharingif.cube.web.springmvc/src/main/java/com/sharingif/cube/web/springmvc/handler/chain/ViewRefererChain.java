package com.sharingif.cube.web.springmvc.handler.chain;

import org.springframework.web.util.UrlPathHelper;

import com.sharingif.cube.core.exception.CubeException;
import com.sharingif.cube.core.handler.chain.AbstractHandlerMethodChain;
import com.sharingif.cube.web.springmvc.handler.SpringMVCHandlerMethodContent;
import com.sharingif.cube.web.springmvc.http.SpringMVCHttpRequest;

/**
 * 交易成功后保存请求地址，当出现验证异常时回跳到此地址
 * 2015年8月16日 下午11:04:53
 * @author Joly
 * @version v1.0
 * @since v1.0
 */
public class ViewRefererChain extends AbstractHandlerMethodChain<SpringMVCHandlerMethodContent> {
	
	private static final String VIEW_REFERER_NAME = "_referer";

	@Override
	public void before(SpringMVCHandlerMethodContent handlerMethodContent) throws CubeException {
		
	}

	@Override
	public void after(SpringMVCHandlerMethodContent handlerMethodContent) throws CubeException {
		handlerMethodContent.getRequest().setAttribute(VIEW_REFERER_NAME, new UrlPathHelper().getLookupPathForRequest(((SpringMVCHttpRequest)handlerMethodContent.getRequest()).getRequest()));
	}

}
