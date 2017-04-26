package com.sharingif.cube.web.servlet.tags;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.tagext.TagSupport;



/**   
 *  
 * @Description:  [Tag]   
 * @Author:       [Joly]   
 * @CreateDate:   [2013年12月27日 下午3:08:45]   
 * @UpdateUser:   [Joly]   
 * @UpdateDate:   [2013年12月27日 下午3:08:45]   
 * @UpdateRemark: [说明本次修改内容]  
 * @Version:      [v1.0] 
 *    
 */
public abstract class AbstractHtmlElementTag extends TagSupport{
	
	private static final long serialVersionUID = -6557652978377341158L;

	public static final String CLASS_ATTRIBUTE = "class";

	public static final String STYLE_ATTRIBUTE = "style";

	public static final String LANG_ATTRIBUTE = "lang";

	public static final String TITLE_ATTRIBUTE = "title";

	public static final String DIR_ATTRIBUTE = "dir";

	public static final String TABINDEX_ATTRIBUTE = "tabindex";

	public static final String ONCLICK_ATTRIBUTE = "onclick";

	public static final String ONDBLCLICK_ATTRIBUTE = "ondblclick";

	public static final String ONMOUSEDOWN_ATTRIBUTE = "onmousedown";

	public static final String ONMOUSEUP_ATTRIBUTE = "onmouseup";

	public static final String ONMOUSEOVER_ATTRIBUTE = "onmouseover";

	public static final String ONMOUSEMOVE_ATTRIBUTE = "onmousemove";

	public static final String ONMOUSEOUT_ATTRIBUTE = "onmouseout";

	public static final String ONKEYPRESS_ATTRIBUTE = "onkeypress";

	public static final String ONKEYUP_ATTRIBUTE = "onkeyup";

	public static final String ONKEYDOWN_ATTRIBUTE = "onkeydown";

	private String name;
	
	private String type;

	private String cssClass;

	private String cssErrorClass;

	private String cssStyle;

	private String lang;

	private String title;

	private String dir;

	private String tabindex;

	private String onclick;

	private String ondblclick;

	private String onmousedown;

	private String onmouseup;

	private String onmouseover;

	private String onmousemove;

	private String onmouseout;

	private String onkeypress;

	private String onkeyup;

	private String onkeydown;

	private Map<String, Object> dynamicAttributes;

	

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	/**
	 * Set the value of the '{@code class}' attribute.
	 * May be a runtime expression.
	 */
	public void setCssClass(String cssClass) {
		this.cssClass = cssClass;
	}

	/**
	 * Get the value of the '{@code class}' attribute.
	 * May be a runtime expression.
	 */
	protected String getCssClass() {
		return this.cssClass;
	}

	/**
	 * The CSS class to use when the field bound to a particular tag has errors.
	 * May be a runtime expression.
	 */
	public void setCssErrorClass(String cssErrorClass) {
		this.cssErrorClass = cssErrorClass;
	}

	/**
	 * The CSS class to use when the field bound to a particular tag has errors.
	 * May be a runtime expression.
	 */
	protected String getCssErrorClass() {
		return this.cssErrorClass;
	}

	/**
	 * Set the value of the '{@code style}' attribute.
	 * May be a runtime expression.
	 */
	public void setCssStyle(String cssStyle) {
		this.cssStyle = cssStyle;
	}

	/**
	 * Get the value of the '{@code style}' attribute.
	 * May be a runtime expression.
	 */
	protected String getCssStyle() {
		return this.cssStyle;
	}

	/**
	 * Set the value of the '{@code lang}' attribute.
	 * May be a runtime expression.
	 */
	public void setLang(String lang) {
		this.lang = lang;
	}

	/**
	 * Get the value of the '{@code lang}' attribute.
	 * May be a runtime expression.
	 */
	protected String getLang() {
		return this.lang;
	}

	/**
	 * Set the value of the '{@code title}' attribute.
	 * May be a runtime expression.
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * Get the value of the '{@code title}' attribute.
	 * May be a runtime expression.
	 */
	protected String getTitle() {
		return this.title;
	}

	/**
	 * Set the value of the '{@code dir}' attribute.
	 * May be a runtime expression.
	 */
	public void setDir(String dir) {
		this.dir = dir;
	}

	/**
	 * Get the value of the '{@code dir}' attribute.
	 * May be a runtime expression.
	 */
	protected String getDir() {
		return this.dir;
	}

	/**
	 * Set the value of the '{@code tabindex}' attribute.
	 * May be a runtime expression.
	 */
	public void setTabindex(String tabindex) {
		this.tabindex = tabindex;
	}

	/**
	 * Get the value of the '{@code tabindex}' attribute.
	 * May be a runtime expression.
	 */
	protected String getTabindex() {
		return this.tabindex;
	}

