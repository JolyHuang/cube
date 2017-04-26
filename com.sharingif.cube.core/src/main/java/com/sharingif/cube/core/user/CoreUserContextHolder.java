package com.sharingif.cube.core.user;




/**
 *
 * @Description:  [存储用户信息]
 * @Author:       [Joly]
 * @CreateDate:   [2014年5月20日 下午12:03:32]
 * @UpdateUser:   [Joly]
 * @UpdateDate:   [2014年5月20日 下午12:03:32]
 * @UpdateRemark: [说明本次修改内容]
 * @Version:      [v1.0]
 *
 */
public class CoreUserContextHolder {
	
	private static final ThreadLocal<ICoreUser> CONTEXT_HOLDER = new ThreadLocal<ICoreUser>();
	
	public static void clearContext() {
		CONTEXT_HOLDER.remove();
    }

    @SuppressWarnings("unchecked")
	public static <T extends ICoreUser> T getContext() {
        return (T) CONTEXT_HOLDER.get();
    }

    public static void setContext(ICoreUser context) {
    	CONTEXT_HOLDER.set(context);
    }

}
