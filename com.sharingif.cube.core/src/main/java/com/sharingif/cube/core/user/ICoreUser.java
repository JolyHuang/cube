package com.sharingif.cube.core.user;


/**   
 *  
 * @Description:  [Core User]   
 * @Author:       [Joly]   
 * @CreateDate:   [2014年1月9日 下午2:20:52]   
 * @UpdateUser:   [Joly]   
 * @UpdateDate:   [2014年1月9日 下午2:20:52]   
 * @UpdateRemark: [说明本次修改内容]  
 * @Version:      [v1.0] 
 *    
 */
public interface ICoreUser extends java.io.Serializable{
	
	public static final String CORE_USER = "_CORE_USER";
	
	String getUniqueId();
	void setUniqueId(String uniqueId);
	
	String getUsername();
	void setUsername(String username);
	
}
