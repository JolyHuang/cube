package com.sharingif.cube.core.chain;

import java.util.List;

import com.sharingif.cube.core.chain.command.Command;
import com.sharingif.cube.core.exception.CubeException;

/**   
 *  
 * @Description:  [ChainImpl]   
 * @Author:       [Joly]   
 * @CreateDate:   [2013年12月16日 下午1:33:02]   
 * @UpdateUser:   [Joly]   
 * @UpdateDate:   [2013年12月16日 下午1:33:02]   
 * @UpdateRemark: [说明本次修改内容]  
 * @Version:      [v1.0] 
 *    
 */
public class ChainImpl<T extends Object> implements Chain<T> {
	
	private List<Command<? super T>> commands;

	@Override
	public List<Command<? super T>> getCommands() {
		return commands;
	}
	@Override
	public void setCommands(List<Command<? super T>> commands) {
		this.commands=commands;
	}

	@Override
	public void invoker(T content) throws CubeException {
		for(Command<? super T> command : commands){
			command.execute(content);
		}
	}
	
	
}
