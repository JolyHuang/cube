package com.sharingif.cube.core.handler.chain;

import com.sharingif.cube.core.exception.CubeException;
import com.sharingif.cube.core.util.CubeExceptionUtil;

import java.util.List;

/**
 * 多种组合HandlerMethodChain
 * 2015年8月1日 下午5:45:40
 * @author Joly
 * @version v1.0
 * @since v1.0
 */
public class MultiHandlerMethodChain extends AbstractHandlerMethodChain {
	
	private List<HandlerMethodChain> chains;

	public List<HandlerMethodChain> getChains() {
		return chains;
	}
	public void setChains(List<HandlerMethodChain> chains) {
		this.chains = chains;
	}

	@Override
	public void before(HandlerMethodContent handlerMethodContent) throws CubeException {
		if(null == chains) {
			return;
		}
		
		for(int i=0; i<chains.size(); i++){
			HandlerMethodChain chain = null;
			try {
				chain = chains.get(i);
				chain.before(handlerMethodContent);
			} catch (Exception exception) {
				CubeExceptionUtil.throwCubeException(exception);
			}
		}

	}
	
	@Override
	public void after(HandlerMethodContent handlerMethodContent) throws CubeException {
		if(null == chains) {
			return;
		}
		
		for(int i=(chains.size()-1); i>-1; i--){
			HandlerMethodChain chain = null;
			try {
				chain = chains.get(i);
				chain.after(handlerMethodContent);
			} catch (Exception exception) {
				CubeExceptionUtil.throwCubeException(exception);
			}
		}
	}
	
	@Override
	public void exception(HandlerMethodContent handlerMethodContent, Exception exception) throws CubeException {
		if(null == chains) {
			return;
		}
		
		
		for(int i=(chains.size()-1); i>-1; i--){
			HandlerMethodChain chain = null;
			try {
				chain = chains.get(i);
				chain.exception(handlerMethodContent, exception);
			} catch (Exception handlerMethodChainException) {
				CubeExceptionUtil.throwCubeException(handlerMethodChainException);
			}
		}
	}
	
	

}
