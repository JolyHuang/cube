package com.sharingif.cube.components.file;

import com.sharingif.cube.core.exception.validation.ValidationCubeException;
import com.sharingif.cube.core.handler.bind.annotation.FileSettings;

/**
 * 简单上传文件验证
 * 2015年10月11日 下午3:34:04
 * @author Joly
 * @version v1.0
 * @since v1.0
 */
public class SimpleValidateUploadFile implements IValidateUploadFile {
	

	@Override
	public void validate(MultipartFile file, FileSettings fileSettings) {
		if(!validateFileSize(file, fileSettings.maxSize())){
			throw new ValidationCubeException("File is too large, can not upload", new Object[]{fileSettings.maxSize()});
		}
		
		if(!validateFileType(file, fileSettings.fileTypes())){
			throw new ValidationCubeException("File type not supported", new Object[]{fileSettings.fileTypes()});
		}
	}
	
	/**
	 * 验证文件大小
	 * @param file : 文件
	 */
	protected boolean validateFileSize(MultipartFile file, Long maxSize) {
		return (maxSize*1024*1024)>file.getSize();
	}
	
	/**
	 * 验证文件类型
	 * @param file : 文件
	 */
	protected boolean validateFileType(MultipartFile file, String[] fileTypes) {
		String fileType = file.getFileNameSuffix();
		if(null == fileType){
			return false;
		}
		for(String ft : fileTypes) {
			if(ft.toLowerCase().equals(fileType.toLowerCase())) {
				return true;
			}
		}

		return false;
	}

}
