package com.sharingif.cube.components.file;

import com.sharingif.cube.core.handler.bind.annotation.FileSettings;

/**
 * 验证上传文件
 * 2015年10月11日 下午3:31:37
 * @author Joly
 * @version v1.0
 * @since v1.0
 */
public interface IValidateUploadFile {

	/**
	 * 验证文件信息
	 * @param file : 文件
	 * @param fileSettings : 文件配置
	 */
	void validate(MultipartFile file, FileSettings fileSettings);

}
