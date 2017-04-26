package com.sharingif.cube.components.monitor;

import java.util.Date;


/**
 * 对象操作历史
 * 2015年8月1日 下午3:36:16
 * @author Joly
 * @version v1.0
 * @since v1.0
 */
public class ObjectOperationHistory implements IObjectOperationHistory {
	
	/**
     * 创建人	
     */
	private String createUser;
    /**
     * 修改人
     */	
	private String modifyUser;
    /**
     * 创建时间
     */	
	private Date createTime;
    /**
     * 修改时间
     */	
	private Date modifyTime;

	@Override
	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}
	@Override
	public String getCreateUser() {
		return this.createUser;
	}
	@Override
	public void setModifyUser(String modifyUser) {
		this.modifyUser = modifyUser;
	}
	@Override
	public String getModifyUser() {
		return this.modifyUser;
	}
	@Override
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	@Override
	public Date getCreateTime() {
		return this.createTime;
	}
	@Override
	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}
	@Override
	public Date getModifyTime() {
		return this.modifyTime;
	}

}
