package com.sharingif.cube.security.authentication.authority;


/**   
 *  
 * @Description:  [处理权限数据格式与验证]   
 * @Author:       [Joly]   
 * @CreateDate:   [2014年12月6日 下午8:24:40]   
 * @UpdateUser:   [Joly]   
 * @UpdateDate:   [2014年12月6日 下午8:24:40]   
 * @UpdateRemark: [说明本次修改内容]  
 * @Version:      [v1.0] 
 *    
 */
public interface IAuthorityHandler<I extends Object, O extends Object> {
	
	/**
	 * 对权限数据格式重新封装，方便权限验证
	 * @param authorities : 权限集合
	 * @return
	 */
	O handleAuthority(I authorities);
	
	/**
	 * 验证权限，验证通过返回true，否则返回false
	 * @param authorities : 权限集合
	 * @param authorityCode : 需要验证的权限代码
	 * @return
	 */
	boolean authenAuthority(O authorities, String authorityCode);

}
