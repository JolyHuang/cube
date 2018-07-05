package com.sharingif.cube.components.json;


import java.io.InputStream;

/**
 *
 * @Description:  [用一句话描述该文件做什么]
 * @Author:       [Joly]
 * @CreateDate:   [2014年8月12日 上午10:45:17]
 * @UpdateUser:   [Joly]
 * @UpdateDate:   [2014年8月12日 上午10:45:17]
 * @UpdateRemark: [说明本次修改内容]
 * @Version:      [v1.0]
 *
 */
public interface IJsonService {
	
	/**
	 * 对象装Json字符串
	 * @param obj
	 * @return
	 */
	String objectoJson(Object obj) ;

	/**
	 * json字符串转对象
	 * @param jsonString
	 * @param cla
	 * @param <T>
	 * @return
	 */
	<T> T jsonToObject(String jsonString, Class<T> cla);

	/**
	 * json数据流转对象
	 * @param jsonInputStream
	 * @param cla
	 * @param <T>
	 * @return
	 */
	<T> T jsonToObject(InputStream jsonInputStream, Class<T> cla);

	/**
	 * json字符串转对象
	 * @param jsonString
	 * @param collectionClass
	 * @param elementClasses
	 * @param <T>
	 * @return
	 */
	<T> T jsonToObject(String jsonString, Class<T> collectionClass, Class<T>... elementClasses);

	/**
	 * json数据流转对象
	 * @param jsonInputStream
	 * @param collectionClass
	 * @param elementClasses
	 * @param <T>
	 * @return
	 */
	<T> T jsonToObject(InputStream jsonInputStream, Class<T> collectionClass, Class<T>... elementClasses);

}
