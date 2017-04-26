package com.sharingif.cube.core.method.chain;

import java.util.List;

import com.sharingif.cube.core.exception.CubeException;
import com.sharingif.cube.core.method.HandlerMethodContent;
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
			try {
				HandlerMethodChain<? super T> chain = chains.get(i);
				chain.before(handlerMethodContent);
			} catch (Exception exception) {
				
				if(exception.getCause() == null){
					this.logger.error("excute before HandlerMethodChain error \nmessage:{} \nlocalizedMessage:{}", exception.getMessage() ,exception.getLocalizedMessage());
				}else{
					this.logger.error("excute before HandlerMethodChain error", exception);
				}
				
				for(;i>-1;i--){
					try {
						HandlerMethodChain<? super T> chain = chains.get(i);
						chain.exception(handlerMethodContent, exception);
					} catch (Exception e) {
						this.logger.error("excute before HandlerMethodChain exception error", e);
					}
				}
				
				CubeExceptionUtil.throwCubeException(exception);
					
			}
		}

	}

	@Override
	public void after(T handlerMethodContent) throws CubeException {
		if(null == chains)
			return;
		
		for(int i=(chains.size()-1); i>-1; i--){
			try {
				HandlerMethodChain<? super T> chain = chains.get(i);
				chain.after(handlerMethodContent);
			} catch (Exception exception) {
				this.logger.error("excute after HandlerMethodChain error", exception);
				
				for(;i>-1;i--){
					try {
						HandlerMethodChain<? super T> chain = chains.get(i);
						chain.exception(handlerMethodContent, exception);
					} catch (Exception e) {
						this.logger.error("excute after HandlerMethodChain exception error", e);
					}
				}
				
				CubeExceptionUtil.throwCubeException(exception);
			}
		}
	}

}
