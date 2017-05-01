package com.sharingif.cube.core.handler.request;

import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * 储存一次请求系统自带属性
 * 2015年8月6日 下午10:34:58
 * @author Joly
 * @version v1.0
 * @since v1.0
 */
public class RequestLocalContextHolder {
	
	private static final ThreadLocal<Map<String,Object>> CONTEXT_HOLDER = new ThreadLocal<Map<String,Object>>();
	
	private static final String DATE_TIME = RequestLocalContextHolder.class.getName()+"beginCurrentTime";
	private static final String TOKEN = RequestLocalContextHolder.class.getName()+"token";
	
	private static final Random random = new SecureRandom();
	
	private static final int TOKEN_LENGTH = 6;										// token有效长度
	private static final boolean FLAG = true;										// 只使用数字
	

	private static String generateUniqueId(){
		long randomNum = random.nextLong();
		long posRandomNum = randomNum < 0L ? -randomNum : randomNum;
		StringBuilder stringBuilder;
		for (stringBuilder = new StringBuilder(FLAG ? Long.toString(posRandomNum) : Long.toString(posRandomNum, 36)); stringBuilder.length() < TOKEN_LENGTH; stringBuilder.insert(0, '0'));
		if (stringBuilder.length() > TOKEN_LENGTH)
			return stringBuilder.substring(stringBuilder.length() - TOKEN_LENGTH);
		else
			return stringBuilder.toString();
	}
	
	public static void init() {
		String token = generateUniqueId();
		
		init(token);
		
    }
	
	public static void init(String token) {
		Long beginCurrentTime = System.currentTimeMillis();
		
		Map<String, Object> dataMap = new HashMap<String,Object>(2);
		dataMap.put(DATE_TIME, beginCurrentTime);
		dataMap.put(TOKEN, token);
		
		CONTEXT_HOLDER.set(dataMap);
		
    }
	
	/**
	 * @return 请求初始时间
	 */
	public static Long getRequestDateTime() {
		
		if(null == CONTEXT_HOLDER.get()){
			return null;
		}
		
        return (Long) CONTEXT_HOLDER.get().get(DATE_TIME);
    }
	
	/**
	 * @return 请求唯一标示
	 */
	public static String getToken() {
		if(null == CONTEXT_HOLDER.get()){
			return null;
		}
        return (String) CONTEXT_HOLDER.get().get(TOKEN);
    }

	public static void clearContext() {
		CONTEXT_HOLDER.remove();
    }


}
