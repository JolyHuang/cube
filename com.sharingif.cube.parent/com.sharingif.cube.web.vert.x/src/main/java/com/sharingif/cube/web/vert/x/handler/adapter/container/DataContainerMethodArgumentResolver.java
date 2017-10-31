package com.sharingif.cube.web.vert.x.handler.adapter.container;

import com.sharingif.cube.core.exception.CubeException;
import com.sharingif.cube.core.handler.adapter.HandlerMethodArgumentResolver;
import com.sharingif.cube.core.handler.bind.annotation.DataContainer;
import com.sharingif.cube.core.handler.bind.support.DataBinderFactory;
import com.sharingif.cube.core.request.RequestInfo;
import org.springframework.core.MethodParameter;

import java.util.ArrayList;
import java.util.List;

/**
 * 支持@DataContainer 处理
 *
 * @author Joly
 * @version v1.0
 * @since v1.0
 * 2017/10/31 下午4:43
 */
public class DataContainerMethodArgumentResolver implements HandlerMethodArgumentResolver {

    public DataContainerMethodArgumentResolver() {
        dataContainerMethodArgumentProcessorList = new ArrayList<DataContainerMethodArgumentProcessor>();

        dataContainerMethodArgumentProcessorList.add(new CoreUserDataContainerMethodArgumentProcessor());

    }

    private List<DataContainerMethodArgumentProcessor> dataContainerMethodArgumentProcessorList;

    public List<DataContainerMethodArgumentProcessor> getDataContainerMethodArgumentProcessorList() {
        return dataContainerMethodArgumentProcessorList;
    }

    public void setDataContainerMethodArgumentProcessorList(List<DataContainerMethodArgumentProcessor> dataContainerMethodArgumentProcessorList) {
        this.dataContainerMethodArgumentProcessorList = dataContainerMethodArgumentProcessorList;
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter, RequestInfo<?> requestInfo) {
        return parameter.hasParameterAnnotation(DataContainer.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, RequestInfo<?> requestInfo, DataBinderFactory dataBinderFactory) throws CubeException {
        for(DataContainerMethodArgumentProcessor dataContainerMethodArgumentProcessor : dataContainerMethodArgumentProcessorList){
            if(dataContainerMethodArgumentProcessor.supportsParameter(parameter.getParameterType())){
                return dataContainerMethodArgumentProcessor.resolveArgument(parameter, requestInfo, dataBinderFactory);
            }
        }
        return null;
    }

}
