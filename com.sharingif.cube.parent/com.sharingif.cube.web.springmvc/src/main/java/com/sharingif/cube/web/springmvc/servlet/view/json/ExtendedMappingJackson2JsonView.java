package com.sharingif.cube.web.springmvc.servlet.view.json;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sharingif.cube.communication.JsonModel;
import com.sharingif.cube.core.config.CubeConfigure;
import com.sharingif.cube.core.exception.ICubeException;
import com.sharingif.cube.core.exception.UnknownCubeException;
import com.sharingif.cube.core.exception.validation.BindValidationCubeException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

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
	private String exceptionMessageName = JsonModel.EXCEPTION_MESSAGE;
	private String exceptionLocalizedMessageName = JsonModel.EXCEPTION_LOCALIZED_MESSAGE;
	private String tranStatusName = JsonModel.TRAN_STATUS;
	private String dataName = JsonModel.DATA;

	public ExtendedMappingJackson2JsonView(){
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.setTimeZone(TimeZone.getTimeZone(CubeConfigure.DEFAULT_TIME_ZONE));
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
	public String getDataName() {
		return dataName;
	}
	public void setDataName(String dataName) {
		this.dataName = dataName;
	}
	
	@Override
	protected void renderMergedOutputModel(Map<String, Object> model,HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String, Object> resultMap = renderMergedOutputModelException(model, request, response);
		
		super.renderMergedOutputModel(resultMap, request, response);
	}
	
	protected Map<String, Object> renderMergedOutputModelException(Map<String, Object> model,HttpServletRequest request, HttpServletResponse response){
		boolean tranStatusFlag = true;
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		Map<String, Object> _data = new HashMap<String, Object>();
		for (Map.Entry<String, Object> entry : model.entrySet()) {
			String key = entry.getKey();
			Object value = entry.getValue();
			
			if(value instanceof Exception){
				tranStatusFlag=false;
				
				if (!(value instanceof ICubeException)) {
					value = new UnknownCubeException((Exception)value);
				}

				ICubeException exception = (ICubeException)value;
				
				resultMap.put(getExceptionMessageName(), exception.getMessage());

				if(value instanceof BindValidationCubeException) {
					List<FieldError> localeFieldErrors = ((BindValidationCubeException)exception).getLocaleFieldErrors();

					resultMap.put("_fieldErrors", localeFieldErrors);
				}
				resultMap.put(getExceptionLocalizedMessageName(), exception.getLocalizedMessage());
			} else {
				
				if(!(value instanceof BindingResult)) {
					_data.put(key, value);
				}
			}
		}
		
		resultMap.put(getTranStatusName(), tranStatusFlag);
		resultMap.put(getDataName(), _data);
		
		return resultMap;
	}
	
}
