package com.sharingif.cube.components.token;

import com.sharingif.cube.core.exception.validation.ValidationCubeException;


/**   
 *  
 * @Description:  [IToken Manager]   
 * @Author:       [Joly_Huang]   
 * @CreateDate:   [2014年1月30日 下午12:15:29]   
 * @UpdateUser:   [Joly_Huang]   
 * @UpdateDate:   [2014年1月30日 下午12:15:29]   
 * @UpdateRemark: [说明本次修改内容]  
 * @Version:      [v1.0] 
 *    
 */
public interface ITokenManager {
	
	/**
	 * 创建token
	 * @return
	 */
	IToken createToken();

	/**
	 * 验证token
	 * @param tokenUniqueId
	 * @param cacheCurrentToken
	 * @throws ValidationCubeException
	 */
    void verifyToken(String tokenUniqueId, IToken cacheCurrentToken)throws ValidationCubeException;

}
