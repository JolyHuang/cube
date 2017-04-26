package com.sharingif.cube.security.authentication.authority;

/**   
 *  
 * @Description:  [储蓄权限]   
 * @Author:       [Joly]   
 * @CreateDate:   [2014年12月16日 下午11:34:53]   
 * @UpdateUser:   [Joly]   
 * @UpdateDate:   [2014年12月16日 下午11:34:53]   
 * @UpdateRemark: [说明本次修改内容]  
 * @Version:      [v1.0] 
 *    
 */
public interface IAuthorityRepertory<T extends Object> {

	T getAuthorities();
	
}
