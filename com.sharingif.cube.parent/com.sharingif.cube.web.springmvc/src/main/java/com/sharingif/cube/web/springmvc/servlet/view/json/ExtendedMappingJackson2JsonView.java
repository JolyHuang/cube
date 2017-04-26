package com.sharingif.cube.web.springmvc.servlet.view.json;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sharingif.cube.core.exception.ICubeException;
import com.sharingif.cube.core.exception.UnknownCubeException;

/**   
 *  
 * @Description:  [解析json view，添加异常处理功能 ]   
 * @Author:       [Joly]   
 * @CreateDate:   [2013年12月24日 下午11:07:11]   
 * @UpdateUser:   [Joly]   
 * @UpdateDate:   [2013年12月24日 下午11:07:11]   
 * @UpdateRemark: [说明本次修改内容]  
 * @Version:      [v1.0] 
 *    
 */
public class ExtendedMappingJackson2JsonView extends MappingJackson2JsonView{
	
	private String exceptionMessageName = "_exceptionMessage";
	private String exceptionLocalizedMessageName = "_exceptionLocalizedMessage";
	private String tranStatusName="_tranStatus";

	public ExtendedMappingJackson2JsonView(){
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.setTimeZone(TimeZone.getTimeZone("GMT+0800"));			// 中国上海
		super.setObjectMapper(objectMapper);
	}
	
	public ExtendedMappingJackson2JsonView(String timeZone){
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.setTimeZone(TimeZone.getTimeZone(timeZone));
		super.setObjectMapper(objectMapper);
	}
	
	public String getExceptionMessageName() {
		return exceptionMessageName;
	}
	public void setExceptionMessageName(String exceptionMessageName) {
		this.exceptionMessageName = exceptionMessageName;
	}
	public String getExceptionLocalizedMessageName() {
		return exceptionLocalizedMessageName;
	}
	public void setExceptionLocalizedMessageName(
			String exceptionLocalizedMessageName) {
		this.exceptionLocalizedMessageName = exceptionLocalizedMessageName;
	}
	public String getTranStatusName() {
		return tranStatusName;
	}
	public void setTranStatusName(String tranStatusName) {
		this.tranStatusName = tranStatusName;
	}
	
	@Override
	protected void renderMergedOutputModel(Map<String, Object> model,HttpServletRequest request, HttpServletResponse response) throws Exception {
		resolverException(model, request, response);
		
		super.renderMergedOutputModel(model, request, response);
	}
	
	protected void resolverException(Map<String, Object> model,HttpServletRequest request, HttpServletResponse response){
		List<String> removeKeys = new ArrayList<String>();
		Map<String, Object> addModels = new HashMap<String, Object>();
		boolean tranStatusFlag = true;
		
		for (Map.Entry<String, Object> entry : model.entrySet()) {
			String key = entry.getKey();
			Object value = entry.getValue();
			
			if(value instanceof Exception){
				tranStatusFlag=false;
				removeKeys.add(key);
				
				if (!(value instanceof ICubeException))
					value = new UnknownCubeException((Exception)value);
				
				ICubeException exception = (ICubeException)value;
				
				addModels.put(exceptionMessageName, exception.getMessage());
				addModels.put(exceptionLocalizedMessageName, exception.getLocalizedMessage());
			}
			
		}
		
		for(String key : removeKeys){
			model.remove(key);
		}
		
		model.putAll(addModels);
		model.put(tranStatusName,tranStatusFlag);
	}
	
}
