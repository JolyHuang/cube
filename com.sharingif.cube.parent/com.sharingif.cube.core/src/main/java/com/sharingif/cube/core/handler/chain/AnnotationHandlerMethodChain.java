package com.sharingif.cube.core.handler.chain;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sharingif.cube.core.chain.Chain;
import com.sharingif.cube.core.chain.ChainImpl;
import com.sharingif.cube.core.chain.command.Command;
import com.sharingif.cube.core.exception.CubeException;
import com.sharingif.cube.core.exception.CubeRuntimeException;
import com.sharingif.cube.core.handler.HandlerMethodContent;
import com.sharingif.cube.core.util.StringUtils;

public class AnnotationHandlerMethodChain<T extends HandlerMethodContent> extends AbstractHandlerMethodChain<T> {
	
	private static final String BEFORE_SIMPLECHAIN_PREFIX = "_B_";
	private static final String AFTER_SIMPLECHAIN_PREFIX = "_A_";
	
	private Map<String, Chain<? super T>> cacheChainMap = new HashMap<String, Chain<? super T>>(100);

	@Override
	public void before(T content) throws CubeException {
		Chain<? super T> beforeChain = getBeforeSimpleChain(content);
		if(null == beforeChain)
			return;
		
		beforeChain.invoker(content);
	}

	@Override
	public void after(T content) throws CubeException {
		Chain<? super T> afterChain = getAfterSimpleChain(content);
		if(null == afterChain)
			return;
		
		afterChain.invoker(content);
	}
	
	@SuppressWarnings("unchecked")
	protected Chain<? super T> getBeforeSimpleChain(HandlerMethodContent handlerMethodContent) throws CubeException {
		
		Method method = handlerMethodContent.getMethod();
		
		String chainMapKey = new StringBuilder(BEFORE_SIMPLECHAIN_PREFIX).append(handlerMethodContent.getObj().getClass().getName()).append(".").append(method.getName()).toString();
		
		Chain<? super T> simpleChain = cacheChainMap.get(chainMapKey);
		
		if(null != simpleChain){
			return simpleChain;
		}
		
		BHMChain bcmChain = method.getAnnotation(BHMChain.class);
		if(null == bcmChain){
			simpleChain = new EmptyChain();
			cacheChainMap.put(chainMapKey, simpleChain);
			return simpleChain;
		}
		
		String chainId=method.getAnnotation(BHMChain.class).ref();
		if(StringUtils.isEmpty(chainId)){
			CubeRuntimeException cubeRuntimeException = new CubeRuntimeException("chainId is null of BeforeSimpleChain",new String[]{method.getName()});
			throw cubeRuntimeException;
		}
		
		try {
			simpleChain = super.getApplicationContext().getBean(chainId, ChainImpl.class);
		} catch (Exception e) {
			CubeRuntimeException cubeRuntimeException = new CubeRuntimeException("BeforeSimpleChain chainId is not find in the ApplicationContext",new String[]{method.getName(),chainId},e);
			throw cubeRuntimeException;
		} 
		
		cacheChainMap.put(chainMapKey, simpleChain);
		
		return simpleChain;
	}
	
	@SuppressWarnings("unchecked")
	protected Chain<? super T> getAfterSimpleChain(HandlerMethodContent handlerMethodContent) throws CubeException {
		
		Method method = handlerMethodContent.getMethod();
		
		String chainMapKey = new StringBuilder().append(AFTER_SIMPLECHAIN_PREFIX).append(handlerMethodContent.getObj().getClass().getName()).append(".").append(method.getName()).toString();
		
		Chain<? super T> simpleChain = cacheChainMap.get(chainMapKey);
		
		if(null != simpleChain){
			return simpleChain;
		}
		
		AHMChain acmChain = method.getAnnotation(AHMChain.class);
		if(null == acmChain){
			simpleChain = new EmptyChain();
			cacheChainMap.put(chainMapKey, simpleChain);
			return simpleChain;
		}
		
		String chainId = acmChain.ref();
		if(StringUtils.isEmpty(chainId)){
			CubeRuntimeException cubeRuntimeException = new CubeRuntimeException("chainId is null of AfterSimpleChain",new String[]{method.getName()});
			throw cubeRuntimeException;
		}
			
		try {
			simpleChain = super.getApplicationContext().getBean(chainId, Chain.class);
		} catch (Exception e) {
			CubeRuntimeException cubeRuntimeException = new CubeRuntimeException("AfterSimpleChain chainId is not find in the ApplicationContext",new String[]{method.getName(),chainId},e);
			throw cubeRuntimeException;
		}
		
		cacheChainMap.put(chainMapKey, simpleChain);
		
		return simpleChain;
	}
	
	private class EmptyChain implements Chain<Object>{

		@Override
		public List<Command<? super Object>> getCommands() {
			return null;
		}

		@Override
		public void setCommands(List<Command<? super Object>> commands) {
			
		}

		@Override
		public void invoker(Object content) throws CubeException {
			
		}
		
	}
	

}
