package com.sharingif.cube.security.authentication.authority.group;

import java.util.List;

import com.sharingif.cube.security.authentication.authority.ICoreAuthority;

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
public interface IAuthorityGroup extends ICoreAuthority {
	
	String getId();
	
	String getAuthorityGroupId();
	
	Integer getSequenceNumber();
	
	List<IAuthorityGroup> getChilds();
	
	void setParent(IAuthorityGroup parent);
	
	void addChild(IAuthorityGroup authorityGroup);
	
}
