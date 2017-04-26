package com.sharingif.cube.core.method.chain.command;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sharingif.cube.core.chain.command.Command;
import com.sharingif.cube.core.method.HandlerMethodContent;

/**   
 *  
 * @Description:  [description]   
 * @Author:       [Joly]   
 * @CreateDate:   [2015年1月5日 下午11:29:37]   
 * @UpdateUser:   [Joly]   
 * @UpdateDate:   [2015年1月5日 下午11:29:37]   
 * @UpdateRemark: [说明本次修改内容]  
 * @Version:      [v1.0] 
 *    
 */
public abstract class AbstractHandlerMethodCommand implements Command<HandlerMethodContent> {
	
	protected final Logger logger = LoggerFactory.getLogger(getClass());

}
