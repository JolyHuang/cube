package com.sharingif.cube.security.authentication.authority.group;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.sharingif.cube.core.util.ObjectUtils;
import com.sharingif.cube.core.util.StringUtils;
import com.sharingif.cube.security.authentication.authority.IAuthorityHandler;

/**   
 *  
 * @Description:  [权限组数据格式与验证]   
 * @Author:       [Joly]   
 * @CreateDate:   [2014年12月6日 下午9:03:02]   
 * @UpdateUser:   [Joly]   
 * @UpdateDate:   [2014年12月6日 下午9:03:02]   
 * @UpdateRemark: [说明本次修改内容]  
 * @Version:      [v1.0] 
 *    
 */
public class AuthorityGroupHandler implements IAuthorityHandler<Set<? extends IAuthorityGroup>, IAuthorityGroup> {
	
	private static final String ROOT_KEY="root";

	@Override
	public IAuthorityGroup handleAuthority(Set<? extends IAuthorityGroup> authorities) {
		if(ObjectUtils.isEmpty(authorities))
			return null;
			
		return sortAuthorities(authorities);
	}

	@Override
	public boolean authenAuthority(IAuthorityGroup authorities, String authorityCode) {
		
		if(null == authorities || null==authorities.getChilds()  || StringUtils.isEmpty(authorityCode))
			return false;
		
		for(IAuthorityGroup authority : authorities.getChilds()){
			if(-1!=authorityCode.indexOf(authority.getAuthorityCode(), 0)){
				if(authorityCode.equals(authority.getAuthorityCode())){
					return true;
				}
				return authenAuthority(authority, authorityCode);
			}
		}
		
		return false;
	}
	
	/**
	 * 对authorities排序
	 * @param authorities
	 * @return
	 */
	protected IAuthorityGroup sortAuthorities(Set<? extends IAuthorityGroup> authorities){
		Map<String,List<IAuthorityGroup>> authoritiesMap = new HashMap<String,List<IAuthorityGroup>>();
		
		// 权限根据权限组编号进行分组放入map中
		for(IAuthorityGroup authorityGroup : authorities){
			String authorityGroupId = authorityGroup.getAuthorityGroupId();
			authorityGroupId = (null == authorityGroupId ? ROOT_KEY : authorityGroupId);
			List<IAuthorityGroup> authorityGroupList = authoritiesMap.get(authorityGroupId);
			if(null == authorityGroupList){
				authorityGroupList = new ArrayList<IAuthorityGroup>();
				authorityGroupList.add(authorityGroup);
				authoritiesMap.put(authorityGroupId, authorityGroupList);
			}else{
				authorityGroupList.add(authorityGroup);
			}
		}
		
		IAuthorityGroup rootAuthorityGroup = new AuthorityGroup();
		List<IAuthorityGroup> rootAuthorityGroupList = authoritiesMap.get(ROOT_KEY);
		sortAuthorities(authoritiesMap, rootAuthorityGroup, rootAuthorityGroupList);
		
		
		return rootAuthorityGroup;
	}
	
	/**
	 * 将权限组map转换为父子级关系对象
	 * @param authoritiesMap
	 * @param parentAuthorityGroup
	 * @param childsGroupList
	 */
	private void sortAuthorities(Map<String,List<IAuthorityGroup>> authoritiesMap, IAuthorityGroup parentAuthorityGroup, List<IAuthorityGroup> childsGroupList){
		// 权限列表根据排序字段进行排序
		Collections.sort(childsGroupList, new Comparator<IAuthorityGroup>(){
			public int compare(IAuthorityGroup a, IAuthorityGroup b) {
				return a.getSequenceNumber()-b.getSequenceNumber();
			}
		});
		
		// 从父级目录递归调用转换为IAuthorityGroup对象
		for(IAuthorityGroup authorityGroup : childsGroupList){
			List<IAuthorityGroup> childs = authoritiesMap.get(authorityGroup.getId());
			if(null != childs){
				sortAuthorities(authoritiesMap, authorityGroup, childs);
			}
			parentAuthorityGroup.addChild(authorityGroup);
		}
	}
	
}
