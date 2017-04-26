package com.sharingif.cube.web.springmvc.servlet.tags;

import java.io.IOException;

import javax.servlet.jsp.JspException;

import com.sharingif.cube.beans.factory.config.ExtendedPropertyPlaceholderConfigurer;


/**
 *
 * @Description:  [获取 ExtendedPropertyPlaceholderConfigurer中的属性]
 * @Author:       [Joly]
 * @CreateDate:   [2014年7月1日 下午5:30:16]
 * @UpdateUser:   [Joly]
 * @UpdateDate:   [2014年7月1日 下午5:30:16]
 * @UpdateRemark: [说明本次修改内容]
 * @Version:      [v1.0]
 *
 */
public class PropertyTag extends SpringContextTagSupport {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6355458716043469337L;
	
	private static final String EXTENDED_PROPERTY_PLACEHOLDER_CONFIGURER = "extendedPropertyPlaceholderConfigurer";
	
	private String propertyKey;
	
	public String getPropertyKey() {
		return propertyKey;
	}
	public void setPropertyKey(String propertyKey) {
		this.propertyKey = propertyKey;
	}


	@Override
	public int doStartTag() throws JspException {
		ExtendedPropertyPlaceholderConfigurer extendedPropertyPlaceholderConfigurer = super.getWebApplicationContext().getBean(EXTENDED_PROPERTY_PLACEHOLDER_CONFIGURER, ExtendedPropertyPlaceholderConfigurer.class);
		try {
			pageContext.getOut().write(extendedPropertyPlaceholderConfigurer.getContextProperty(propertyKey));
		} catch (IOException e) {
			throw new JspException(e.getMessage());
		}
		return super.doStartTag();
	}

}
