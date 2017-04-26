package com.sharingif.cube.web.springmvc.components.file;

import org.springframework.web.multipart.MultipartFile;

/**
 * 验证上传文件
 * 2015年10月11日 下午3:31:37
 * @author Joly
 * @version v1.0
 * @since v1.0
 */
public interface IValidateUploadFile {
	
	void validate(MultipartFile file);

}
