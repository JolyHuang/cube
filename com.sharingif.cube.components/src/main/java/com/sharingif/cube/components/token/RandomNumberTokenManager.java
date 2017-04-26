package com.sharingif.cube.components.token;

import java.util.Map;

import com.sharingif.cube.core.exception.validation.TokenValidationCubeException;
import com.sharingif.cube.core.exception.validation.ValidationCubeException;


/**   
 *  
 * @Description:  [token 管理，验证动态密码，验证码、防重复提交等]   
 * @Author:       [Joly_Huang]   
 * @CreateDate:   [2014年1月30日 下午1:18:03]   
 * @UpdateUser:   [Joly_Huang]   
 * @UpdateDate:   [2014年1月30日 下午1:18:03]   
 * @UpdateRemark: [说明本次修改内容]  
 * @Version:      [v1.0] 
 *    
 */
public class RandomNumberTokenManager implements ITokenManager {
	
	private int tokenLength;						// token有效长度
	private boolean useCapital;						// 只使用数字
	private boolean ignoreCase;						// 忽略大小写		
	private int delayTime;							// 有效时间
	private Map<String,String> replaces;			// 替换指定字符
	
	private String unavailable;			// token已经被验证
	private String tokenVerifyFailure;	// token验证失败
	private String tokeTimeout;			// token超时
	
	
	public String getUnavailable() {
		return unavailable;
	}
	public void setUnavailable(String unavailable) {
		this.unavailable = unavailable;
	}
	public int getTokenLength() {
		return tokenLength;
	}
	public void setTokenLength(int tokenLength) {
		this.tokenLength = tokenLength;
	}
	public boolean isUseCapital() {
		return useCapital;
	}
	public void setUseCapital(boolean useCapital) {
		this.useCapital = useCapital;
	}
	public boolean isIgnoreCase() {
		return ignoreCase;
	}
	public void setIgnoreCase(boolean ignoreCase) {
		this.ignoreCase = ignoreCase;
	}
	public int getDelayTime() {
		return delayTime;
	}
	public void setDelayTime(int delayTime) {
		this.delayTime = delayTime;
	}
	public Map<String, String> getReplaces() {
		return replaces;
	}
	public void setReplaces(Map<String, String> replaces) {
		this.replaces = replaces;
	}
	
	
	public String getTokenVerifyFailure() {
		return tokenVerifyFailure;
	}
	public void setTokenVerifyFailure(String tokenVerifyFailure) {
		this.tokenVerifyFailure = tokenVerifyFailure;
	}
	public String getTokeTimeout() {
		return tokeTimeout;
	}
	public void setTokeTimeout(String tokeTimeout) {
		this.tokeTimeout = tokeTimeout;
	}
	
	
	@Override
	public IToken createToken() {
		RandomNumberToken token = new RandomNumberToken(tokenLength, useCapital);
		
		if(null != replaces){
			String uniqueId = token.getUniqueId();
			
			for(Map.Entry<String, String> entry : replaces.entrySet()){
				uniqueId = uniqueId.replaceAll(entry.getKey(), entry.getValue());
			}
			token = new RandomNumberToken(uniqueId);
		}
		
		return token;
	}

	@Override
	public void verifyToken(String tokenUniqueId, IToken cacheToken)throws ValidationCubeException {
		if(null == cacheToken)
			throw new TokenValidationCubeException(tokenVerifyFailure);
		if(null == tokenUniqueId)
			throw new ValidationCubeException(tokenVerifyFailure);
		
		IToken token = verifyTokenUniqueId(tokenUniqueId, cacheToken);
		
		if(null == token)
			throw new TokenValidationCubeException(tokenVerifyFailure);
			
		
		if(!verifyTokenDelayTime(token))
			throw new TokenValidationCubeException(tokeTimeout);
		
		synchronized(cacheToken){
			if(cacheToken.isUnavailable())
				throw new TokenValidationCubeException(unavailable);
				
			cacheToken.setUnavailable(true);
		}
		
	}
	
	protected boolean verifyTokenDelayTime(IToken currentToken){
		if(delayTime<0)
			return true;
		
		long currentDelayTime = System.currentTimeMillis()-currentToken.getCreateDate();
		
		if(currentDelayTime > (delayTime*1000))
			return false;
		
		return true;
	}
	
	protected IToken verifyTokenUniqueId(String tokenUniqueId, IToken cacheToken){
		
		if(ignoreCase){
			if(tokenUniqueId.toLowerCase().equals(cacheToken.getUniqueId().toLowerCase()))
				return cacheToken;
		}else{
			if(tokenUniqueId.equals(cacheToken.getUniqueId()))
				return cacheToken;
		}
		
		return null;
		
	}
	
}