	/**
	 * Set the value of the '{@code onclick}' attribute.
	 * May be a runtime expression.
	 */
	public void setOnclick(String onclick) {
		this.onclick = onclick;
	}

	/**
	 * Get the value of the '{@code onclick}' attribute.
	 * May be a runtime expression.
	 */
	protected String getOnclick() {
		return this.onclick;
	}

	/**
	 * Set the value of the '{@code ondblclick}' attribute.
	 * May be a runtime expression.
	 */
	public void setOndblclick(String ondblclick) {
		this.ondblclick = ondblclick;
	}

	/**
	 * Get the value of the '{@code ondblclick}' attribute.
	 * May be a runtime expression.
	 */
	protected String getOndblclick() {
		return this.ondblclick;
	}

	/**
	 * Set the value of the '{@code onmousedown}' attribute.
	 * May be a runtime expression.
	 */
	public void setOnmousedown(String onmousedown) {
		this.onmousedown = onmousedown;
	}

	/**
	 * Get the value of the '{@code onmousedown}' attribute.
	 * May be a runtime expression.
	 */
	protected String getOnmousedown() {
		return this.onmousedown;
	}

	/**
	 * Set the value of the '{@code onmouseup}' attribute.
	 * May be a runtime expression.
	 */
	public void setOnmouseup(String onmouseup) {
		this.onmouseup = onmouseup;
	}

	/**
	 * Get the value of the '{@code onmouseup}' attribute.
	 * May be a runtime expression.
	 */
	protected String getOnmouseup() {
		return this.onmouseup;
	}

	/**
	 * Set the value of the '{@code onmouseover}' attribute.
	 * May be a runtime expression.
	 */
	public void setOnmouseover(String onmouseover) {
		this.onmouseover = onmouseover;
	}

	/**
	 * Get the value of the '{@code onmouseover}' attribute.
	 * May be a runtime expression.
	 */
	protected String getOnmouseover() {
		return this.onmouseover;
	}

	/**
	 * Set the value of the '{@code onmousemove}' attribute.
	 * May be a runtime expression.
	 */
	public void setOnmousemove(String onmousemove) {
		this.onmousemove = onmousemove;
	}

	/**
	 * Get the value of the '{@code onmousemove}' attribute.
	 * May be a runtime expression.
	 */
	protected String getOnmousemove() {
		return this.onmousemove;
	}

	/**
	 * Set the value of the '{@code onmouseout}' attribute.
	 * May be a runtime expression.
	 */
	public void setOnmouseout(String onmouseout) {
		this.onmouseout = onmouseout;
	}
	/**
	 * Get the value of the '{@code onmouseout}' attribute.
	 * May be a runtime expression.
	 */
	protected String getOnmouseout() {
		return this.onmouseout;
	}

	/**
	 * Set the value of the '{@code onkeypress}' attribute.
	 * May be a runtime expression.
	 */
	public void setOnkeypress(String onkeypress) {
		this.onkeypress = onkeypress;
	}

	/**
	 * Get the value of the '{@code onkeypress}' attribute.
	 * May be a runtime expression.
	 */
	protected String getOnkeypress() {
		return this.onkeypress;
	}

	/**
	 * Set the value of the '{@code onkeyup}' attribute.
	 * May be a runtime expression.
	 */
	public void setOnkeyup(String onkeyup) {
		this.onkeyup = onkeyup;
	}

	/**
	 * Get the value of the '{@code onkeyup}' attribute.
	 * May be a runtime expression.
	 */
	protected String getOnkeyup() {
		return this.onkeyup;
	}

	/**
	 * Set the value of the '{@code onkeydown}' attribute.
	 * May be a runtime expression.
	 */
	public void setOnkeydown(String onkeydown) {
		this.onkeydown = onkeydown;
	}

	/**
	 * Get the value of the '{@code onkeydown}' attribute.
	 * May be a runtime expression.
	 */
	protected String getOnkeydown() {
		return this.onkeydown;
	}

	/**
	 * Get the map of dynamic attributes.
	 */
	protected Map<String, Object> getDynamicAttributes() {
		return this.dynamicAttributes;
	}

	/**
	 * {@inheritDoc}
	 */
	public void setDynamicAttribute(String uri, String localName, Object value ) {
		if (this.dynamicAttributes == null) {
			this.dynamicAttributes = new HashMap<String, Object>();
		}
		if (!isValidDynamicAttribute(localName, value)) {
			throw new IllegalArgumentException(
					"Attribute " + localName + "=\"" + value + "\" is not allowed");
		}
		dynamicAttributes.put(localName, value);
	}

	/**
	 * Whether the given name-value pair is a valid dynamic attribute.
	 */
	protected boolean isValidDynamicAttribute(String localName, Object value) {
		return true;
	}

	public HttpServletRequest getHttpServletRequest(){
		return (HttpServletRequest) pageContext.getRequest();
	}
	
}
