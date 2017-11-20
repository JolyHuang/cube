package com.sharingif.cube.communication.http.handler.chain;

import com.sharingif.cube.communication.http.HttpRequest;
import com.sharingif.cube.communication.http.HttpResponse;
import com.sharingif.cube.communication.http.request.HttpRequestContext;
import com.sharingif.cube.components.sequence.ISequenceGenerator;
import com.sharingif.cube.core.exception.CubeException;
import com.sharingif.cube.core.handler.chain.AbstractHandlerMethodChain;
import com.sharingif.cube.core.handler.chain.HandlerMethodContent;
import com.sharingif.cube.core.handler.request.RequestLocalContextHolder;
import com.sharingif.cube.core.util.StringUtils;

/**
 * http处理一次请求系统自带属性
 * 2015年8月14日 上午12:15:07
 * @author Joly
 * @version v1.0
 * @since v1.0
 */
public class HttpRequestLocalContextHolderChain extends AbstractHandlerMethodChain {
	
	private ISequenceGenerator<String> sequenceGenerator;
	
	public ISequenceGenerator<String> getSequenceGenerator() {
		return sequenceGenerator;
	}
	public void setSequenceGenerator(ISequenceGenerator<String> sequenceGenerator) {
		this.sequenceGenerator = sequenceGenerator;
	}

	@Override
	public void before(HandlerMethodContent handlerMethodContent) throws CubeException {
		RequestLocalContextHolder.init(getSequence(handlerMethodContent));
	}
	
	@Override
	public void after(HandlerMethodContent handlerMethodContent) throws CubeException {
		RequestLocalContextHolder.clearContext();
	}
	
	@Override
	public void exception(HandlerMethodContent handlerMethodContent, Exception exception) {
		RequestLocalContextHolder.clearContext();
	}
	
	protected String getSequence(HandlerMethodContent content) {
		HttpRequestContext<HttpRequest,HttpResponse> httpRequestContext = content.getRequestContext();
		
		String uniqueNumber = httpRequestContext.getRequest().getHeader("_uniqueNumber");
		if(StringUtils.isEmpty(uniqueNumber)) {
			uniqueNumber = sequenceGenerator.generateSequence();
		}
		return uniqueNumber;
	}

}
