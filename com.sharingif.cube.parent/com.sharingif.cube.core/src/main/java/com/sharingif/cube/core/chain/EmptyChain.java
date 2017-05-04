package com.sharingif.cube.core.chain;

import java.util.List;

import com.sharingif.cube.core.chain.command.Command;
import com.sharingif.cube.core.exception.CubeException;

/**
 * 空chain，不做任何处理
 * 2017年5月4日 上午9:24:25
 * @author Joly
 * @version v1.0
 * @since v1.0
 */
public class EmptyChain implements Chain<Object>{

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
