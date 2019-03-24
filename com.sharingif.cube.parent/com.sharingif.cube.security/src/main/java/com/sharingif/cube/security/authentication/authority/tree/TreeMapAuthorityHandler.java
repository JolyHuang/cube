package com.sharingif.cube.security.authentication.authority.tree;

import com.sharingif.cube.security.authentication.authority.IAuthorityHandler;
import com.sharingif.cube.security.authentication.authority.ICoreAuthority;

import java.util.List;
import java.util.TreeMap;

/**   
 *  
 * @Description:  [权限树数据格式与验证]   
 * @Author:       [Joly]   
 * @CreateDate:   [2014年12月7日 下午8:36:26]   
 * @UpdateUser:   [Joly]   
 * @UpdateDate:   [2014年12月7日 下午8:36:26]   
 * @UpdateRemark: [说明本次修改内容]  
 * @Version:      [v1.0] 
 *    
 */
public class TreeMapAuthorityHandler implements IAuthorityHandler<List<? extends ICoreAuthority>, TreeMap<String, String>> {

	@Override
	public TreeMap<String, String> handleAuthority(List<? extends ICoreAuthority> authorities) {
		TreeMap<String, String> authoritieTreeMap = new TreeMap<>();
		for(ICoreAuthority coreAuthority : authorities){
			authoritieTreeMap.put(coreAuthority.getAuthorityCode(), null);
		}
		return authoritieTreeMap;
	}

	@Override
	public boolean authenAuthority(TreeMap<String, String> authorities,String authorityCode) {
		if(authorities == null)
			return false;
		
		return null == authorities.get(authorityCode) ? false : true;
	}

}
