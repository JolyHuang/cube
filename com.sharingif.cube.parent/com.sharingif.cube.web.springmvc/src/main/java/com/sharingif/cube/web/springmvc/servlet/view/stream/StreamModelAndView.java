package com.sharingif.cube.web.springmvc.servlet.view.stream;

import org.springframework.web.servlet.ModelAndView;


/**
 * [Stream ModelAndView]
 * [2015年5月20日 上午12:21:47]
 * [@author Joly]
 * [@version v1.0]
 * [@since v1.0]
 */
public class StreamModelAndView extends ModelAndView {
	
	public static final String CONTENT = "_STREAMVIEW_CONTENT";
	
	public void setContent(String content){
		addObject(CONTENT, content);
	}
	public void setContent(byte[] content){
		addObject(CONTENT, content);
	}
	public Object getContent(){
		return getModel().get(CONTENT);
	}
	
}
