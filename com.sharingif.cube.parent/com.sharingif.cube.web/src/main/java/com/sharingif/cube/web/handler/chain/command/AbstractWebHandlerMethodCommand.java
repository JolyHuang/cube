package com.sharingif.cube.web.handler.chain.command;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sharingif.cube.communication.http.handler.HttpHandlerMethodContent;
import com.sharingif.cube.core.chain.command.Command;


/**
 * web命令
 * 2015年8月3日 上午12:05:22
 * @author Joly
 * @version v1.0
 * @since v1.0
 */
public abstract class AbstractWebHandlerMethodCommand implements Command<HttpHandlerMethodContent> {
	
	protected final Logger logger = LoggerFactory.getLogger(getClass());

}
