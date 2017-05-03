package com.sharingif.cube.core.handler.chain;

import java.util.List;

import com.sharingif.cube.core.exception.CubeException;
import com.sharingif.cube.core.handler.HandlerMethodContent;
import com.sharingif.cube.core.util.CubeExceptionUtil;

/**
 * 多种组合HandlerMethodChain
 * 2015年8月1日 下午5:45:40
 * @author Joly
 * @version v1.0
 * @since v1.0
 */
public class MultiHandlerMethodChain<T extends HandlerMethodContent> extends AbstractHandlerMethodChain<T> {
	
	private List<HandlerMethodChain<? super T>> chains;

	public List<HandlerMethodChain<? super T>> getChains() {
		return chains;
	}
	public void setChains(List<HandlerMethodChain<? super T>> chains) {
		this.chains = chains;
	}

	@Override
	public void before(T handlerMethodContent) throws CubeException {
		if(null == chains)
			return;
		
		for(int i=0; i<chains.size(); i++){
			HandlerMethodChain<? super T> chain = null;
			try {
				chain = chains.get(i);
				chain.before(handlerMethodContent);
			} catch (Exception exception) {
				this.logger.error("Before HandlerMethodChain runtime exception,handlerName:{},exception:{}", chain.getClass().getSimpleName(),exception);
				boolean flag = chain.exception(handlerMethodContent, exception);
				if(!flag) {
					CubeExceptionUtil.throwCubeException(exception);
				} 
			}
		}

	}

	@Override
	public void after(T handlerMethodContent) throws CubeException {
		if(null == chains)
			return;
		
		for(int i=(chains.size()-1); i>-1; i--){
			HandlerMethodChain<? super T> chain = null;
			try {
				chain = chains.get(i);
				chain.after(handlerMethodContent);
			} catch (Exception exception) {
				this.logger.error("After HandlerMethodChain runtime exception,handlerName:{},exception:{}", chain.getClass().getSimpleName(),exception);
				boolean flag = chain.exception(handlerMethodContent, exception);
				if(!flag) {
					CubeExceptionUtil.throwCubeException(exception);
				} 
			}
		}
	}

}
