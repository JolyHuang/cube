package com.sharingif.cube.security.authentication.authority.group;

import java.util.ArrayList;
import java.util.List;

/**   
 *  
 * @Description:  [权限组]   
 * @Author:       [Joly]   
 * @CreateDate:   [2014年12月6日 下午8:47:08]   
 * @UpdateUser:   [Joly]   
 * @UpdateDate:   [2014年12月6日 下午8:47:08]   
 * @UpdateRemark: [说明本次修改内容]  
 * @Version:      [v1.0] 
 *    
 */
public class AuthorityGroup implements IAuthorityGroup {
	
	/**
	 * 权限id
	 */
	private String id;
	/**
	 * 交易组代码
	 */
	private String authorityCode;
	/**
	 * 权限所属组id
	 */
	private String authorityGroupId;
	/**
	 * 排序号
	 */
	private Integer sequenceNumber;
	/**
	 * 父级权限组
	 */
	private IAuthorityGroup parent;
	
	/**
	 * 子级权限组
	 */
	private List<IAuthorityGroup> childs;

	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getAuthorityCode() {
		return authorityCode;
	}
	public void setAuthorityCode(String authorityCode) {
		this.authorityCode = authorityCode;
	}
	public String getAuthorityGroupId() {
		return authorityGroupId;
	}
	public void setAuthorityGroupId(String authorityGroupId) {
		this.authorityGroupId = authorityGroupId;
	}
	public Integer getSequenceNumber() {
		return sequenceNumber;
	}
	public void setSequenceNumber(Integer sequenceNumber) {
		this.sequenceNumber = sequenceNumber;
	}
	public IAuthorityGroup getParent() {
		return parent;
	}
	public void setParent(IAuthorityGroup parent) {
		this.parent = parent;
	}
	public List<IAuthorityGroup> getChilds() {
		return childs;
	}
	public void addChild(IAuthorityGroup authorityGroup) {
		if(null == childs){
			childs = new ArrayList<IAuthorityGroup>();
		}
		childs.add((IAuthorityGroup)authorityGroup);
		
		authorityGroup.setParent(this);
	}
	
}
