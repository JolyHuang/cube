package com.sharingif.cube.web.springmvc.components.file;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.sharingif.cube.core.exception.validation.ValidationCubeException;

/**
 * 简单上传文件验证
 * 2015年10月11日 下午3:34:04
 * @author Joly
 * @version v1.0
 * @since v1.0
 */
public class SimpleValidateUploadFile implements IValidateUploadFile {
	
	private int supportMaxSize;
	private Map<String,String> supportFileTypeMap;
	private String supportFileTypeSemicolonString;
	
	public int getSupportMaxSize() {
		return supportMaxSize;
	}
	public void setSupportMaxSize(int supportMaxSize) {
		this.supportMaxSize = supportMaxSize;
	}
	public void setSupportFileTypes(List<String> supportFileTypes) {
		supportFileTypeMap = new HashMap<String,String>(supportFileTypes.size());
		StringBuilder supportFileTypeSemicolonStringBuilder = new StringBuilder();
		for(int i=0; i<supportFileTypes.size(); i++){
			String fileType = supportFileTypes.get(i);
			supportFileTypeMap.put(fileType, fileType);
			supportFileTypeSemicolonStringBuilder.append(fileType);
			if(i != supportFileTypes.size()-1){
				supportFileTypeSemicolonStringBuilder.append("\u3001");
			}
		}
		supportFileTypeSemicolonString = supportFileTypeSemicolonStringBuilder.toString();
	}
	
	@Override
	public void validate(MultipartFile file) {
		if(!validateFileSize(file)){
			throw new ValidationCubeException("File is too large, can not upload", new Object[]{supportMaxSize});
		}
		
		if(!validateFileType(file)){
			throw new ValidationCubeException("File type not supported", new Object[]{supportFileTypeSemicolonString});
		}
	}
	
	/**
	 * 验证文件大小
	 * @param file : 文件
	 */
	protected boolean validateFileSize(MultipartFile file) {
		return (supportMaxSize*1024*1024)>file.getSize();
	}
	
	/**
	 * 获取文件后缀
	 * @param fileName : 文件名
	 * @return
	 */
	protected String getFileNameSuffix(String fileName){
		int i = fileName.lastIndexOf('.');
		return fileName.substring(i + 1);
	}
	
	/**
	 * 验证文件类型
	 * @param file : 文件
	 */
	protected boolean validateFileType(MultipartFile file) {
		String fileType = getFileNameSuffix(file.getOriginalFilename());
		if(null == fileType){
			return false;
		}
		return supportFileTypeMap.get(fileType.toLowerCase()) != null;
	}

}
