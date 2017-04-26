package com.sharingif.cube.communication.method.handler;

/**
 * 请求调度处理器
 * 2016年4月24日 下午8:43:46
 * @author Joly
 * @version v1.0
 * @since v1.0
 */
public interface DispatcherHandler<I> {
	
	void doDispatch(I request);

}
