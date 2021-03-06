package com.sharingif.cube.web.springmvc.servlet.view.json;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sharingif.cube.core.exception.FieldError;
import com.sharingif.cube.core.exception.IFieldErrorCubeException;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sharingif.cube.core.view.JsonModel;
import com.sharingif.cube.core.config.CubeConfigure;
import com.sharingif.cube.core.exception.ICubeException;
import com.sharingif.cube.core.exception.UnknownCubeException;
import com.sharingif.cube.core.handler.HandlerMethod;
import com.sharingif.cube.core.handler.bind.annotation.SettingConstants;
import com.sharingif.cube.web.springmvc.handler.annotation.ExtendedServletInvocableHandlerMethod;

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
	private String fieldErrorsName = JsonModel.FIELD_ERRORS;
	private String tranStatusName = JsonModel.TRAN_STATUS;
	private String dataName = JsonModel.DATA;

	private MockMappingJackson2JsonView mockMappingJackson2JsonView;

	public ExtendedMappingJackson2JsonView(){
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.setTimeZone(TimeZone.getTimeZone(CubeConfigure.DEFAULT_TIME_ZONE));
		mockMappingJackson2JsonView = new MockMappingJackson2JsonView();
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
	public String getFieldErrorsName() {
		return fieldErrorsName;
	}
	public void setFieldErrorsName(String fieldErrorsName) {
		this.fieldErrorsName = fieldErrorsName;
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
	public void render(Map<String, ?> model, HttpServletRequest request, HttpServletResponse response) throws Exception {

		HandlerMethod handlerMethod = (HandlerMethod)request.getAttribute(ExtendedServletInvocableHandlerMethod.HANDLER_METHOD);
		if((mockMappingJackson2JsonView != null) && (handlerMethod != null)) {
			String useMock = handlerMethod.getSettings().get(SettingConstants.USE_MOCK_VIEW);
			if(SettingConstants.USE_MOCK_VIEW_YES.equals(useMock)) {
				mockMappingJackson2JsonView.render(model, request, response);
				return;
			}
		}

		super.render(model, request, response);
	}

	@Override
	protected void renderMergedOutputModel(Map<String, Object> model,HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String, Object> resultMap = renderMergedOutputModelException(model, request, response);
		
		super.renderMergedOutputModel(resultMap, request, response);
	}
	
	protected Map<String, Object> renderMergedOutputModelException(Map<String, Object> model,HttpServletRequest request, HttpServletResponse response){
		Map<String, Object> resultMap = new HashMap<String, Object>();
		boolean tranStatusFlag = true;

		resultMap.put(getExceptionMessageName(), null);
		resultMap.put(getExceptionLocalizedMessageName(), null);
		resultMap.put(getFieldErrorsName(), null);
		resultMap.put(getDataName(), null);

		for (Map.Entry<String, Object> entry : model.entrySet()) {
			Object value = entry.getValue();

			if(value instanceof Exception){
				tranStatusFlag=false;

				if (!(value instanceof ICubeException)) {
					value = new UnknownCubeException((Exception)value);
				}

				ICubeException exception = (ICubeException)value;

				String message = exception.getMessage();

				resultMap.put(getExceptionMessageName(), message);

				if(value instanceof IFieldErrorCubeException) {
					List<FieldError> localeFieldErrors = ((IFieldErrorCubeException)exception).getLocaleFieldErrors();

					resultMap.put(getFieldErrorsName(), localeFieldErrors);
				}
				resultMap.put(getExceptionLocalizedMessageName(), exception.getLocalizedMessage());
			} else {

				if(!(value instanceof BindingResult)) {
					resultMap.put(getDataName(), value);
				}
			}
		}

		resultMap.put(getTranStatusName(), tranStatusFlag);

		return resultMap;
	}
	
}
