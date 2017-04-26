package com.sharingif.cube.web.servlet.view;

/**
 * 视图缓存
 * 2015年11月22日 下午12:06:48
 * @author Joly
 * @version v1.0
 * @since v1.0
 */
public enum ViewCacheControl {
	
	NO_STORE("no-store"), NO_CACHE("no-cache");
	
	private final String value;

	ViewCacheControl(String value) {
        this.value = value;
    }
    
    public String getValue() {
        return value;
    }
	
	public static final String VIEW_CACHE_CONTROL_TYPE = "_viewCacheControlType";
	
	public static void main(String[] args) {
		System.out.println(ViewCacheControl.NO_STORE);
	}

}
