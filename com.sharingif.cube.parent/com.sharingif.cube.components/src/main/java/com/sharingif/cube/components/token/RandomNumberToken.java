package com.sharingif.cube.components.token;

import java.security.SecureRandom;
import java.util.Random;

/**
 * 
 * @Description: [随机数Token]
 * @Author: [Joly_Huang]
 * @CreateDate: [2014年1月30日 下午12:26:35]
 * @UpdateUser: [Joly_Huang]
 * @UpdateDate: [2014年1月30日 下午12:26:35]
 * @UpdateRemark: [说明本次修改内容]
 * @Version: [v1.0]
 * 
 */
public class RandomNumberToken extends Token {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3954199560341188047L;
	
	private static final Random random = new SecureRandom();
	
	public RandomNumberToken(String uniqueId){
		super(uniqueId);
	}
	
	public RandomNumberToken(int tokenLength, boolean flag){
		super(generateUniqueId(tokenLength, flag));
	}


	private static String generateUniqueId(int tokenLength, boolean flag){
		long randomNum = random.nextLong();
		long posRandomNum = randomNum < 0L ? -randomNum : randomNum;
		StringBuilder stringBuilder;
		for (stringBuilder = new StringBuilder(flag ? Long.toString(posRandomNum) : Long.toString(posRandomNum, 36)); stringBuilder.length() < tokenLength; stringBuilder.insert(0, '0'));
		if (stringBuilder.length() > tokenLength)
			return stringBuilder.substring(stringBuilder.length() - tokenLength);
		else
			return stringBuilder.toString();
	}

}
