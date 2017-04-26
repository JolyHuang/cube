package com.sharingif.cube.components.captcha;


/**
 *
 * @Description:  [验证码处理程序]
 * @Author:       [Joly]
 * @CreateDate:   [2014年5月29日 下午6:35:24]
 * @UpdateUser:   [Joly]
 * @UpdateDate:   [2014年5月29日 下午6:35:24]
 * @UpdateRemark: [说明本次修改内容]
 * @Version:      [v1.0]
 *
 */
public interface ICaptchaHandler {
	
	/**
	 * 根据输入内容获得验证码图片字节流
	 * @param Content : 图片输入内容
	 * @return
	 */
	byte[] getCaptchByteStream(String Content)throws Exception;

}
