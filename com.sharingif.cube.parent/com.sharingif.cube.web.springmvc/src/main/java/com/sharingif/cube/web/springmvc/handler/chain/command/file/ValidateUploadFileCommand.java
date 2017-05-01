package com.sharingif.cube.web.springmvc.handler.chain.command.file;

import org.springframework.web.multipart.MultipartFile;

import com.sharingif.cube.core.exception.CubeException;
import com.sharingif.cube.web.springmvc.components.file.IValidateUploadFile;
import com.sharingif.cube.web.springmvc.handler.SpringMVCHandlerMethodContent;
import com.sharingif.cube.web.springmvc.handler.chain.command.AbstractSpringMVCHandlerMethodCommand;

/**
 * 验证上传文件
 * 2015年10月11日 下午4:22:26
 * @author Joly
 * @version v1.0
 * @since v1.0
 */
public class ValidateUploadFileCommand extends AbstractSpringMVCHandlerMethodCommand {
	
	private IValidateUploadFile validateUploadFile;
	
	public IValidateUploadFile getValidateUploadFile() {
		return validateUploadFile;
	}
	public void setValidateUploadFile(IValidateUploadFile validateUploadFile) {
		this.validateUploadFile = validateUploadFile;
	}


	@Override
	public void execute(SpringMVCHandlerMethodContent content) throws CubeException {
		validateUploadFile.validate(content.findObject(MultipartFile.class));
	}

}
