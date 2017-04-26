package com.sharingif.cube.core.exception.handler;

import java.util.Map;

import com.sharingif.cube.core.exception.ICubeException;

/**
 * 异常返回数据
 * 2015年8月6日 下午11:25:01
 * @author Joly
 * @version v1.0
 * @since v1.0
 */
public class ExceptionContent {
	
	private String viewName;
	private Map<String,Object> model;
	private ICubeException cubeException;
	
	public String getViewName() {
		return viewName;
	}
	public void setViewName(String viewName) {
		this.viewName = viewName;
	}
	public Map<String, Object> getModel() {
		return model;
	}
	public void setModel(Map<String, Object> model) {
		this.model = model;
	}
	public ICubeException getCubeException() {
		return cubeException;
	}
	public void setCubeException(ICubeException cubeException) {
		this.cubeException = cubeException;
	}
	
}
