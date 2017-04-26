package com.sharingif.cube.core.exception;


/**
 *
 * @Description:  [运行时异常]
 * @Author:       [Joly]
 * @CreateDate:   [2014年8月12日 上午11:14:13]
 * @UpdateUser:   [Joly]
 * @UpdateDate:   [2014年8月12日 上午11:14:13]
 * @UpdateRemark: [说明本次修改内容]
 * @Version:      [v1.0]
 *
 */
public class CubeRuntimeException extends RuntimeException implements ICubeRuntimeException {

	private static final long serialVersionUID = 755654791711065568L;

	public CubeRuntimeException(String message) {
		super(message);
	}
	public CubeRuntimeException(Throwable cause) {
		super(cause);
	}
	public CubeRuntimeException(String message, Object[] args) {
		super(message);
		this.args = args;
	}
	public CubeRuntimeException(String message, Throwable cause) {
		super(message, cause);
	}
	public CubeRuntimeException(String message, Object[] args, Throwable cause) {
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
