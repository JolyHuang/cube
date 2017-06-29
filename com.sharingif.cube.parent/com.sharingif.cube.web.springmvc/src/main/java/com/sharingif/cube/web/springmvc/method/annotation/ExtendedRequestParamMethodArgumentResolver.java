package com.sharingif.cube.web.springmvc.method.annotation;

import com.sharingif.cube.components.file.IValidateUploadFile;
import com.sharingif.cube.components.file.SimpleValidateUploadFile;
import com.sharingif.cube.core.handler.bind.annotation.FileSettings;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.core.MethodParameter;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.annotation.RequestParamMethodArgumentResolver;
import org.springframework.web.multipart.MultipartFile;

/**
 * 扩展RequestParamMethodArgumentResolver，添加文件验证功能
 *
 * @author Joly
 * @version v1.0
 * @since v1.0
 * 2017/6/29 下午5:35
 */
public class ExtendedRequestParamMethodArgumentResolver extends RequestParamMethodArgumentResolver {;

    private IValidateUploadFile validateUploadFile = new SimpleValidateUploadFile();

    public IValidateUploadFile getValidateUploadFile() {
        return validateUploadFile;
    }

    public void setValidateUploadFile(IValidateUploadFile validateUploadFile) {
        this.validateUploadFile = validateUploadFile;
    }

    public ExtendedRequestParamMethodArgumentResolver(boolean useDefaultResolution) {
        super(useDefaultResolution);
    }

    public ExtendedRequestParamMethodArgumentResolver(ConfigurableBeanFactory beanFactory, boolean useDefaultResolution) {
        super(beanFactory, useDefaultResolution);
    }

    @Override
    protected Object resolveName(String name, MethodParameter parameter, NativeWebRequest request) throws Exception {
        Object arg = super.resolveName(name, parameter, request);

        if(null == arg || !(arg instanceof MultipartFile)) {
            return arg;
        }

        MultipartFile multipartFile = (MultipartFile)arg;
        FileSettings fileSettings = parameter.getParameterAnnotation(FileSettings.class);

        if(fileSettings == null) {
            return arg;
        }

        getValidateUploadFile().validate(new com.sharingif.cube.components.file.MultipartFile(multipartFile.getSize(), multipartFile.getOriginalFilename()), fileSettings);

        return arg;
    }

}
