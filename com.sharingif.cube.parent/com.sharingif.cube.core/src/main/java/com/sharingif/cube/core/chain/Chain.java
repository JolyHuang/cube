package com.sharingif.cube.core.chain;

import java.util.List;

import com.sharingif.cube.core.chain.command.Command;
import com.sharingif.cube.core.exception.CubeException;

/**   
 *  
 * @Description:  [Chain]   
 * @Author:       [Joly]   
 * @CreateDate:   [2013年12月16日 下午1:33:02]   
 * @UpdateUser:   [Joly]   
 * @UpdateDate:   [2013年12月16日 下午1:33:02]   
 * @UpdateRemark: [说明本次修改内容]  
 * @Version:      [v1.0] 
 *    
 */
public interface Chain<T extends Object> extends Invoker<T>{
	
	List<Command<? super T>> getCommands();
	
	void setCommands(List<Command<? super T>> commands);
	
	void invoker(T content) throws CubeException;

}
