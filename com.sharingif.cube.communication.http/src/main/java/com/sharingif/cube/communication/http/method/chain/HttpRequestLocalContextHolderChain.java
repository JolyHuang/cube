package com.sharingif.cube.communication.http.method.chain;

import com.sharingif.cube.communication.http.method.HttpHandlerMethodContent;
import com.sharingif.cube.components.sequence.ISequenceGenerator;
import com.sharingif.cube.core.exception.CubeException;
import com.sharingif.cube.core.method.chain.AbstractHandlerMethodChain;
import com.sharingif.cube.core.method.handler.request.RequestLocalContextHolder;
import com.sharingif.cube.core.util.StringUtils;

/**
 * http处理一次请求系统自带属性
 * 2015年8月14日 上午12:15:07
 * @author Joly
 * @version v1.0
 * @since v1.0
 */
public class HttpRequestLocalContextHolderChain extends AbstractHandlerMethodChain<HttpHandlerMethodContent> {
	
	private ISequenceGenerator<String> sequenceGenerator;
	
	public ISequenceGenerator<String> getSequenceGenerator() {
		return sequenceGenerator;
	}
	public void setSequenceGenerator(ISequenceGenerator<String> sequenceGenerator) {
		this.sequenceGenerator = sequenceGenerator;
	}

	@Override
	public void before(HttpHandlerMethodContent handlerMethodContent) throws CubeException {
		RequestLocalContextHolder.init(getSequence(handlerMethodContent));
	}
	
	@Override
	public void after(HttpHandlerMethodContent handlerMethodContent) throws CubeException {
		RequestLocalContextHolder.clearContext();
	}
	
	@Override
	public void exception(HttpHandlerMethodContent handlerMethodContent,
			Exception exception) {
		RequestLocalContextHolder.clearContext();
		super.exception(handlerMethodContent, exception);
	}
	
	protected String getSequence(HttpHandlerMethodContent handlerMethodContent) {
		String uniqueNumber = handlerMethodContent.getRequest().getHeader("_uniqueNumber");
		if(StringUtils.isEmpty(uniqueNumber)) {
			uniqueNumber = sequenceGenerator.generateSequence();
		}
		return uniqueNumber;
	}

}
