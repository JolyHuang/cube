package com.sharingif.cube.communication.exception;

/**
 * 业务通讯异常
 * 2016年4月12日 下午7:51:26
 * @author Joly
 * @version v1.0
 * @since v1.0
 */
public class BusinessCommunicationException extends CommunicationException {

	private static final long serialVersionUID = -2340347890799123297L;
	
	public BusinessCommunicationException(String message){
		super(message);
	}

}
