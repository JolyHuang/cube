package com.sharingif.cube.core.exception;


/**
 * 
 * @Description: [异常]
 * @Author: [Joly]
 * @CreateDate: [2013年12月16日 下午1:33:02]
 * @UpdateUser: [Joly]
 * @UpdateDate: [2013年12月16日 下午1:33:02]
 * @UpdateRemark: [说明本次修改内容]
 * @Version: [v1.0]
 * 
 */
public class CubeException extends Exception implements ICubeException {

	private static final long serialVersionUID = -5307974911676419273L;

	public CubeException(String message) {
		super(message);
	}
	public CubeException(Throwable cause) {
		super(cause);
	}
	public CubeException(String message, Object[] args) {
		super(message);
		this.args = args;
	}
	public CubeException(String message, Throwable cause) {
		super(message, cause);
	}
	public CubeException(String message, Object[] args, Throwable cause) {
		super(message, cause);
		this.args = args;
	}

	private String localizedMessage;
	private Object[] args;

	@Override
	public String getLocalizedMessage() {
		if (null == localizedMessage)
			return super.getMessage();

		return localizedMessage;
	}

	public void setLocalizedMessage(String localizedMessage) {
		this.localizedMessage = localizedMessage;
	}

	public Object[] getArgs() {
		return args;
	}

}
