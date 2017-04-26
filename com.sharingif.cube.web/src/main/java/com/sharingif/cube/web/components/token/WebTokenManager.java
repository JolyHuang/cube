package com.sharingif.cube.web.components.token;

import com.sharingif.cube.communication.http.HttpRequest;
import com.sharingif.cube.components.token.IToken;
import com.sharingif.cube.components.token.RandomNumberTokenManager;
import com.sharingif.cube.core.exception.validation.ValidationCubeException;

/**
 * 防重复提交Token Manager
 * 2015年8月11日 下午10:14:10
 * @author Joly
 * @version v1.0
 * @since v1.0
 */
public class WebTokenManager extends RandomNumberTokenManager {
	
	private String parameterTokenName;
	private String tokenName;
	
	
	public String getParameterTokenName() {
		return parameterTokenName;
	}
	public void setParameterTokenName(String parameterTokenName) {
		this.parameterTokenName = parameterTokenName;
	}
	public String getTokenName() {
		return tokenName;
	}
	public void setTokenName(String tokenName) {
		this.tokenName = tokenName;
	}
	
	public WebTokenManager(){
	}

	public WebTokenManager(String tokenName){
		this.parameterTokenName = tokenName;
		this.tokenName = tokenName;
	}

	public IToken generateToken(HttpRequest request) {
		IToken token = createToken();
		request.getSession().setAttribute(tokenName, token);
		
		return token;
	}

	public void verifyToken(HttpRequest request)throws ValidationCubeException {
		String tokenUniqueId = request.getParameter(getParameterTokenName());
		
		IToken cacheToken = (IToken) request.getSession().getAttribute(tokenName);
		
		verifyToken(tokenUniqueId, cacheToken);
	}
	
}
