package com.sharingif.cube.components.handler.chain;

import com.sharingif.cube.components.sequence.ISequenceGenerator;
import com.sharingif.cube.core.exception.CubeException;
import com.sharingif.cube.core.handler.HandlerMethodContent;
import com.sharingif.cube.core.handler.chain.AbstractHandlerMethodChain;
import com.sharingif.cube.core.handler.request.RequestLocalContextHolder;

/**
 * 处理一次请求系统自带属性
 * 2015年8月14日 上午12:15:07
 * @author Joly
 * @version v1.0
 * @since v1.0
 */
public class RequestLocalContextHolderChain extends AbstractHandlerMethodChain<HandlerMethodContent> {

	private ISequenceGenerator<String> sequenceGenerator;

	public ISequenceGenerator<String> getSequenceGenerator() {
		return sequenceGenerator;
	}

	public void setSequenceGenerator(ISequenceGenerator<String> sequenceGenerator) {
		this.sequenceGenerator = sequenceGenerator;
	}

	@Override
	public void before(HandlerMethodContent handlerMethodContent)
			throws CubeException {

		if(getSequenceGenerator() == null) {
			RequestLocalContextHolder.init();
			return;
		}

		RequestLocalContextHolder.init(getSequenceGenerator().generateSequence());
	}

	@Override
	public void after(HandlerMethodContent handlerMethodContent)
			throws CubeException {
		RequestLocalContextHolder.clearContext();
	}

	@Override
	public void exception(HandlerMethodContent handlerMethodContent, Exception exception) throws CubeException {
		RequestLocalContextHolder.clearContext();
	}
	
	

}
