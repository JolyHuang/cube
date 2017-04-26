package com.sharingif.cube.core.util;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sharingif.cube.core.exception.CubeRuntimeException;

/**   
 *  
 * @Description:  Class工具箱
 * @Author:       Joly
 * @CreateDate:   2015年2月1日 上午1:00:24
 * @UpdateUser:   Joly
 * @UpdateDate:   2015年2月1日 上午1:00:24
 * @UpdateRemark: 说明本次修改内容
 * @Version:      v1.0
 *    
 */
public class ClassUtils {
	
	/**
	 * 获取类以及父类上的所有field
	 * @param cla
	 */
	public static List<Field> getDeclaredAllFields(Class<?> cla){
		List<Field> fields = new ArrayList<Field>();
		
		getDeclaredAllFields(cla, fields);
		
		return fields;
	}
	/**
	 * 获取类以及父类上的所有field
	 * @param cla
	 * @param fields : 填充field
	 */
	public static void getDeclaredAllFields(Class<?> cla, List<Field> fields){
		for(Field field : cla.getDeclaredFields()){
			fields.add(field);
		}
		
		if(Object.class.getName() != cla.getSuperclass().getName())
			getDeclaredAllFields(cla.getSuperclass(), fields);
		
	}
	
	/**
	 * 将对象中不为空的字段转换为map
	 * @param obj
	 * @return
	 */
	public static final Map<String,Object> getNotEmptyField(Object obj){
		Map<String,Object> fieldsMap = new HashMap<String,Object>();
		
		List<Field> fields = getDeclaredAllFields(obj.getClass());
		for(Field field : fields){
			field.setAccessible(true);
			try {
				
				Object fieldValue = field.get(obj);
				if(null == fieldValue)
					continue;
				
				fieldsMap.put(field.getName(), fieldValue);
			} catch (Exception e) {
				throw new CubeRuntimeException("get field value error", e);
			}
		}
		
		
		return fieldsMap;
	}
	
}
